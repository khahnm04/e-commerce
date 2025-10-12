package com.khahnm04.dto.request;

import com.khahnm04.constant.Gender;
import com.khahnm04.constant.Status;

import java.io.Serializable;
import java.time.LocalDate;

public class UserUpdateRequest implements Serializable {

    private String username;

    private String email;

    private String phoneNumber;

    private String password;

    private String fullName;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String image;

    private Status status;

}
