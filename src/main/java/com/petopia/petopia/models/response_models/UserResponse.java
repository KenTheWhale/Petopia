package com.petopia.petopia.models.response_models;

import com.petopia.petopia.models.entity_models.Comment;
import com.petopia.petopia.models.entity_models.PostImage;
import com.petopia.petopia.models.entity_models.PostLikedUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String status;

    private String message;

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
    private List<blackListResponse> blackList;

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
        private Integer id;
        private String status;
        private String content;
        private LocalDateTime postDate;
        private List<PostImageList> postImageList;
        private List<PostLikedUserList> postLikedUserList;
        private List<CommentList> commentList;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostImageList {
        private String imgLink;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostLikedUserList {
        private Integer id;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentList {
        private String name;
        private List<imgLinkResponse> imgLink;
        private String content;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class commentListResponse {
        private Integer id;
        private String content;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class petListResponse {
        private Integer id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class orderListResponse {
        private Integer id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class feedbackListResponse {
        private Integer id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class blackListResponse {
        private Integer id;
        private String name;
        private String imgLink;
    }
}
