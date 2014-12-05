
package com.randika.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.randika.spring.entity.Blog;
import com.randika.spring.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    
    List<Blog> findByUser(User user);
    
}
