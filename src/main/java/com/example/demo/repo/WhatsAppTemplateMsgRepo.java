package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.User;
import com.example.demo.entity.WhatsappTemplateStatus;

public interface WhatsAppTemplateMsgRepo extends JpaRepository<WhatsappTemplateStatus, Long> {

    @Query(value = "select * from whatsapp_template_status where user_mobile=?1 order by created_dt desc limit 1", nativeQuery = true)
    List<WhatsappTemplateStatus> getLatest(String mobile);

}
