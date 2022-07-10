package com.bobocode.picture.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Picture {
    @JsonProperty("img_src")
    private String imgSrc;
    private Long size;
}
