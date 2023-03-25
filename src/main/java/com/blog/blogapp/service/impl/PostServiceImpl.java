package com.blog.blogapp.service.impl;

import com.blog.blogapp.entities.Post;
import com.blog.blogapp.exception.ResourceNotFoundException;
import com.blog.blogapp.payload.PostDto;
import com.blog.blogapp.payload.PostResponse;
import com.blog.blogapp.repository.PostRepository;
import com.blog.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper mapper;

    //work as a autowired act as a setter constructor automatically creates a bean
    public PostServiceImpl(PostRepository postRepo,ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);
        Post postEntity = postRepo.save(post);  //it is telling which class object it is saving

        // convert entity to DTO
        PostDto dto = mapToDto(postEntity);
        return dto;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort); //create pageable object || Sort.by(sortBy) above line first sort then asc or dsc
        Page<Post> posts= postRepo.findAll(pageable);             //posts object consist data,page_no,page_size in sorted manner
        List<Post> content= posts.getContent();                   //convert it to list of post from pageclass object
        List<PostDto> collect= content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(collect);            //all are set in post-response object then sent as (json) object
        postResponse.setPageNo(posts.getNumber());  //Page class object will give current page no and other build-in methods
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {

//        Optional<Post> byId= postRepo.findById(id);
//        Post post= byId.get();

        Post post= postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {   //needed dto object since we to set entity
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepo.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);
    }


    public Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);

//        Post post=new Post();
//        post.setId(postDto.getId());  auto-incremented
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }

    public PostDto mapToDto(Post post){
        PostDto dto= mapper.map(post, PostDto.class);

//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());

        return dto;
    }
}
