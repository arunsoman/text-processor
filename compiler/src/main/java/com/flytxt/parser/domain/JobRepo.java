package com.flytxt.parser.domain;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "jobrepo", path = "jobrepo")
public interface JobRepo extends CrudRepository<Job, Long>{
	
	List<Job> findByhostNameAndIsActive(@Param("hostName") String hostName,@Param("is_active") Long isActive);
	

}
