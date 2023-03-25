package com.blog.blogapp.controller;

import com.blog.blogapp.payload.PostDto;
import com.blog.blogapp.payload.PostResponse;
import com.blog.blogapp.service.PostService;
import com.blog.blogapp.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){  //ResponseEntity used to get response code 201 <> returning dto object

       if(bindingResult.hasErrors()){
           return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    // first it will call create-post method which will save the data then status is returned

//    @GetMapping  //this will give all the records
//    public List<PostDto> getAllPosts(){
//        return postService.getAllPosts();
//    }
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value="pageNo",defaultValue = AppConstants.Default_Page_No,required = false) int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = AppConstants.Default_Page_Size,required = false) int pageSize,
                                    @RequestParam(value = "sortBy",defaultValue = AppConstants.Default_Sort_By,required = false) String sortBy,
                                    @RequestParam(value = "sortDir",defaultValue = AppConstants.Default_Sort_Dir,required = false) String sortDir
                                    ){

        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto= postService.getPostById(id);
        return  ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postResponse = postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);  //new is used when we to get two values in response

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }

}
