package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {

    private String status;

    private String message;

    private List<Notificationn> notifications;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Notificationn {
        private int id;

        private String content;
    }
}
