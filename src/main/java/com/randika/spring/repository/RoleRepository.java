
package com.randika.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.randika.spring.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Role findByName(String name);
    
}
