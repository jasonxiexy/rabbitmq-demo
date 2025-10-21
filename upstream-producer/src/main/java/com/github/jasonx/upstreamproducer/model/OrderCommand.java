package com.github.jasonx.upstreamproducer.model;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderCommand {
    private String orderId;
    private String userId;
    private String sku;
    private int quantity;
}
