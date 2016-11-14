package com.flytxt.tp.processor;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface JobRepo extends PagingAndSortingRepository<Job, Long> {

    List<Job> findByhostNameAndActiveTrueAndStatusTrue(@Param("hostName") String hostName);

}
