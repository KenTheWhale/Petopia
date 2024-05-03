package com.petopia.petopia.services_implementors;

import com.petopia.petopia.models.entity_models.User;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.BlackListResponse;
import com.petopia.petopia.models.response_models.UserProfileResponse;
import com.petopia.petopia.repositories.UserRepo;
import com.petopia.petopia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public ResponseEntity<?> getUserProfile(UserRequest userRequest) {

        // Validate the userId
        if (userRequest.getId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID.");
        }

        Optional<User> optionalUser = userRepo.findById(userRequest.getId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.status(HttpStatus.OK).body(UserProfileResponse
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .gender(user.getGender())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .imgLinkList(user.getImgLinkList())
                    .groupList(user.getGroupList())
                    .postList(user.getPostList())
                    .commentList(user.getCommentList())
                    .petList(user.getPetList())
                    .orderList(user.getOrderList())
                    .feedbackList(user.getFeedbackList())
                    .blackList(user.getBlackList())
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + userRequest.getId() + " not found.");
        }
    }

    public String getImgLink(int id) {
        User user = userRepo.findById(id).orElseThrow();
        String img = user.getImgLinkList().get(0);
        if (img != null) {
            return img;
        }
        return "No Avatar";
    }

    @Override
    public ResponseEntity<?> viewBlackList(UserRequest userRequest) {

        if (userRequest.getId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID.");
        }

        Optional<User> optionalUser = userRepo.findById(userRequest.getId());


        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getImgLinkList() == null) {
                user.setImgLinkList(new ArrayList<>());
            }
            // Add a string to imgLinkList
            String newImgLink = "Avatar Link";
            user.getImgLinkList().add(newImgLink);

            return ResponseEntity.status(HttpStatus.OK).body(BlackListResponse
                    .builder()
                    .id(user.getId())
                    .imgLink(getImgLink(user.getId()))
                    .name(user.getName())
                    .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + userRequest.getId() + " not found.");
        }
    }

}
