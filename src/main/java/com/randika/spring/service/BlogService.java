
package com.randika.spring.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.randika.spring.entity.Blog;
import com.randika.spring.entity.Item;
import com.randika.spring.entity.User;
import com.randika.spring.exception.RssException;
import com.randika.spring.repository.BlogRepository;
import com.randika.spring.repository.ItemRepository;
import com.randika.spring.repository.UserRepository;

@Service
public class BlogService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private RssService rssService;
    
    @Autowired
    private ItemRepository itemRepository;
    
    public void saveItems(Blog blog) {
    
        try {
            
            List<Item> items = rssService.getItems(blog.getUrl());
            
            for (Item item : items) {
                Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
                
                if (savedItem == null) {
                    item.setBlog(blog);
                    itemRepository.save(item);
                }
                
            }
            
        } catch (RssException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public void save(Blog blog, String name) {
    
        User user = userRepository.findByName(name);
        blog.setUser(user);
        blogRepository.save(blog);
        saveItems(blog);
    }
    
    @PreAuthorize("#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
    public void delete(@P("blog") Blog blog) {
    
        blogRepository.delete(blog);
    }
    
    public Blog findOne(int id) {
    
        return blogRepository.findOne(id);
    }
    
}
