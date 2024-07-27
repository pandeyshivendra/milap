package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.WhatsappImageStorage;

public interface WhatsAppImgRepo extends JpaRepository<WhatsappImageStorage, Long> {

    List<WhatsappImageStorage> findByIsSent(Integer isSent);

}
