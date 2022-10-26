package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GenericResponse {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "statusCode")
    private Integer statusCode;
}
