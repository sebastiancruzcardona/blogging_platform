package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status", length = 15)
    private String status;

    public Status() {
        super();
    }

    public Status(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public Status(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
