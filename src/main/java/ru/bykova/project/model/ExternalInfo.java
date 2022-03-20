package ru.bykova.project.model;

import lombok.Data;

@Data
public class ExternalInfo {
    private Integer id;
    private String info;

    public ExternalInfo(Integer id, String info) {
        this.id = id;
        this.info = info;
    }
}
