package com.bobocode.picture.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Pictures {
    @JsonProperty("photos")
    private List<Picture> pictures;
}
