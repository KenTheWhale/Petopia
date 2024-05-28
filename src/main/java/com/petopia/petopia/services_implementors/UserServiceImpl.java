package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.*;
import com.petopia.petopia.models.request_models.BlockAndUnblockUserRequest;
import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.CreateUserProfileRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.ServiceRequest;
import com.petopia.petopia.models.request_models.*;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    private final BlackListRepo blackListRepo;
    private final PetImageRepo petImageRepo;
    private final ServiceCenterImageRepo serviceCenterImageRepo;
    private final ServiceImageRepo serviceImageRepo;


    @Override
    public CurrentUserResponse getCurrentUserProfile() {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;

        Token accessToken = tokenRepo.findByAccount_IdAndType(currentAcc.getId(), "Truy Cập").orElse(null);
        String accessTokenValue = accessToken != null ? accessToken.getValue() : "No access token";

        return CurrentUserResponse.builder()
                .status("200")
                .message("Lấy thông tin người dùng thành công")
                .user(
                        CurrentUserResponse.userResponse.builder()
                                .id(currentAcc.getId())
                                .name(currentAcc.getName())
                                .email(currentAcc.getEmail())
                                .avatarLink(currentAcc.getAvatar())
                                .status(currentAcc.getAccountStatus().getStatus())
                                .accessToken(accessTokenValue)
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
        if (blackList.isEmpty()) {
            return BlackListResponse.builder()
                    .status("400")
                    .message("Người dùng không có ai trong danh sách chặn")
                    .build();
        }
        return BlackListResponse.builder()
                    .status("200")
                    .message("Lấy danh sách chặn thành công")
                    .blockedUsers(blackList.stream()
                            .map(element -> BlackListResponse.BlockedUser.builder()
                                 .id(userRepo.findUserById(element.getBlockedUserId()).getId())
                                 .name(userRepo.findUserById(element.getBlockedUserId()).getAccount().getName())
                                 .avatarLink(userRepo.findUserById(element.getBlockedUserId()).getAccount().getAvatar())
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
                    .message("Lấy thông báo thành công")
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
                .message("Người dùng không có thông báo")
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
                                                .avatarLink(report.getAppointment().getServiceProvider().getAccount().getAvatar())
                                                .build()
                                )
                                .build()
                );
            }

            return HealthHistoryResponse.builder()
                    .status("200")
                    .message("Lấy lịch sử khám sức khỏe thành công")
                    .totalPage(serviceReports.getTotalPages())
                    .petId(pet.getId())
                    .petName(pet.getName())
                    .petGender(pet.getGender())
                    .petAge(pet.getAge())
                    .petType(pet.getType())
                    .petNecklaceId(pet.getNecklaceId())
                    .petDescription(pet.getDescription())
                    .imgLinkList(petImageRepo.findAllImageByPetId(pet.getId()))
                    .appointments(list)
                    .build();
        }
        return HealthHistoryResponse.builder()
                .status("400")
                .message("Không tìm thấy thú cưng với id này")
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
                .message("Lấy danh sách thú cưng thành công")
                .petList(pets.stream()
                        .map(pet -> new PetListResponse.PetResponse(pet.getId(), pet.getName(), pet.getGender(), pet.getAge(), pet.getType(), pet.getNecklaceId(), pet.getDescription(), petImageRepo.findAllImageByPetId(pet.getId())))
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
                    .message("Không thể tìm thấy trung tâm dịch vụ với id này")
                    .build();
        }
        Pet pet = petRepo.findByNameAndUser_Account_Id(request.getPetName(), currentAcc.getId());
        if (pet == null) {
            return CreateAppointmentResponse.builder()
                    .status("400")
                    .message("Không tìm thấy thú cưng với tên này")
                    .build();
        }

        AppointmentStatus appointmentStatus = appointmentStatusRepo.findByStatus(Const.APPOINTMENT_STATUS_PENDING);

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .serviceProvider(serviceCenter.getServiceProviderList().get(1))
                .appointmentStatus(appointmentStatus)
                .date(request.getDateTime())
                .fee(calculateSumOfFees(request))
                .type(appointmentType)
                .extraInformation(request.getExtraInformation())
                .build();

        // Save the Appointment
        Appointment savedAppointment = appointmentRepo.save(appointment);
        savedAppointment.setServicesList(serviceList);

        return CreateAppointmentResponse.builder()
                .status("200")
                .message("Tạo lịch hẹn thành công")
                .appointment(
                        CreateAppointmentResponse.appointmentDraft.builder()
                                .petName(savedAppointment.getPet().getName())
                                .status(Const.APPOINTMENT_STATUS_PENDING)
                                .date(request.getDateTime())
                                .location(savedAppointment.getServiceProvider().getServiceCenter().getAddress())
                                .services(serviceList.stream()
                                        .map(service -> CreateAppointmentResponse.Servicee.builder()
                                                .id(service.getId())
                                                .name(service.getName())
                                                .build())
                                        .collect(Collectors.toList()))
                                .type(appointmentType)
                                .extraInformation(request.getExtraInformation())
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

    @Override
    public ServiceListResponse getServiceList(ServiceCenterRequest request) {
        ServiceCenter serviceCenter = serviceCenterRepo.findServiceCenterById(request.getCenterId()).orElse(null);
        if (serviceCenter == null) {
            return ServiceListResponse.builder()
                    .status("400")
                    .message("Không thể tìm thấy trung tâm dịch vụ với id này")
                    .serviceList(Collections.emptyList())
                    .build();
        }
        return ServiceListResponse.builder()
                .status("200")
                .message("Lấy danh sách dịch vụ thành công")
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
                .message("Lấy trang dịch vụ thành công")
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
    public BlackListResponse blockUser(BlockAndUnblockUserRequest request) {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;
        User blockedUser = userRepo.findById(request.getBlockUserId()).orElse(null);
        if (blockedUser == null) {
            return BlackListResponse.builder()
                    .status("400")
                    .message("Không thể tìm thấy người dùng với id này")
                    .build();
        }
        //check block yourself
        if (request.getBlockUserId().equals(currentAcc.getUser().getId())) {
            return BlackListResponse.builder()
                    .status("400")
                    .message("Không thể chặn chính mình")
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
                .message("Chặn người dùng thành công")
                //return the black list
                .blockedUsers(blockedUserList.stream()
                        .map(user -> BlackListResponse.BlockedUser.builder()
                                .id(blockedUser.getId())
                                .name(blockedUser.getAccount().getName())
                                .avatarLink(blockedUser.getAccount().getAvatar())
                                .build())
                        .collect(Collectors.toList()))
                .build();

    }

    @Override
    public BlackListResponse unblockUser(BlockAndUnblockUserRequest request) {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;

        BlackList blackList = blackListRepo.findByBlockedUserIdAndUser_Id(request.getBlockUserId(), currentAcc.getUser().getId()).orElse(null);

        User unBlockUser = userRepo.findUserById(request.getBlockUserId());

        if (unBlockUser == null) {
            return BlackListResponse.builder()
                    .status("400")
                    .message("Không thể tìm thấy người dùng với id này")
                    .build();
        }
        if (blackList == null) {
            return BlackListResponse.builder()
                    .status("400")
                    .message("Người dùng không nằm trong danh sách chặn")
                    .build();
        }
        //delete the unblock user from the black list
        blackListRepo.deleteByBlockedUserIdAndUser_Id(request.getBlockUserId(), currentAcc.getUser().getId());

        List<BlackList> blockedUserList = new ArrayList<>();
        blockedUserList.add(blackList);

        return BlackListResponse.builder()
                .status("200")
                .message("Bỏ chặn người dùng thành công")
                .blockedUsers(blockedUserList.stream()
                        .map(user -> BlackListResponse.BlockedUser.builder()
                                .id(unBlockUser.getId())
                                .name(unBlockUser.getAccount().getName())
                                .avatarLink(unBlockUser.getAccount().getAvatar())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ServiceCenterDetailResponse getServiceCenterDetail(ServiceCenterRequest request) {
        ServiceCenter serviceCenter = serviceCenterRepo.findServiceCenterById(request.getCenterId()).orElse(null);
        if (serviceCenter == null) {
            return ServiceCenterDetailResponse.builder()
                    .status("400")
                    .message("Không thể tìm thấy trung tâm dịch vụ với id này")
                    .build();
        }
        return ServiceCenterDetailResponse.builder()
                .status("200")
                .message("Lấy thông tin trung tâm dịch vụ thành công")
                .name(serviceCenter.getName())
                .website(serviceCenter.getWebsite())
                .phone(serviceCenter.getPhone())
                .address(serviceCenter.getAddress())
                .description(serviceCenter.getDescription())
                .images(
                        serviceCenterImageRepo.findAllImageByCenterId(serviceCenter.getId())
                                .stream()
                                .map(x -> ServiceCenterDetailResponse.ImageResponse.builder().link(x).build())
                                .toList()
                )
                .build();
    }

    @Override
    public ServiceDetailResponse getServiceDetail(ServiceRequest request) {
        Services service = serviceRepo.findById(request.getServiceId()).orElse(null);
        if (service == null) {
            return ServiceDetailResponse.builder()
                    .status("400")
                    .message("Không thể tìm thấy dịch vụ với id này")
                    .build();
        }
        return ServiceDetailResponse.builder()
                .status("200")
                .message("Lấy thông tin dịch vụ thành công")
                .name(service.getName())
                .description(service.getDescription())
                .images(
                        serviceImageRepo.findAllImageByServiceId(service.getId())
                                .stream()
                                .map(x -> ServiceDetailResponse.ImageResponse.builder().link(x).build())
                                .toList()
                )
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
        Optional<User> existingUser = userRepo.findByAccountId(id);
        if (existingUser.isPresent()) {
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
