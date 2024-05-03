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
import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Pet;
import com.petopia.petopia.models.entity_models.ServiceReport;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.response_models.BasicResponse;
import com.petopia.petopia.models.response_models.HealthHistoryResponse;
import com.petopia.petopia.repositories.PetRepo;
import com.petopia.petopia.repositories.ServiceReportRepo;
import com.petopia.petopia.services.AuthenticationService;
import com.petopia.petopia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private final ServiceReportRepo serviceReportRepo;

    private final PetRepo petRepo;

    @Override
    public HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request) {
        Pet pet = petRepo.findById(request.getPetId()).orElse(null);
        if(pet != null){
            Page<ServiceReport> serviceReports = getPaginationHealthReportListByUserId(request.getPage(), request.getPetId(), request.getSort());
            List<HealthHistoryResponse.HealthReportResponse> list = new ArrayList<>();
            for(ServiceReport report: serviceReports){
                list.add(
                        HealthHistoryResponse.HealthReportResponse.builder()
                                .id(report.getId())
                                .date(report.getDate())
                                .report(report.getReport())
                                .status(Const.APPOINTMENT_STATUS_SUCCESSFUL_VIETNAMESE)
                                .extraContent(report.getExtraContent())
                                .place(report.getAppointment().getLocation())
                                .doctor(
                                        HealthHistoryResponse.DoctorResponse.builder()
                                                .id(report.getAppointment().getAccount().getId())
                                                .name(report.getAppointment().getAccount().getName())
                                                .avatarLink(report.getAppointment().getAccount().getAvatarLink())
                                                .build()
                                )
                                .build()
                );
            }

            return HealthHistoryResponse.builder()
                    .basicResponse(
                            BasicResponse.builder()
                                    .status("200")
                                    .message("Get list successfully")
                                    .build()
                    )
                    .totalPage(serviceReports.getTotalPages())
                    .petId(pet.getId())
                    .petName(pet.getName())
                    .petGender(pet.getGender())
                    .petAge(pet.getAge())
                    .petType(pet.getType())
                    .petNecklaceId(pet.getNecklaceId())
                    .petDescription(pet.getDescription())
                    .imgLinkList(pet.getImgLinkList())
                    .appointments(list)
                    .build();
        }
        return HealthHistoryResponse.builder()
                .basicResponse(
                        BasicResponse.builder()
                                .status("400")
                                .message("Fail to get list")
                                .build()
                )
                .totalPage(0)
                .petId(0)
                .petName("")
                .petGender("")
                .petAge(0)
                .petType("")
                .petNecklaceId("")
                .petDescription("")
                .imgLinkList(Collections.emptyList())
                .appointments(Collections.emptyList())
                .build();
    }

    private Page<ServiceReport> getPaginationHealthReportListByUserId(int pageNo, Integer petID, String sort) {
        Pageable pageable = PageRequest.of(pageNo, Const.PAGE_SIZE, Sort.by(sort));
        return serviceReportRepo.findAllByAppointment_Pet_IdAndAppointment_TypeAndAppointment_AppointmentStatus_Status(petID, Const.APPOINTMENT_TYPE_HEALTH, Const.APPOINTMENT_STATUS_SUCCESSFUL, pageable);
    }


}
