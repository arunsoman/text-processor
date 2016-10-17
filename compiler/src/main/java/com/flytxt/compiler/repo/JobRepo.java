package com.flytxt.compiler.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.flytxt.compiler.domain.Job;

public interface JobRepo extends CrudRepository<Job, Long> {

    List<Job> findByhostNameAndActiveTrue(@Param("hostName") String hostName);
}
