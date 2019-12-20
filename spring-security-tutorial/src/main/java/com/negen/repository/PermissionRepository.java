package com.negen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.negen.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
}
