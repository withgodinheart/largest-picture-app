package com.bobocode.picture.service;

import com.bobocode.picture.entity.Picture;
import com.bobocode.picture.entity.Pictures;
import com.bobocode.picture.exception.PictureNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureService {

    @NonNull
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
    private static final String API_KEY_PARAM = "DEMO_KEY";

    @Cacheable("maxSizePicture")
    public Optional<Picture> getMaxSizePicture(Integer sol) {

        var pictures = getPicturesFromJson(sol)
                .orElseThrow(() -> new PictureNotFoundException("Unexpected error while getting all pictures!"));

        return pictures.getPictures().parallelStream()
                .peek(picture -> {
                    var redirectUrl = getRedirectUrl(picture.getImgSrc()).toString();
                    picture.setImgSrc(redirectUrl);
                    picture.setSize(getContentLength(redirectUrl));
                })
                .max(Comparator.comparing(Picture::getSize));
    }

    private Optional<Pictures> getPicturesFromJson(Integer sol) {
        var urlWithParameters = BASE_URL + "?sol=" + sol + "&api_key=" + API_KEY_PARAM;
        return Optional.ofNullable(restTemplate.getForObject(urlWithParameters, Pictures.class));
    }

    private URI getRedirectUrl(String url) {
        return restTemplate.headForHeaders(url).getLocation();
    }

    private long getContentLength(String url) {
        return restTemplate.headForHeaders(url).getContentLength();
    }
}
