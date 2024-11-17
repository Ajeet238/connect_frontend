package com.docmanagement.filemetadata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docmanagement.filemetadata.entity.FileMetaData;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Long> {
	
	FileMetaData findByFileId(Long fileid);
	
}
