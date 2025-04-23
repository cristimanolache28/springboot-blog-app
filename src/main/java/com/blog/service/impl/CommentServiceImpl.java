package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapDtoToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        comment.setPost(post);
        commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("The post doesn't exist.")
        );

        List<Comment> comments = commentRepository.findCommentsByPostId(post.getId());

        return comments.stream()
                .map(this::mapToDto)
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
        return mapToDto(comment);
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

    private CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapDtoToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }
}









