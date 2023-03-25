package com.blog.blogapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String body;
    private String email;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)    //comment(child table) is mapped to Post(Parent table) || lazy loaded only that table which is required not all the table
                                            //if it is eager then it will all the tables(entities) at once(not required all at once) and will decrease the performance
    @JoinColumn(name="post_id",nullable = false)  //to create foreign key primary is already present in Post
    private Post post;   // For one post there are many comments
}
