package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto create(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    String deleteById(Long id);
    PostDto updatePostById(PostDto postDto, Long id);
}
