package com.example.demo.entity;

import org.springframework.web.multipart.MultipartFile;

public class DocsFile {

    private String name;
    private String type;
    private MultipartFile docsFile;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getDocsFile() {
        return docsFile;
    }

    public void setDocsFile(MultipartFile docsFile) {
        this.docsFile = docsFile;
    }


}
