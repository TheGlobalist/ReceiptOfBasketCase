package com.example.lastminute.ReceiptDetails.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(builderMethodName = "newBuilder")
@Getter
public class ResponseMessage {

    //prova per travisci
    private Integer statusCode;
    private String message;
    private Object data;
}
