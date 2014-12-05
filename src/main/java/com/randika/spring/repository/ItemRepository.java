
package com.randika.spring.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.randika.spring.entity.Blog;
import com.randika.spring.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    
    List<Item> findByBlog(Blog blog, Pageable pageable);
    
}
