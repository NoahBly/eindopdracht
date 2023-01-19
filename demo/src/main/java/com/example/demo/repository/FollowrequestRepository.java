package com.example.demo.repository;

import com.example.demo.model.Followrequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FollowrequestRepository extends JpaRepository<Followrequest, Long> {
}
