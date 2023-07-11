package com.thesun4sky.springblog.post.repository;

import com.thesun4sky.springblog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
