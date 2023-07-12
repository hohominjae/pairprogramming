package com.thesun4sky.springblog.post.service;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import com.thesun4sky.springblog.user.dto.AuthRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thesun4sky.springblog.post.dto.PostListResponseDto;
import com.thesun4sky.springblog.post.dto.PostRequestDto;
import com.thesun4sky.springblog.post.dto.PostResponseDto;
import com.thesun4sky.springblog.post.entity.Post;
import com.thesun4sky.springblog.user.entity.User;
import com.thesun4sky.springblog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public PostListResponseDto getPosts() {
        List<PostResponseDto> postList = postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PostListResponseDto(postList);
    }

    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, User user, AuthRequestDto authRequestDto) {
        //id값을 이용해서 찾은 게시물
        Post post = findPost(id);

        if(authRequestDto.isAdmin()){
            postRepository.delete(post);
        } else {
            throw new RejectedExecutionException();
        }

//        작성자가 아닐때
        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }
        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);

        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    private Post findPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
