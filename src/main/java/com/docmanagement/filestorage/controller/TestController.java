package com.docmanagement.filestorage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/")
	public ResponseEntity<?>  testAPI() {

		return ResponseEntity.ok("test controller loaded Successfully");
	}
}
