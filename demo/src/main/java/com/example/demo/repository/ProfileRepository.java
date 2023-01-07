package com.example.demo.repository;

import com.example.demo.model.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    List<Profile>findAllByName(String name);
}
