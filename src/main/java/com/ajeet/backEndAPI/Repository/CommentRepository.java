package com.ajeet.backEndAPI.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajeet.backEndAPI.Entity.CommentEntity;


// no need to annotate JPA repository bcz we simpledatajpa class with @repository annotation which implements jparepository intarnally
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
	// get comment by post id
	List<CommentEntity> findCommentByPostId(Long postId);
}
