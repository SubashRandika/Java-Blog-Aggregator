package com.randika.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.randika.spring.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

}
