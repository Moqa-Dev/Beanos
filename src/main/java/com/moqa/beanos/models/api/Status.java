package com.moqa.beanos.models.api;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    private int code = 200;

    private String errorMsg;

    private String error;

}
