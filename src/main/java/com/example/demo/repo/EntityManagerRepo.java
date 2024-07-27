package com.example.demo.repo;

import com.example.demo.entity.FilterCriteria;
import com.example.demo.entity.User;
import com.example.demo.utility.ResponseObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class EntityManagerRepo {

    @PersistenceContext
    private EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(EntityManagerRepo.class);

    @Transactional
    public void mapEventImage(Long eventId, Long imageId) throws SQLException {
        entityManager.createNativeQuery("insert into event_image_map (event_id,image_id)values(?,?)")
                .setParameter(1, eventId)
                .setParameter(2, imageId)
                .executeUpdate();
    }

    @Transactional
    public void mapDriveImageUser(Long userId, Long driveImage, long eventId, int status, String exceptions) throws SQLException {
        entityManager.createNativeQuery("insert into drive_image_user_map (user_id,drive_image_id,event_id,recognization_status,exception)values(?,?,?,?,?)")
                .setParameter(1, userId)
                .setParameter(2, driveImage)
                .setParameter(3, eventId)
                .setParameter(4, status)
                .setParameter(5, exceptions)
                .executeUpdate();
    }

    @Transactional
    public int removeMappingEventImage(Long event_id, Long image_id) throws SQLException {
        logger.info("REMOVING MAPPING ::::::: ");
        var query = image_id == null || image_id == 0 ? "delete from event_image_map where event_id=" + event_id : "delete from event_image_map where event_id=" + event_id + " and image_id=" + image_id;
        logger.info("QUERY ::: " + query);
        int res = entityManager.createNativeQuery(query).executeUpdate();
        return res;
    }


    public List<Object[]> eventFilterData(FilterCriteria filterdata) throws SQLException {
        var limit = filterdata.getPageNo() != null && filterdata.getSize() != null ? getPagenationData(filterdata) : "";
        var userID = filterdata.getUserId() != null ? "(se.user_id_shared_to="+ filterdata.getUserId()+" or ev.user_id=" + filterdata.getUserId() + ") and " : "";
        var category = filterdata.getCategory() != null && !filterdata.getCategory().isBlank() ?
                "ev.type = '" + filterdata.getCategory() + "' and " : "";

        var whereClous = " where " + userID + category + " ev.event_date >='" + filterdata.getFromDate() + "' and ev.event_date <='" + filterdata.getToDate() + "'";

        var query = "SELECT ev.id,ev.name,group_concat(ei.image_url),ev.token,ev.venue,ev.qrcode,ev.event_date,ev.type,ev.description,ev.event_profile_pic,u.name as user_name,CONCAT(\"https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/placeHolder.png\") image_url,u.user_id,"
                + "CONCAT(\"https://drive.google.com/drive/folders/\",ev.drive_file_id) drive_path "
                + "  FROM event ev left join event_image_map eim on "
                + "  ev.id = eim.event_id left join event_image ei on ei.id = eim.image_id left join user u on ev.user_id=u.user_id left join share_event se on se.event_id=ev.id " + whereClous
                + " group by ev.id order by ev.event_date " + limit;

        logger.info("FILTER QUERY ::::: " + query);
        List<Object[]> result = entityManager.createNativeQuery(query).getResultList();

        return result;

    }

    public List<Object[]> getSharedEvent(FilterCriteria filterdata) throws SQLException {
        var userID = filterdata.getUserId() != null ? "se.user_id_shared_to=" + filterdata.getUserId() + " and " : "";
        var category = filterdata.getCategory() != null && !filterdata.getCategory().isBlank() ?
                "ev.type = '" + filterdata.getCategory() + "' and " : "";
        var whereClous = " where " + userID + category + " ev.event_date >='" + filterdata.getFromDate() + "' and ev.event_date <='" + filterdata.getToDate() + "'";
        var query = "SELECT ev.id,ev.name,group_concat(ei.image_url) url,ev.token,ev.venue,ev.qrcode,ev.event_date,ev.type,ev.description,ev.event_profile_pic,u.name as user_name,CONCAT(\"https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/placeHolder.png\") image_url,u.user_id,\n" +
                " CONCAT(\"https://drive.google.com/drive/folders/\",ev.drive_file_id) drive_path FROM event ev left join event_image_map eim on\n" +
                " ev.id = eim.event_id left join event_image ei on ei.id = eim.image_id left join user u on ev.user_id=u.user_id left join share_event se on se.event_id = ev.id "+whereClous+
                " group by ev.id order by ev.event_date ";

        logger.info("SHARE EVENT ::::: " + query);
        List<Object[]> result = entityManager.createNativeQuery(query).getResultList();

        return result;
    }



    public List<Object[]> searchEvent(String search, String userId) {

        var whereClouse = userId != null ? " and ev.user_id='" + userId.strip() + "'" : "";
        var qry = "SELECT ev.id,ev.name,group_concat(ei.image_url),ev.token,ev.venue,ev.qrcode,ev.event_date,ev.type,ev.description,ev.event_profile_pic,u.name as user_name,u.image_url,u.user_id,"
                + "CONCAT(\"https://drive.google.com/drive/folders/\",ev.drive_file_id) drive_path  "
                + " FROM event ev left join event_image_map eim on "
                + "	ev.id = eim.event_id left join event_image ei on ei.id = eim.image_id left join user u on ev.user_id=u.user_id where ev.name like '%" + search + "%'" + whereClouse
                + " group by ev.id order by ev.event_date ";
        logger.info("FILTER QUERY ::::: " + qry);
        List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();

        return result;
    }

    @Transactional
    public ResponseEntity<?> purgeUser(Map<String, String> qparams) {
        ResponseObj resp = new ResponseObj();
        try{// delete from whatsapp_image_storage where user_mobile=
            String mobileNo = qparams.get("mobile");
            String userId = qparams.get("userId");

            var query = "delete from otp where mobile='"+mobileNo+"'";
            int res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: OTP "+res);

            query = "delete from whatsapp_template_status where user_mobile='"+mobileNo+"'";
            res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: whatsapp_template_status "+res);

            query = "delete from whatsapp_image_storage where user_mobile='"+mobileNo+"'";
            res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: whatsapp_image_storage "+res);

            query = "delete from event_user_map where user_id='"+userId+"'";
            res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: event_user_map "+res);

            query = "delete from drive_image_user_map where event_id in (select id from event where user_id='"+userId+"')";
            res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: drive_image_user_map "+res);

            query = "delete from drive_image_list where event_id in (select id from event where user_id='"+userId+"')";
            res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: drive_image_list "+res);

            query = " delete from event where user_id='"+userId+"'";
            res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: event "+res);

            query = "delete from user where user_id='"+userId+"'";
            res = entityManager.createNativeQuery(query).executeUpdate();
            logger.info("DELETE ::: USER "+res);

            resp.setStatusMsg("Deleted Successfully");
            resp.setStatusCode("ncs-200");
            return ResponseEntity.status(200).body(resp);

        }catch (Exception e){
            e.printStackTrace();
            resp.setData(e.getMessage());
            resp.setStatusMsg("error");
            resp.setStatusCode("ncs-401");
            logger.error("DELETE ::: Error "+e.getMessage());
        }
        return ResponseEntity.status(401).body(resp);
    }

    public Boolean checkDriveImage_usermap(Long userId, Long driveImageId) {
        var qry = "select user_id,drive_image_id from drive_image_user_map where user_id=" + userId + " and drive_image_id=" + driveImageId;
        List<Object> result = entityManager.createNativeQuery(qry).getResultList();
        logger.info("ENTITY MANAGER ---::: " + result.size());
        return result != null && result.size() >= 1L;
    }
	    
	    /* formula for  pagenation for size = 2
	      page 1  0,2
	      page 2  2,2        start = size * (page -1) where size is fixed
	      page 3  4,2  
	      */

    private String getPagenationData(FilterCriteria filterdata) {
        int start = 0;
        int size = filterdata.getSize();
        if (filterdata.getPageNo() > 1) {
            start = size * (filterdata.getPageNo() - 1);
        }
        return " limit " + start + "," + size;
    }



}
