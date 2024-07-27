package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.LookupValue;

public interface LookupRepo extends JpaRepository<LookupValue, Long> {

    @Query(value = "SELECT * FROM lookup_value lk where lk.lookup_type LIKE %?1% order by name", nativeQuery = true)
    List<LookupValue> findByLookupType(String type);

    @Query(value = "SELECT title,description FROM documentation order by id", nativeQuery = true)
    List<Object[]> getDocument();

    @Query(value = "SELECT title,description FROM documentation doc where doc.title LIKE %?1% order by id ", nativeQuery = true)
    List<Object[]> getDocumentByTitle(String title);
}
