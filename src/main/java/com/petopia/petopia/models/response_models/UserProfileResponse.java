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
    private Integer id;
    private String name;
    private String gender;
    private String address;
    private String phone;
    private List<String> imgLinkList;
    private List<Group> groupList;
    private List<Post> postList;
    private List<Comment> commentList;
    private List<Pet> petList;
    private List<Order> orderList;
    private List<Feedback> feedbackList;
    private List<BlackList> blackList;
}
