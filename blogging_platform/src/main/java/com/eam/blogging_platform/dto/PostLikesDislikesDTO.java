package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;

public class PostLikesDislikesDTO {

    @Min(1)
    private int likes;

    @Min(1)
    private int dislikes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}