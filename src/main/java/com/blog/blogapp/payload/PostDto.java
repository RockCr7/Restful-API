package com.blog.blogapp.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotNull
    @Size(min=2,message = "Post-Title should have at-least 2 Characters")
    private String title;

    @NotNull
    @Size(min=10,message = "Post-description should have at-least 10 Characters")
    private String description;

    private String content;
}
