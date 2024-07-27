package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EventUserMap;

public interface EventUserMapRepo extends JpaRepository<EventUserMap, Long> {

}
