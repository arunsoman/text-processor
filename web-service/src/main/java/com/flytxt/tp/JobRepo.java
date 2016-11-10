package com.flytxt.tp;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "jobrepo", path = "jobrepo")
public interface JobRepo extends PagingAndSortingRepository<Job, Long> {

    List<Job> findByhostNameAndActiveTrue(@Param("hostName") String hostName);

}
