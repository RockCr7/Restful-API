package com.blog.blogapp.service;

import com.blog.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto> getCommentByPostId(long postId);

    CommentDto updateComment(long postId, CommentDto commentDto, long id);

    void deleteComment(long postId, long id);
}
