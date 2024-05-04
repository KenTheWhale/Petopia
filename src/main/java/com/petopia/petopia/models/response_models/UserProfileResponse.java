package com.petopia.petopia.models.response_models;

import com.petopia.petopia.models.entity_models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class imgLinkResponse {
        private String link;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class groupListResponse {
        private String groupName;
        private String imgLink;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class postListResponse {
        private String id;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class commentListResponse {
        private String id;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class petListResponse {
        private String id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class orderListResponse {
        private String id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class feedbackListResponse {
        private String id;
    }

    private Integer id;
    private String name;
    private String gender;
    private String address;
    private String phone;
    private List<imgLinkResponse> imgLinkList;
    private List<groupListResponse> groupList;
    private List<postListResponse> postList;
    private List<commentListResponse> commentList;
    private List<petListResponse> petList;
    private List<orderListResponse> orderList;
    private List<feedbackListResponse> feedbackList;
    private List<BlackList> blackList;
}
