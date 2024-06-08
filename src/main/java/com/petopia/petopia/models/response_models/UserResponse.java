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

    private List<Image> images;
    private List<Group> groups;
    private List<Post> posts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Image {
        private String link;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Group {
        private String name;
        private String avatar;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Post {
        private Integer id;
        private String status;
        private String content;
        private LocalDateTime uploadDate;
        private List<Image> images;
        private List<PostLikedUser> postLikedUsers;
        private List<Comment> comments;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostLikedUser {
        private Integer id;
        private String name;
        private String avatar;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Comment {
        private String name;
        private String content;
    }
}
