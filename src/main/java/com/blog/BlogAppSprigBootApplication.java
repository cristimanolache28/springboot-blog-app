package com.blog;

import com.blog.entity.Role;
import com.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppSprigBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppSprigBootApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("USER_ROLE");
		roleRepository.save(userRole);
	}
}
