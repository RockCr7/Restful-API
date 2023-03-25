package com.blog.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication            //starting point of application and some configuration need to be done like beans to be created
public class BlogappApplication {

	@Bean  //wherever ModelMapper comes Spring Boot will create its object bcz this is not a S.B component it's a separate java library
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogappApplication.class, args);
	}

}
