package com.example.demo.repo;

import com.example.demo.entity.ShareEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareEventRepo extends JpaRepository<ShareEvent,Long> {
}
