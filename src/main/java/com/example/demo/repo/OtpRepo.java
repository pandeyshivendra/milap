package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Otp;

public interface OtpRepo extends JpaRepository<Otp, Long> {

    //@Query(value = "SELECT * FROM tutorials t WHERE t.title LIKE %?1%", nativeQuery = true)
    //List<Tutorial> findByTitleAndSortNative(String title, Sort sort);

    @Query(value = "SELECT * FROM otp o where o.mobile LIKE %?1% order by expire_time desc limit 1", nativeQuery = true)
    List<Otp> findByMobile(String mobile);

}
