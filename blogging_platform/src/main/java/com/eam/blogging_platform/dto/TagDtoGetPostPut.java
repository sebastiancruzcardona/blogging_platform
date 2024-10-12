package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Tag;

import java.time.LocalDateTime;


public class TagDtoGetPostPut {

    private Long id;
    private String tag;
    private LocalDateTime creation_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    //This method receives a Tag and sets its attributes to the TagDTOGetPuTPost objectPublic void convertToTagDTO(Tag tag) {
    public void convertToTagDTO(Tag tag) {
        this.setId(tag.getId());
        this.setTag(tag.getTag());
        this.setCreation_date(tag.getCreationDate());
    }

}
