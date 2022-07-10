package com.bobocode.picture.controller;

import com.bobocode.picture.exception.PictureNotFoundException;
import com.bobocode.picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/pictures")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/{sol}/largest")
    public ResponseEntity<String> getLargestPicture(@PathVariable Integer sol) {

        var maxSizePicture = pictureService.getMaxSizePicture(sol)
                .orElseThrow(() -> new PictureNotFoundException("Picture with max size not found!"));

        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(URI.create(maxSizePicture.getImgSrc())).build();
    }
}
