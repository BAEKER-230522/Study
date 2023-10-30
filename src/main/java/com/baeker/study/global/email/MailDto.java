package com.baeker.study.global.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailDto {

    private String address;
    private String subject;
    private String text;
}
