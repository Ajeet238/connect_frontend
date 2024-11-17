package com.docmanagement.filemetadata.controller;

import java.util.Optional;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmanagement.filemetadata.entity.FileMetaData;
import com.docmanagement.filemetadata.repository.FileMetaDataRepository;

@Controller
@RequestMapping("/metadata")
public class FileMetaDataController {
	
	@Autowired
	FileMetaDataRepository fileMetaDataRepository;
	
	@PostMapping("/adddata")
//	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addMetaData(@RequestBody FileMetaData fileMetaData){
		fileMetaDataRepository.save(fileMetaData);
		return ResponseEntity.ok("MetaData saved Successfully");
	}
	
	@GetMapping("/getdata/{fileId}")
	//@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getMetaData(@PathVariable Long fileId){
		   Optional<FileMetaData> metadata = Optional.ofNullable(fileMetaDataRepository.findByFileId(fileId));
	    
		   return Optional.ofNullable(metadata)
				    .map(data -> new ResponseEntity<>(data, HttpStatus.OK))  // If metadata is not null, return 200 OK with metadata
				    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // If metadata is null, return 404 Not Found	 		
	}
	
	
}
