package com.docmanagement.filestorage.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.docmanagement.filemetadata.entity.FileMetaData;

// used to call API inside microservices
@FeignClient(name = "filemetadata")
public interface FileMetaDataService {
	
	@PostMapping("/metadata/addddata")
	FileMetaData addMetaData(FileMetaData values);
}
