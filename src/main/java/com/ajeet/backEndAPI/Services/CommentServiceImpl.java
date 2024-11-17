package com.ajeet.backEndAPI.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ajeet.backEndAPI.Entity.CommentEntity;
import com.ajeet.backEndAPI.Entity.PostEntity;
import com.ajeet.backEndAPI.Exception.BlogApiException;
import com.ajeet.backEndAPI.Exception.ResourceNotFoundException;
import com.ajeet.backEndAPI.Repository.CommentRepository;
import com.ajeet.backEndAPI.Repository.PostRepository;
import com.ajeet.backEndAPI.Services.CommentService;
import com.ajeet.backEndAPI.payload.CommentEntityDto;
import com.ajeet.backEndAPI.payload.PostEntityDto;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

// create comment
 public	CommentEntityDto createComment (CommentEntityDto commentEntityDto, Long id) {
	 PostEntity post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
	 CommentEntity comment = dtoToCommentEntity(commentEntityDto);
	 comment.setPost(post);
	 	CommentEntity saveComment = commentRepository.save(comment);
	 	return commentEntityToDto(saveComment);
	}
 
// get comment
 public List<CommentEntityDto> getCommentById(Long postId) {
	//List<CommentEntity> comment =  commentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
	 List<CommentEntity> comment = commentRepository.findCommentByPostId(postId);
	List<CommentEntityDto> listOfComment = comment.stream().map(commentEntity ->commentEntityToDto(commentEntity)).collect(Collectors.toList());
	return listOfComment; 
 }
 
 // get comment with commentid in particular postid
 public CommentEntityDto getCommentByPostId(Long commentId, Long PostId) {
	 // find post
	 PostEntity post = postRepository.findById(PostId).orElseThrow(()-> new ResourceNotFoundException("post", "id", PostId));
	 // find comment
	 CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));
	 
	 if(!comment.getPost().getId().equals(post.getId())) {
		 throw new BlogApiException(HttpStatus.BAD_GATEWAY, "Comment does not belong to the post");
	 }
	 
	 return commentEntityToDto(comment);
 }
 
 // update comment of specific post
 
 public	CommentEntityDto updateComment(Long postId,Long commentId,CommentEntityDto commentDto) {
	//	PostEntity post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
	 PostEntity post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
	 // find comment
	 CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));
	 
	 if(!comment.getPost().getId().equals(post.getId())) {
		 throw new BlogApiException(HttpStatus.BAD_GATEWAY, "Comment does not belong to the post");
	 }
	 	
	 	comment.setBody(commentDto.getBody());
	 	comment.setEmail(commentDto.getEmail());
	 	comment.setName(commentDto.getName());
	 	
	 	 commentRepository.save(comment);
	 	return commentEntityToDto(comment);
	}
 
 // delete comment of particular post
 public void deleteComment(Long postId,Long commentId) {
	 PostEntity post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
	 // find comment
	 CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));
	 
	 if(!comment.getPost().getId().equals(post.getId())) {
		 throw new BlogApiException(HttpStatus.BAD_GATEWAY, "Comment does not belong to the post");
	 }
	 commentRepository.delete(comment);
 }
 // CommentEntityDto to CommentEntity
 public CommentEntityDto commentEntityToDto(CommentEntity commentEntity) {
	 
	 CommentEntityDto commentEntityDto = modelMapper.map(commentEntity, CommentEntityDto.class);
	 
	 return commentEntityDto;
	 
 }
 // CommentEntity to CommentEntityDto
 public CommentEntity dtoToCommentEntity(CommentEntityDto commentEntityDto) {
	 
	 CommentEntity commentEntity = modelMapper.map(commentEntityDto, CommentEntity.class);
	 
	 return commentEntity;
	 
 }

}
