package com.example.lastminute.ReceiptDetails.model;

import lombok.Builder;
import lombok.Getter;

@Builder(builderMethodName = "newBuilder")
@Getter
public class ResponseMessage {

    private Integer statusCode;
    private String message;
    private Object data;
}
