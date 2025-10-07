package com.khahnm04.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String phoneNumber;
    private String password;

}
