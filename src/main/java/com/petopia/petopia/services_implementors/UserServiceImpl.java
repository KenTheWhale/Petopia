package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Pet;
import com.petopia.petopia.models.entity_models.ServiceReport;
import com.petopia.petopia.models.entity_models.User;
import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.CreateUserProfileRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.PetRepo;
import com.petopia.petopia.repositories.ServiceReportRepo;
import com.petopia.petopia.repositories.UserRepo;
import com.petopia.petopia.services.AuthenticationService;
import com.petopia.petopia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ServiceReportRepo serviceReportRepo;
    private final PetRepo petRepo;
    private final AuthenticationService authenticationService;
    private final AccountRepo accountRepo;

    @Override
    public CurrentUserResponse getCurrentUserProfile() {
//        Account currentAcc = authenticationService.getCurrentLoggedUser();
//        assert currentAcc != null;
//        return CurrentUserResponse.builder()
//                .status("200")
//                .message("Get current user profile successfully")
//                .user(
//                        CurrentUserResponse.userResponse.builder()
//                                .id(currentAcc.getId())
//                                .name(currentAcc.getName())
////                                .password(currentAcc.getPassword())
//                                .email(currentAcc.getEmail())
//                                .avatarLink(currentAcc.getAvatarLink())
//                                .accountStatus(CurrentUserResponse.userResponse.accountStatusResponse.builder()
//                                        .status(currentAcc.getAccountStatus().getStatus())
//                                        .build())
//                                .tokenList(currentAcc.getTokenList().stream()
//                                        .map(token -> CurrentUserResponse.userResponse.tokenListResponse.builder()
//                                                .id(token.getId())
//                                                .tokenStatus(CurrentUserResponse.userResponse.tokenListResponse.tokenStatusResponse.builder()
//                                                        .status(token.getTokenStatus().getStatus())
//                                                        .build())
//                                                .value(token.getValue())
//                                                .type(token.getType())
//                                                .build())
//                                        .collect(Collectors.toList()))
//                                .appointmentList(currentAcc.getAppointmentList().stream()
//                                        .map(appointment -> CurrentUserResponse.userResponse.appointmentResponse.builder()
//                                                .id(appointment.getId())
//                                                .pet(appointment.getPet())
//                                                .appointmentStatus(CurrentUserResponse.userResponse.appointmentResponse.appointmentStatusResponse.builder()
//                                                        .status(appointment.getAppointmentStatus().getStatus())
//                                                        .build())
//                                                .date(appointment.getDate())
//                                                .location(appointment.getLocation())
//                                                .build())
//                                        .collect(Collectors.toList()))
//                                .shop(currentAcc.getShop() != null ?
//                                        CurrentUserResponse.userResponse.shopResponse.builder()
//                                                .id(currentAcc.getShop().getId())
//                                                .name(currentAcc.getShop().getName())
//                                                .address(currentAcc.getShop().getAddress())
//                                                .build() : null
//                                )
//                                .role(currentAcc.getRole())
//                                .build())
//                .build();

        return null;
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
    public BlackListResponse viewBlackList() {
//        Account currentAcc = authenticationService.getCurrentLoggedUser();
//        assert currentAcc != null;
//        return BlackListResponse.builder()
//                .status("200")
//                .message("Get black list successfully")
//                .blackList(currentAcc.getUser().getBlackList().stream()
//                        .map(blackList -> BlackListResponse.blackList.builder()
//                                .id(blackList.getId())
//                                .userData(BlackListResponse.blackList.userResponse.builder()
//                                        .id(blackList.getUser().getId())
//                                        .account(blackList.getUser().getAccount() != null ?
//                                                BlackListResponse.blackList.userResponse.accountResponse.builder()
//                                                        .name(blackList.getUser().getAccount().getName())
//                                                        .build() : null)
//                                        .avatarLink(getImgLink(blackList.getUser().getId()))
//                                        .build())
//                                .build())
//                        .collect(Collectors.toList()))
//                .build();
        return null;
    }

    @Override
    public ResponseEntity<?> viewNotification(UserRequest userRequest) {
        if (userRequest.getId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID.");
        }
        Optional<User> optionalUser = userRepo.findById(userRequest.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getNotificationList() == null) {
                user.setNotificationList(new ArrayList<>());
            }
            List<NotificationResponse> notificationResponses = user.getNotificationList().stream()
                    .map(notification -> new NotificationResponse(notification.getId(), notification.getContent(), notification.getStatus()))
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(notificationResponses);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + userRequest.getId() + " not found.");
        }
    }


    @Override
    public HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request) {
        Pet pet = petRepo.findById(request.getPetId()).orElse(null);
        if (pet != null) {
            Page<ServiceReport> serviceReports = getPaginationHealthReportListByUserId(request.getPage(), request.getPetId(), request.getSort());
            List<HealthHistoryResponse.HealthReportResponse> list = new ArrayList<>();
            for (ServiceReport report : serviceReports) {
                list.add(
                        HealthHistoryResponse.HealthReportResponse.builder()
                                .id(report.getId())
                                .date(report.getDate())
                                .report(report.getReport())
                                .status(Const.APPOINTMENT_STATUS_SUCCESSFUL)
                                .extraContent(report.getExtraContent())
                                .doctor(
                                        HealthHistoryResponse.DoctorResponse.builder()
                                                .id(report.getAppointment().getServiceProvider().getAccount().getId())
                                                .name(report.getAppointment().getServiceProvider().getAccount().getName())
                                                .avatarLink(report.getAppointment().getServiceProvider().getAccount().getAvatarLink())
                                                .build()
                                )
                                .build()
                );
            }

            return HealthHistoryResponse.builder()
                    .status("200")
                    .message("Get list successfully")
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
                .status("400")
                .message("Can not find pet")
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

    @Override
    public PetListResponse getPetList() {
        Account currentAcc = authenticationService.getCurrentLoggedUser();
        assert currentAcc != null;
        List<Pet> pets = petRepo.findAllByUser_Account_IdOrderById(currentAcc.getId());
        return PetListResponse.builder()
                .status("200")
                .message("Get list successfully")
                .petList(pets.stream()
                        .map(pet -> new PetListResponse.PetResponse(pet.getId(), pet.getName(), pet.getGender(), pet.getAge(), pet.getType(), pet.getNecklaceId(), pet.getDescription(), pet.getImgLinkList()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CreateAppointmentResponse createAppointment(CreateAppointmentRequest request) {
        return null;
    }

    @Override
    public CreateUserProfileResponse createUserProfile(int id, CreateUserProfileRequest request) {
        // Kiểm tra xem accountId đã tồn tại trong bảng User hay chưa
        Optional<User> existingUser = userRepo.findByAccountId(id);
        if (existingUser.isPresent()) {
            // Nếu đã tồn tại, trả về thông báo lỗi
            return CreateUserProfileResponse.builder()
                    .status("409")
                    .message("User profile for this account already exists")
                    .build();
        }

        Optional<Account> optionalAccount = accountRepo.findById(id);
        if (optionalAccount.isEmpty()) {
            return CreateUserProfileResponse.builder()
                    .status("404")
                    .message("User does not exist")
                    .build();
        }

        Account account = optionalAccount.get();
        User user = User.builder()
                .account(account)
                .gender(request.getGender())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
        userRepo.save(user);

        return CreateUserProfileResponse.builder()
                .address(user.getAddress())
                .gender(user.getGender())
                .phone(user.getPhone())
                .status("200")
                .message("User profile created successfully")
                .build();
    }


    private Page<ServiceReport> getPaginationHealthReportListByUserId(int pageNo, Integer petID, String sort) {
        Pageable pageable = PageRequest.of(pageNo, Const.PAGE_SIZE, Sort.by(sort));
        return serviceReportRepo.findAllByAppointment_Pet_IdAndAppointment_TypeAndAppointment_AppointmentStatus_Status(petID, Const.APPOINTMENT_TYPE_HEALTH, Const.APPOINTMENT_STATUS_SUCCESSFUL, pageable);
    }
}
