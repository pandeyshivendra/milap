package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByMobile(String mobile);

    boolean existsUserByMobile(String mobile);

    @Query(value = "select * from user u inner join event_user_map eum on u.user_id = eum.user_id "
            + "inner join event e on e.id=eum.event_id where eum.event_id=?1", nativeQuery = true)
    List<User> getlListByevent(Long eventId);


    //select u.user_id,u.name,u.image_url from user u inner join event_user_map eum on u.user_id = eum.user_id inner join event e on e.id=eum.event_id

}
