package com.ajeet.docManagement.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
import com.ajeet.docManagement.Entity.User;
import com.ajeet.docManagement.Repository.UserRepository;
import com.ajeet.docManagement.config.JwtProvider;
import com.ajeet.docManagement.exception.UserException;
import com.ajeet.docManagement.request.LoginRequest;
import com.ajeet.docManagement.response.AuthResponse;
import com.ajeet.docManagement.userDetailServiceimpl.UserDetailService;


@RestController
@RequestMapping("/auth")
public class AuthController {
 
    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailService customUserService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		System.out.print("insideAPI");
		String password = user.getPassword();
		String userName = user.getUsername();
		String firstName = user.getFirstname();
		String lastName = user.getLastname();
		String role = user.getRole();
		
				System.out.println("authcontroller");
		Optional<User> isEmailExist = userRepository.findByEmail(email);
		if (isEmailExist.isPresent()) {
			throw new UserException("email already registered");
		} else {
			User createUser = new User();
			createUser.setEmail(email);
			// set encoded password
			createUser.setPassword(passwordEncoder.encode(password));
			createUser.setFirstname(firstName);
			createUser.setLastname(lastName);
			createUser.setUsername(userName);
			createUser.setRole(role);

			// now save user using userrepository

			User savedUser = userRepository.save(createUser);
			
			// create authentication
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// generate token
			String token = jwtProvider.generateToken(authentication);
			
			// now return the response, for this create authresponse classs

			AuthResponse authResponse = new AuthResponse();
			authResponse.setJwt(token);
			authResponse.setMessage("Sign Success");
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

