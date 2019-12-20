package com.negen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.negen.entity.Role;
import com.negen.entity.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
}
