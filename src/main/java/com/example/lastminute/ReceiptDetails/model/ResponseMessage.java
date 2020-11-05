package com.example.lastminute.ReceiptDetails.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(builderMethodName = "newBuilder")
public class ResponseMessage {

    private Integer statusCode;
    private String message;
    private Object data;
}
