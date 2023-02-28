package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.ImageEntity;
import com.teksecure.service.impoundsrv.model.type.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ImageListPayload {

    @JsonProperty(value = "images")
    private List<File> images;

    public ImageListPayload(List<ImageEntity> imageEntityEntities) {
        images = new ArrayList<>();
        for (ImageEntity imageEntity : imageEntityEntities) {
            File file = new File(Base64.getEncoder().encodeToString(imageEntity.getBytes()), imageEntity.getFiletype());
            images.add(file);
        }
    }

}
