package com.flytxt.compiler.repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.flytxt.compiler.domain.Job;

@RepositoryRestResource(collectionResourceRel = "jobrepo", path = "jobrepo")
public interface JobRepo extends CrudRepository<Job, Long>{
	
	List<Job> findByhostNameAndIsActive(@Param("hostName") String hostName,@Param("is_active") Long isActive);
	

}
