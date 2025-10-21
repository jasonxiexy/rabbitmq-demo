package com.github.jasonx.upstreamproducer.model;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SystemAlert {
    private String severity; // INFO | WARN | ERROR
    private String message;
    private long timestamp;
}