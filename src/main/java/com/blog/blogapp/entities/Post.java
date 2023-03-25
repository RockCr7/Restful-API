package com.blog.blogapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
        )
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="description",nullable=false)
    private String description;

    @Lob
    @Column(name="content",nullable=false)
    private String content; //255 characters if no Lob

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)  //if we remove data(one post) from parent
    Set<Comments> comment=new HashSet<>();                      //then it will remove all the data(all comments) from child,orphan is used to delete all comments for particular post
}
