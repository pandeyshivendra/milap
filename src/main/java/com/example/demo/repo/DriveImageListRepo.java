package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DriveImageList;

public interface DriveImageListRepo extends JpaRepository<DriveImageList, Long> {

    List<DriveImageList> findByEventId(Long eventId);

    boolean existsByDriveFileId(String fileID);
}
