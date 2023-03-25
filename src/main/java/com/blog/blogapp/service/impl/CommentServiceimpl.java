package com.blog.blogapp.service.impl;

import com.blog.blogapp.entities.Comments;
import com.blog.blogapp.entities.Post;
import com.blog.blogapp.exception.ResourceNotFoundException;
import com.blog.blogapp.payload.CommentDto;
import com.blog.blogapp.repository.CommentRepository;
import com.blog.blogapp.repository.PostRepository;
import com.blog.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceimpl implements CommentService {
    private CommentRepository commentRepo;
    private PostRepository postRepo;

    private ModelMapper mapper;

    public CommentServiceimpl(CommentRepository commentRepo, PostRepository postRepo,ModelMapper mapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.mapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) { //is passed to specify for which post comment is saved
        Post post= postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comments comments= mapToComment(commentDto);
        comments.setPost(post);  //for which post comment is to be set
        Comments save= commentRepo.save(comments); //return entity object
        CommentDto commentDto1= mapToDto(save);
        return commentDto1;
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comments> comments = commentRepo.findByPostId(postId);
        List<CommentDto> collect= comments.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto, long id) {
        Post post= postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comments comments= commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", id)
        );
        comments.setName(commentDto.getName());
        comments.setEmail(commentDto.getEmail());
        comments.setBody(commentDto.getBody());
        Comments save= commentRepo.save(comments);
        return mapToDto(save);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post= postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comments comments= commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", id)
        );
        commentRepo.deleteById(id);
    }

    Comments mapToComment(CommentDto commentDto){
        Comments comment= mapper.map(commentDto, Comments.class);
//        Comments comment=new Comments();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }

    CommentDto mapToDto(Comments comments){
        CommentDto commentDto= mapper.map(comments, CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(comments.getId());
//        commentDto.setName(comments.getName());
//        commentDto.setEmail(comments.getEmail());
//        commentDto.setBody(comments.getBody());
        return commentDto;
    }
}
