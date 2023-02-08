package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class SignupRequest {
    @JsonProperty(value = "username", required = true)
    private String username;

    @JsonProperty(value = "email", required = true)
    private String email;

    @JsonProperty(value = "role", required = true)
    private Set<String> role;

    @JsonProperty(value = "password", required = true)
    private String password;
}
