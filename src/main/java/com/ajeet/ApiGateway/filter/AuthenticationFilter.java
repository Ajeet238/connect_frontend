package com.ajeet.ApiGateway.filter;

import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator routeValidate;
	
	@Autowired
	private RestTemplate template;
	
	public class Config {

	}

	public AuthenticationFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthenticationFilter(Class<Config> configClass) {
		super(configClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
        return ((exchange, chain) -> {
           if(routeValidate.isSecured.test(exchange.getRequest())) {
        	   if(!exchange.getRequest().getHeaders().containsKey(org.springframework.http.HttpHeaders.AUTHORIZATION)) {
        		   throw new RuntimeException("Missing authorization header");
        	   }
       	   
        	   String authHeader = exchange.getRequest().getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);
        	   if(authHeader != null && authHeader.startsWith("Bearer")) {
        		   authHeader = authHeader.substring(7);
        	   }
        	   try {
        		   template.getForObject("http://USERSERVICE/auth/validatetoken?token=" + authHeader,String.class);
        	   }catch(Exception e) {
        		   System.out.println("Invalid accesss");
        		   throw new RuntimeException("Un authorised accesss to application");
        	   }
        }

            return chain.filter(exchange);
        });
		
		}

		
   }

	
