package com.ajeet.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.ecommerce.config.JwtProvider;
import com.ajeet.ecommerce.exception.UserException;
import com.ajeet.ecommerce.model.Cart;
import com.ajeet.ecommerce.model.User;
import com.ajeet.ecommerce.repository.UserRepository;
import com.ajeet.ecommerce.request.LoginRequest;
import com.ajeet.ecommerce.response.AuthResponse;
import com.ajeet.ecommerce.service.CartService;
import com.ajeet.ecommerce.service.CustomUserServiceImplementation;

@RestController
@RequestMapping("/auth") // endpoint starts with auth
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserServiceImplementation customUserService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		User isEmailExist = userRepository.findByEmail(email);
		if (isEmailExist != null) {
			throw new UserException("email already registered");
		} else {
			User createUser = new User();
			createUser.setEmail(email);
			// set encoded password
			createUser.setPassword(passwordEncoder.encode(password));
			createUser.setFirstName(firstName);
			createUser.setLastName(lastName);

			// now save user using userrepository

			User savedUser = userRepository.save(createUser);
			// create cart just after creating user
			Cart cart = cartService.createCart(savedUser);
			

			// create authentication
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// generate token
			String token = jwtProvider.generateToken(authentication);
			
			// now return the response, for this create authresponse classs

			AuthResponse authResponse = new AuthResponse();
			authResponse.setJwt(token);
			authResponse.setMessage("sign up success");
			return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
		String userName = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authenticate(userName, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("sign in success");
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

	}

	Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid User");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");

		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
