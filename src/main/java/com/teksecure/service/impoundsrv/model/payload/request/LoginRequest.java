package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "password")
    private String password;
}
