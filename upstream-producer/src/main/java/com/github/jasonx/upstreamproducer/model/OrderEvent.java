package com.github.jasonx.upstreamproducer.model;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderEvent {
    private String orderId;
    private String type;     // created | paid | shipped
    private long timestamp;
}