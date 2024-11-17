package com.docmanagement.filestorage.controller;

import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.docmanagement.filemetadata.entity.FileMetaData;
import com.docmanagement.filestorage.service.FileMetaDataService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RestController
@RequestMapping("/storage")
public class FileStorageController {

	@Autowired
	private S3Client s3client;
	
	@Value("${aws.s3.bucket.name}")
	private String bucketName;
	
	// @PreAuthorize("hasRole('USER')")
	@PostMapping("/upload")
	public  ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
		System.out.print("inside controller");
		String fileName = file.getOriginalFilename();
		
		try{
			s3client.putObject(PutObjectRequest.builder()
					.bucket(bucketName).key(fileName)
					.build(), 
					RequestBody.fromBytes(file.getBytes()));
			
			return ResponseEntity.ok("FIle uploaded Successfully"+ fileName);
		}catch(IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
		}
	}
	
	@GetMapping("/download/{fileName}")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName){
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
				.bucket(bucketName)
				.key(fileName)
				.build();
		 
		//.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")  => help to download the file in required format
		byte [] content = s3client.getObjectAsBytes(getObjectRequest).asByteArray();
		// below code return array of bytes. If you save the response in postman in save as file the file get downloaded.
		//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
		//				.contentType(MediaType.APPLICATION_OCTET_STREAM)
		//				.body(content);
		
		// return the array of bytes in base64 json format.
		String base64 = Base64.getEncoder().encodeToString(content);
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"fileContent\": \"" + base64 + "\"}");
	}
}
