package com.blog;

import ch.qos.logback.core.model.Model;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import com.blog.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private ModelMapper modelMapper;

    private Post post;
    private PostDto postDto;

    @BeforeEach
    public void setup() {
        post = new Post();
        post.setId(100L);
        post.setTitle("Test Post");
        post.setContent("Test Content");
        post.setDescription("Test Description");

        postDto = new PostDto();
        postDto.setId(100L);
        postDto.setTitle("Test Post");
        postDto.setContent("Test Content");
        postDto.setDescription("Test Description");

    }

    @Test
    void testCreatePostWithoutComments() {
        when(modelMapper.map(postDto, Post.class)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        PostDto saved = postService.createPost(postDto);
        assertEquals(postDto.getTitle(), saved.getTitle());
    }

    @Test
    void testGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        PostDto found = postService.getPostById(1L);
        assertEquals(post.getTitle(), found.getTitle());
    }


}
