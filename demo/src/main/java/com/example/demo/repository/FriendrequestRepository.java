package com.example.demo.repository;

import com.example.demo.model.Friendrequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FriendrequestRepository extends JpaRepository<Friendrequest, Long> {
}
