package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.*;
import com.petopia.petopia.models.request_models.BlockUserRequest;
import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.CreateUserProfileRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.ServiceRequest;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.PetRepo;
import com.petopia.petopia.repositories.ServiceReportRepo;
import com.petopia.petopia.repositories.UserRepo;
import com.petopia.petopia.repositories.*;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final AppointmentStatusRepo appointmentStatusRepo;
    private final ServiceRepo serviceRepo;
    private final AppointmentServiceRepo appointmentServiceRepo;
    private final AccountStatusRepo accountStatusRepo;
    private final BlackListRepo blackListRepo;


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
//                                .appointmentList(appointmentRepo.findAllByPet_User_Id(currentAcc.getId()).stream()
//                                        .map(appointment -> CurrentUserResponse.userResponse.appointmentResponse.builder()
//                                                .id(appointment.getId())
//                                                .pet(appointment.getPet())
//                                                .appointmentStatus(CurrentUserResponse.userResponse.appointmentResponse.appointmentStatusResponse.builder()
//                                                        .status(appointment.getAppointmentStatus().getStatus())
//                                                        .build())
//                                                .date(appointment.getDate())
//                                                .build())
//                                        .collect(Collectors.toList()))
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

        List<BlackList> blackList = currentAcc.getUser().getBlackList();

        return BlackListResponse.builder()
                    .status("200")
                    .message("Get black list successfully")
                    .blockedUsers(blackList.stream()
                            .map(element -> BlackListResponse.BlockedUser.builder()
                                    .id(userRepo.findUserById(element.getBlockedUserId()).getId())
                                    .name(userRepo.findUserById(element.getBlockedUserId()).getAccount().getName())
                                    .avatarLink(userRepo.findUserById(element.getBlockedUserId()).getAccount().getAvatarLink())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();


    }

    @Override
    public NotificationResponse viewNotification() {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;
        List<Notification> notification = notificationRepo.findAllByUser_Id(currentAcc.getUser().getId());
        if (notification != null) {
            return NotificationResponse.builder()
                    .status("200")
                    .message("Get notification successfully")
                    .notifications(notification.stream()
                            .map(notify -> NotificationResponse.Notificationn.builder()
                                    .id(notify.getId())
                                    .content(notify.getContent())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        }
        return NotificationResponse.builder()
                .status("400")
                .message("This user has no notification")
                .notifications(null)
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

        String appointmentType = type.equals("health") ? Const.APPOINTMENT_TYPE_HEALTH : Const.APPOINTMENT_TYPE_SERVICE;

        ServiceCenter serviceCenter = serviceCenterRepo.findServiceCenterById(request.getCenterId()).orElse(null);

        List<Services> serviceList = new ArrayList<>();

        for (Integer id : request.getServiceId()) {
            Optional<Services> sv = serviceRepo.findById(id);
            serviceList.add(sv.get());
        }
        if (serviceCenter == null) {
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

        AppointmentStatus appointmentStatus = appointmentStatusRepo.findByStatus(Const.APPOINTMENT_STATUS_PENDING);

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .serviceProvider(serviceCenter.getServiceProviderList().get(1))
                .appointmentStatus(appointmentStatus)
                .date(LocalDateTime.now())
                .fee(calculateSumOfFees(request))
                .type(appointmentType)
                .build();

        // Save the Appointment
        Appointment savedAppointment = appointmentRepo.save(appointment);
        saveServiceListToAppointment(savedAppointment, serviceList);

        return CreateAppointmentResponse.builder()
                .status("200")
                .message("Create appointment successfully")
                .appointment(
                        CreateAppointmentResponse.appointmentDraft.builder()
                                .petName(savedAppointment.getPet().getName())
                                .status(Const.APPOINTMENT_STATUS_PENDING)
                                .date(LocalDateTime.now())
                                .location(savedAppointment.getServiceProvider().getServiceCenter().getAddress())
                                .services(serviceList.stream()
                                        .map(service -> CreateAppointmentResponse.Servicee.builder()
                                                .id(service.getId())
                                                .name(service.getName())
                                                .build())
                                        .collect(Collectors.toList()))
                                .type(appointmentType)
                                .build()
                )
                .build();
    }

    public double calculateSumOfFees(CreateAppointmentRequest request) {
        double sum = 0;
        for (Integer id : request.getServiceId()) {
            Optional<Services> sv = serviceRepo.findById(id);
            if (sv.isPresent()) {
                Services services = sv.get();
                sum += services.getFee();
            }
        }
        return sum;
    }

    //save list of services to appointment
    public void saveServiceListToAppointment(Appointment appointment, List<Services> serviceList) {
        for (Services service : serviceList) {
            AppointmentService appointmentService = AppointmentService.builder()
                    .appointment(appointment)
                    .services(service)
                    .build();
            if (appointment.getAppointmentServiceList() == null) {
                appointment.setAppointmentServiceList(new ArrayList<>());
            }
            appointment.getAppointmentServiceList().add(appointmentService);
            appointmentServiceRepo.save(appointmentService);
        }
    }

    @Override
    public ServiceListResponse getServiceList(ServiceRequest request) {
        ServiceCenter serviceCenter = serviceCenterRepo.findServiceCenterById(request.getCenterId()).orElse(null);
        if (serviceCenter == null) {
            return ServiceListResponse.builder()
                    .status("400")
                    .message("Can not find service center with this id")
                    .serviceList(Collections.emptyList())
                    .build();
        }
        return ServiceListResponse.builder()
                .status("200")
                .message("Get list successfully")
                .serviceList(serviceCenter.getServicesList().stream()
                        .map(service -> new ServiceListResponse.ServiceList(service.getId(), service.getName(), service.getServiceCenter().getType(), service.getFee()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public LoadServicePageResponse loadServicePage(String type) {
        String types = type.equals("health") ? Const.APPOINTMENT_TYPE_HEALTH : Const.APPOINTMENT_TYPE_SERVICE;
        List<ServiceCenter> serviceCenterList = getServiceCenterList(types);
        List<Services> serviceList = getHealthServiceList(types);
        return LoadServicePageResponse.builder()
                .status("200")
                .message("Get service page successfully")
                .serviceCenters(serviceCenterList.stream()
                        .map(serviceCenter -> LoadServicePageResponse.ServiceCenter.builder()
                                .id(serviceCenter.getId())
                                .name(serviceCenter.getName())
                                .address(serviceCenter.getAddress())
                                .rating(serviceCenter.getRating())
                                .build())
                        .collect(Collectors.toList()))
                .services(serviceList.stream()
                        .map(service -> LoadServicePageResponse.Service.builder()
                                .id(service.getId())
                                .serviceName(service.getName())
                                .rating(service.getRating())
                                .servicePrice(service.getFee())
                                .build())
                        .collect(Collectors.toList()))
                .build();

    }

    @Override
    public BlackListResponse blockUser(BlockUserRequest request) {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;
        User blockedUser = userRepo.findById(request.getUserId()).orElse(null);
        if (blockedUser == null) {
            return BlackListResponse.builder()
                    .status("400")
                    .message("Can not find user with this id")
                    .build();
        }
        //check block yourself
        if (request.getUserId().equals(currentAcc.getUser().getId())) {
            return BlackListResponse.builder()
                    .status("400")
                    .message("Can not block yourself")
                    .build();
        }

        BlackList blackList = BlackList.builder()
                .user(currentAcc.getUser())
                .blockedUserId(blockedUser.getId())
                .build();

        blackListRepo.save(blackList);

        List<BlackList> blockedUserList = new ArrayList<>();
        blockedUserList.add(blackList);

        return BlackListResponse.builder()
                .status("200")
                .message("Block user successfully")
                //return the black list
                .blockedUsers(blockedUserList.stream()
                        .map(user -> BlackListResponse.BlockedUser.builder()
                                .id(user.getBlockedUserId())
                                .name(user.getUser().getAccount().getName())
                                .avatarLink(user.getUser().getAccount().getAvatarLink())
                                .build())
                        .collect(Collectors.toList()))
                .build();

    }

    private List<ServiceCenter> getServiceCenterList(String type) {
        return serviceCenterRepo.findAllByTypeAndServiceCenterStatus_StatusOrServiceCenterStatus_StatusOrderByRatingDesc(type, Const.SERVICE_CENTER_STATUS_ACTIVE, Const.SERVICE_CENTER_STATUS_CLOSED);
    }

    private List<Services> getHealthServiceList(String type) {
        return serviceRepo.findAllByServiceCenter_TypeAndServiceStatus_StatusOrderByRatingDesc(type, Const.SERVICE_STATUS_ACTIVE);
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
