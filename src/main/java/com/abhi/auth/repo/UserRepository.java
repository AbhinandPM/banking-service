package com.abhi.auth.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abhi.auth.model.CustomUser;

@Repository
public interface UserRepository extends CrudRepository<CustomUser, Long>{

	Optional<CustomUser> findByUsername(String username);
	
}
