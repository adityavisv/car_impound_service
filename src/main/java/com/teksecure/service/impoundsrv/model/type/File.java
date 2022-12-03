package com.teksecure.service.impoundsrv.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class File {
    private String base64EncodedBlob;

    private String contentType;
}
