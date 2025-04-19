package com.blog.payload;

import com.blog.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public static PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }

    public static Post convertDtoToEntity(PostDto postDto) {
        Post post = new Post();
        if (postDto.getId() != null) {
            post.setId(postDto.getId());
        }
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }
}
