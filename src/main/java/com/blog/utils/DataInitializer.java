package com.blog.utils;

import com.blog.entity.Post;
import com.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PostRepository postRepository;

    @Autowired
    public DataInitializer(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (postRepository.count() == 0) {
            List<Post> postList = List.of(
                    new Post("Titlu 1", "Descriere 1", "Continut 1"),
                    new Post("Titlu 2", "Descriere 2", "Continut 2"),
                    new Post("Titlu 3", "Descriere 3", "Continut 3"),
                    new Post("Titlu 4", "Descriere 4", "Continut 4"),
                    new Post("Titlu 5", "Descriere 5", "Continut 5"),
                    new Post("Titlu 6", "Descriere 6", "Continut 6"),
                    new Post("Titlu 7", "Descriere 7", "Continut 7"),
                    new Post("Titlu 8", "Descriere 8", "Continut 8"),
                    new Post("Titlu 9", "Descriere 9", "Continut 9"),
                    new Post("Titlu 10", "Descriere 10", "Continut 10")
            );

            postRepository.saveAll(postList);

            System.out.println("Postări prestabilite salvate.");
        } else {
            System.out.println("Postările există deja. Nu se inserează din nou.");
        }
    }
}
