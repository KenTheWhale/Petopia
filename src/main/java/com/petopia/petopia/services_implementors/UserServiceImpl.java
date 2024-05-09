package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.*;
import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.CreateUserProfileRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.PetRepo;
import com.petopia.petopia.repositories.ServiceReportRepo;
import com.petopia.petopia.repositories.UserRepo;
import com.petopia.petopia.repositories.*;
import com.petopia.petopia.services.AccountService;
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

import java.time.LocalDateTime;
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
    private final AccountService accountService;
    private final AppointmentRepo appointmentRepo;
    private final TokenRepo tokenRepo;
    private final NotificationRepo notificationRepo;
    private final ServiceCenterRepo serviceCenterRepo;

    @Override
    public CurrentUserResponse getCurrentUserProfile() {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;

        Token accessToken = tokenRepo.findByAccount_IdAndType(currentAcc.getId(), "Truy Cập").orElse(null);
        String accessTokenValue = accessToken != null ? accessToken.getValue() : "No access token";

        return CurrentUserResponse.builder()
                .status("200")
                .message("Get current user profile successfully")
                .user(
                        CurrentUserResponse.userResponse.builder()
                                .id(currentAcc.getId())
                                .name(currentAcc.getName())
                                .email(currentAcc.getEmail())
                                .avatarLink(currentAcc.getAvatarLink())
                                .status(currentAcc.getAccountStatus().getStatus())
                                .accessToken(accessTokenValue)
                                .appointmentList(appointmentRepo.findAllByPet_User_Id(currentAcc.getId()).stream()
                                        .map(appointment -> CurrentUserResponse.userResponse.appointmentResponse.builder()
                                                .id(appointment.getId())
                                                .pet(appointment.getPet())
                                                .appointmentStatus(CurrentUserResponse.userResponse.appointmentResponse.appointmentStatusResponse.builder()
                                                        .status(appointment.getAppointmentStatus().getStatus())
                                                        .build())
                                                .date(appointment.getDate())
                                                .build())
                                        .collect(Collectors.toList()))
                                .shop(currentAcc.getShop() != null ?
                                        CurrentUserResponse.userResponse.shopResponse.builder()
                                                .id(currentAcc.getShop().getId())
                                                .name(currentAcc.getShop().getName())
                                                .address(currentAcc.getShop().getAddress())
                                                .build() : null
                                )
                                .role(currentAcc.getRole())
                                .build())
                .build();
    }

    @Override
    public BlackListResponse viewBlackList() {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;

        List<Integer> blackListUserIdList = currentAcc.getUser().getBlackListUserIdList();
        List<BlackListResponse.blackList> blackListResponses = new ArrayList<>();

        if (blackListUserIdList != null) {
            blackListResponses = blackListUserIdList.stream()
                    .map(blackList -> {
                        User user = userRepo.findById(blackList).orElse(null);
                        if (user != null) {
                            return BlackListResponse.blackList.builder()
                                    .userData(BlackListResponse.blackList.userResponse.builder()
                                            .id(user.getId())
                                            .name(user.getAccount().getName())
                                            .avatarLink(user.getAccount().getAvatarLink())
                                            .build())
                                    .build();
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return BlackListResponse.builder()
                .status("200")
                .message("Get black list successfully")
                .blackList(blackListResponses)
                .build();
    }

    @Override
    public NotificationResponse viewNotification() {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;
        Notification notification = notificationRepo.findNotificationByUser_Id(currentAcc.getId());
        if(notification != null) {
            return NotificationResponse.builder()
                    .status("200")
                    .message("Get notification successfully")
                    .id(notification.getId())
                    .content(notification.getContent())
                    .build();
        }
        return NotificationResponse.builder()
                .status("400")
                .message("This user has no notification")
                .id(0)
                .content("")
                .build();
    }


    @Override
    public HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request) {
        Pet pet = petRepo.findById(request.getPetId()).orElse(null);
        if (pet != null) {
            Page<ServiceReport> serviceReports = getPaginationHealthReportListByPetId(request.getPage(), request.getPetId(), request.getSort());
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
        Account currentAcc = accountService.getCurrentLoggedAccount();
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
    public CreateAppointmentResponse createAppointment(CreateAppointmentRequest request, String type) {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;

        String appointmentType = type.equals("health")? Const.APPOINTMENT_TYPE_HEALTH: Const.APPOINTMENT_TYPE_SERVICE;

        ServiceCenter serviceCenter = serviceCenterRepo.findServiceCenterByName(request.getServiceCenterName()).orElse(null);
        if(serviceCenter == null) {
            return CreateAppointmentResponse.builder()
                    .status("400")
                    .message("Can not find service center with this name")
                    .build();
        }
        Pet pet = petRepo.findByNameAndUser_Account_Id(request.getPetName(), currentAcc.getId());
        if (pet == null) {
            return CreateAppointmentResponse.builder()
                    .status("400")
                    .message("Can not find pet with this name")
                    .build();
        }
        return CreateAppointmentResponse.builder()
                .status("200")
                .message("Create appointment successfully")
                .appointment(
                        CreateAppointmentResponse.appointmentDraft.builder()
                                .petName(pet.getName())
                                .status(Const.APPOINTMENT_STATUS_PENDING)
                                .date(LocalDateTime.now())
                                .location(serviceCenter.getAddress())
                                .service(serviceCenter.getServiceList().stream()
                                        .map(service -> new  CreateAppointmentResponse.serviceList(service.getId(), service.getName()))
                                        .collect(Collectors.toList()))
                                .type(appointmentType)
                                .build()
                )
                .build();
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



    private Page<ServiceReport> getPaginationHealthReportListByPetId(int pageNo, Integer petID, String sort) {
        Pageable pageable = PageRequest.of(pageNo, Const.PAGE_SIZE, Sort.by(sort));
        return serviceReportRepo.findAllByAppointment_Pet_IdAndAppointment_TypeAndAppointment_AppointmentStatus_Status(petID, Const.APPOINTMENT_TYPE_HEALTH, Const.APPOINTMENT_STATUS_SUCCESSFUL, pageable);
    }
}
