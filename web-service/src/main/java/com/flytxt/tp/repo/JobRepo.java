package com.flytxt.tp.repo;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.flytxt.tp.domain.Job;
@ComponentScan
@RepositoryRestResource(collectionResourceRel = "jobrepo", path = "jobrepo")
public interface JobRepo extends PagingAndSortingRepository<Job, Long> {

    List<Job> findByhostNameAndActiveTrue(@Param("hostName") String hostName);

}
