package com.teksecure.service.impoundsrv.model.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class SignupRequest {
    private String username;

    private String email;

    private Set<String> role;

    private String password;
}