package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {
    private  CommentRepository commentRepository;
    private  PostRepository postRepository;
    private  ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public com.blog.payload.CommentDto createComment(Long postId, com.blog.payload.CommentDto commentDto) {
        Comment comment = mapDtoToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        comment.setPost(post);
        commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public List<com.blog.payload.CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        List<Comment> comments = commentRepository.findCommentsByPostId(post.getId());

        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public com.blog.payload.CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        if (!comment.getPost().getId().equals(postId)) {
            throw new ResourceNotFoundException("Comentariul nu apartine postarii specifica.");
        }
        return mapToDto(comment);
    }

    @Override
    public com.blog.payload.CommentDto updateComment(Long postId, Long commentId, com.blog.payload.CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("The comment doesn't exist.")
        );
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        if (!comment.getPost().getId().equals(postId)) {
            throw new ResourceNotFoundException("Comentariul nu apartine postarii specifica.");
        } else {
            commentRepository.save(comment);
        }

        return mapToDto(comment);
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("The comment doesn't exist.")
        );

        commentRepository.deleteById(commentId);
        return "The comment was deleted.";
    }

    private com.blog.payload.CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment, com.blog.payload.CommentDto.class);
    }

    private Comment mapDtoToEntity(com.blog.payload.CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }
}









