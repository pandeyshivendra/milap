package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Event;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findByToken(String token);

    @Query(value = "SELECT title,description FROM documentation doc where doc.title LIKE %?1% order by id ", nativeQuery = true)
    List<Object[]> getDocumentByTitle(String title);

    @Query(value = "SELECT ev.id,ev.name,group_concat(ei.image_url),ev.token,ev.venue,ev.qrcode,ev.event_date,ev.type,ev.description,ev.event_profile_pic,u.name as user_name,CONCAT(\"https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/placeHolder.png\") image_url,u.user_id,"
            + "CONCAT(\"https://drive.google.com/drive/folders/\",ev.drive_file_id) drive_path "
            + " FROM event ev left join event_image_map eim on"
            + " ev.id = eim.event_id left join event_image ei on ei.id = eim.image_id left join user u on ev.user_id=u.user_id group by ev.id order by ev.id desc", nativeQuery = true)
    List<Object[]> getAllEventwithImage();


    @Query(value = "SELECT ev.id,ev.name,group_concat(ei.image_url),ev.token,ev.venue,ev.qrcode,ev.event_date,ev.type,ev.description,ev.event_profile_pic,u.name as user_name,CONCAT(\"https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/placeHolder.png\") image_url,u.user_id,"
            + "CONCAT(\\\"https://drive.google.com/drive/folders/\\\",ev.drive_file_id) drive_path "
            + " FROM event ev left join event_image_map eim on"
            + " ev.id = eim.event_id left join event_image ei on ei.id = eim.image_id left join user u on ev.user_id=u.user_id group by ev.id order by ev.id desc limit ?1,?2", nativeQuery = true)
    List<Object[]> getAllEventwithImageWithPagenation(int pageNo, int size);

    @Query(value = "SELECT ev.id,ev.name,group_concat(ei.image_url),ev.token,ev.venue,ev.qrcode,ev.event_date,ev.type,ev.description,ev.event_profile_pic,u.name as user_name,CONCAT(\"https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/placeHolder.png\") image_url,u.user_id,"
            + "CONCAT(\"https://drive.google.com/drive/folders/\",ev.drive_file_id) drive_path  "
            + " FROM event ev left join event_image_map eim on"
            + " ev.id = eim.event_id left join event_image ei on ei.id = eim.image_id left join user u on ev.user_id=u.user_id where ev.id=?1", nativeQuery = true)
    List<Object[]> getEventById(Long id);

    @Query(value = "SELECT count(ev.id) FROM event ev left join event_image_map eim on"
            + " ev.id = eim.event_id left join event_image ei on ei.id = eim.image_id group by ev.id ", nativeQuery = true)
    Long getAllEventwithImageCount();


}
