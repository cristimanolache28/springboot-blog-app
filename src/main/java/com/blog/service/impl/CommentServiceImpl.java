package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.payload.CommentMapper;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = CommentMapper.mapDtoToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        comment.setPost(post);
        commentRepository.save(comment);
        return CommentMapper.mapToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        List<Comment> comments = commentRepository.findCommentsByPostId(post.getId());

        return comments.stream()
                .map(CommentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        if (!comment.getPost().getId().equals(postId)) {
            throw new ResourceNotFoundException("Comentariul nu apartine postarii specifica.");
        }
        return CommentMapper.mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
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

        return CommentMapper.mapToDto(comment);
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("The comment doesn't exist.")
        );

        commentRepository.deleteById(commentId);
        return "The comment was deleted.";
    }
}









