package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class EventDirConfig {
    //: id, event_id, source_dir, source_dir_type, destination_dir
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String sourceDir;
    private String sourceDirType;
    private String destinationDir;
    private Integer eventId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getSourceDirType() {
        return sourceDirType;
    }

    public void setSourceDirType(String sourceDirType) {
        this.sourceDirType = sourceDirType;
    }

    public String getDestinationDir() {
        return destinationDir;
    }

    public void setDestinationDir(String destinationDir) {
        this.destinationDir = destinationDir;
    }


}
