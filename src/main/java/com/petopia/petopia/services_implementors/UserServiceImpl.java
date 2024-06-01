package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.*;
import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.repositories.*;
import com.petopia.petopia.services.AccountService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ServiceReportRepo serviceReportRepo;
    private final PetRepo petRepo;
    private final AccountService accountService;
    private final AppointmentRepo appointmentRepo;
    private final NotificationRepo notificationRepo;
    private final ServiceCenterRepo serviceCenterRepo;
    private final AppointmentStatusRepo appointmentStatusRepo;
    private final ServiceRepo serviceRepo;
    private final BlackListRepo blackListRepo;
    private final PetImageRepo petImageRepo;
    private final ServiceImageRepo serviceImageRepo;
    private final ProductRepo productRepo;
    private final FeedBackRepo feedBackRepo;
    private final SubstituteRepo substituteRepo;
    private final SubstituteStatusRepo substituteStatusRepo;
    private final ShopRepo shopRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final CartRepo cartRepo;
    private final AttributeComboRepo attributeComboRepo;
    private final CartStatusRepo cartStatusRepo;
    private final CartItemRepo cartItemRepo;

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public UserResponse getCurrentUserProfile() {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;
        if (currentAcc.getUser() != null) {
            return UserResponse.builder()
                    .status("200")
                    .message("Lấy thông tin người dùng thành công")
                    .id(currentAcc.getUser().getId())
                    .name(currentAcc.getName())
                    .gender(currentAcc.getUser().getGender())
                    .address(currentAcc.getUser().getAddress())
                    .phone(currentAcc.getUser().getPhone())
                    .images(getUserImages(currentAcc.getUser()))
                    .posts(getUserPosts(currentAcc.getUser()))
                    .groups(getUserGroups(currentAcc.getUser()))
                    .build();
        }
        return UserResponse.builder()
                .status("400")
                .message("Người dùng chưa có hồ sơ")
                .build();
    }

    private List<UserResponse.Image> getUserImages(User user) {
        return user.getUserImageList().stream()
                .map(image -> new UserResponse.Image(image.getLink()))
                .collect(Collectors.toList());
    }

    private List<UserResponse.Group> getUserGroups(User user) {
        return user.getGroupList().stream()
                .map(group -> new UserResponse.Group(group.getName(), group.getGroupImageList().get(0).getLink()))
                .collect(Collectors.toList());
    }

    private List<UserResponse.Post> getUserPosts(User user) {
        return user.getPostList().stream()
                .map(post -> UserResponse.Post.builder()
                        .id(post.getId())
                        .status(post.getPostStatus().getStatus())
                        .content(post.getContent())
                        .uploadDate(post.getPostDate())
                        .images(getPostImages(post))
                        .postLikedUsers(getPostLikedUser(post))
                        .comments(getComment(post))
                        .build())
                .collect(Collectors.toList());
    }

    private List<UserResponse.Image> getPostImages(Post post) {
        return post.getPostImageList().stream()
                .map(image -> new UserResponse.Image(image.getLink()))
                .collect(Collectors.toList());
    }

    private List<UserResponse.PostLikedUser> getPostLikedUser(Post post) {

        return post.getPostLikedUserList().stream()
                .map(user -> new UserResponse.PostLikedUser(user.getId(), userRepo.findUserById(user.getUserId()).getRealName(), userRepo.findUserById(user.getUserId()).getAccount().getAvatar()))
                .toList();
    }

    private List<UserResponse.Comment> getComment(Post post) {
        return post.getCommentList().stream()
                .map(comment -> new UserResponse.Comment(comment.getUser().getRealName(), comment.getContent()))
                .toList();
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public CreateAppointmentResponse createAppointment(CreateAppointmentRequest request, String type) {
        Account currentAcc = accountService.getCurrentLoggedAccount();
        assert currentAcc != null;

        AppointmentStatus appointmentStatus = appointmentStatusRepo.findByStatus(Const.APPOINTMENT_STATUS_PENDING);

        String appointmentType = type.equals("health") ? Const.APPOINTMENT_TYPE_HEALTH : Const.APPOINTMENT_TYPE_SERVICE;

        ServiceCenter serviceCenter = serviceCenterRepo.findById(request.getCenterId()).orElse(null);

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
        Pet pet = petRepo.findByIdAndUser_Account_Id(request.getPetId(), currentAcc.getId());
        if (pet == null) {
            return CreateAppointmentResponse.builder()
                    .status("400")
                    .message("Không tìm thấy thú cưng với tên này")
                    .build();
        }


        if (request.getServicePlace().trim().equalsIgnoreCase("nhà riêng")) {
            Appointment appointment = Appointment.builder()
                    .pet(pet)
                    .serviceProvider(null)
                    .appointmentStatus(appointmentStatus)
                    .date(request.getDateTime())
                    .fee(calculateSumOfFees(request))
                    .type(appointmentType)
                    .extraInformation(request.getExtraInformation())
                    .location(currentAcc.getUser().getAddress())
                    .build();

            if (!request.getSubstituteName().isEmpty() && !request.getSubstitutePhone().isEmpty()) {
                Substitute substitute = Substitute.builder()
                        .name(request.getSubstituteName())
                        .phone(request.getSubstitutePhone())
                        .substituteStatus(substituteStatusRepo.findByStatus(Const.SUBSTITUTE_STATUS_ACTIVE))
                        .build();
                List<Substitute> substituteList = new ArrayList<>();
                substituteList.add(substitute);
                appointment.setSubstituteList(substituteList);
            }

            Appointment savedAppointment = appointmentRepo.save(appointment);
            savedAppointment.setServicesList(serviceList);

            if (!savedAppointment.getSubstituteList().isEmpty()) {
                List<Substitute> savedSubstitutes = savedAppointment.getSubstituteList();
                substituteRepo.saveAll(savedSubstitutes);
            }
            return CreateAppointmentResponse.builder()
                    .status("200")
                    .message("Tạo lịch hẹn thành công")
                    .appointment(
                            CreateAppointmentResponse.appointmentDraft.builder()
                                    .petName(savedAppointment.getPet().getName())
                                    .status(Const.APPOINTMENT_STATUS_PENDING)
                                    .date(request.getDateTime())
                                    .location(savedAppointment.getPet().getUser().getAddress())
                                    .services(serviceList.stream()
                                            .map(service -> CreateAppointmentResponse.Servicee.builder()
                                                    .id(service.getId())
                                                    .name(service.getName())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .type(appointmentType)
                                    .extraInformation(savedAppointment.getExtraInformation())
                                    .substitute(savedAppointment.getSubstituteList().stream()
                                            .map(substitute -> CreateAppointmentResponse.SubstituteList.builder()
                                                    .name(substitute.getName())
                                                    .phone(substitute.getPhone())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .fee(savedAppointment.getFee())
                                    .build()
                    )
                    .build();
        } else if (request.getServicePlace().trim().equalsIgnoreCase("trung tâm")) {
            Appointment appointment = Appointment.builder()
                    .pet(pet)
                    .serviceProvider(null)
                    .appointmentStatus(appointmentStatus)
                    .date(request.getDateTime())
                    .fee(calculateSumOfFees(request))
                    .type(appointmentType)
                    .extraInformation(request.getExtraInformation())
                    .location(serviceCenter.getAddress())
                    .build();

            if (!request.getSubstituteName().isEmpty() && !request.getSubstitutePhone().isEmpty()) {
                Substitute substitute = Substitute.builder()
                        .name(request.getSubstituteName())
                        .phone(request.getSubstitutePhone())
                        .substituteStatus(substituteStatusRepo.findByStatus(Const.SUBSTITUTE_STATUS_ACTIVE))
                        .build();
                List<Substitute> substituteList = new ArrayList<>();
                substituteList.add(substitute);
                appointment.setSubstituteList(substituteList);
            }

            Appointment savedAppointment = appointmentRepo.save(appointment);
            savedAppointment.setServicesList(serviceList);

            if (!savedAppointment.getSubstituteList().isEmpty()) {
                List<Substitute> savedSubstitutes = savedAppointment.getSubstituteList();
                substituteRepo.saveAll(savedSubstitutes);
            }
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
                                    .substitute(savedAppointment.getSubstituteList().stream()
                                            .map(substitute -> CreateAppointmentResponse.SubstituteList.builder()
                                                    .name(substitute.getName())
                                                    .phone(substitute.getPhone())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .fee(savedAppointment.getFee())
                                    .build()
                    )
                    .build();
        } else {
            return CreateAppointmentResponse.builder()
                    .status("400")
                    .message("Địa điểm không hợp lệ")
                    .build();
        }
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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public ServiceListResponse getServiceList(ServiceCenterRequest request) {
        ServiceCenter serviceCenter = serviceCenterRepo.findById(request.getCenterId()).orElse(null);
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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public ServiceCenterDetailResponse getServiceCenterDetail(ServiceCenterRequest request) {
        ServiceCenter serviceCenter = serviceCenterRepo.findById(request.getCenterId()).orElse(null);
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
                        serviceCenter.getServiceCenterImageList()
                                .stream()
                                .map(x -> ServiceCenterDetailResponse.Images.builder()
                                        .link(x.getLink())
                                        .build())
                                .toList()
                )
                .build();
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public ViewOtherUserProfileResponse viewOtherUserProfile(ViewOtherUserProfileRequest request) {
        String message = "";
        String status = "";
        ViewOtherUserProfileResponse.UserProfile userProfile = new ViewOtherUserProfileResponse.UserProfile();

        User user = userRepo.findUserById(request.getId());
        if (user != null && checkIfAccountActive(user.getAccount())) {
            userProfile = ViewOtherUserProfileResponse.UserProfile.builder()
                    .userName(user.getAccount().getName())
                    .gender(user.getGender())
                    .avatar(user.getAccount().getAvatar())
                    .phoneNumber(user.getPhone())
                    .address(user.getAddress())
                    .build();
            message = "Thành công tìm thấy hồ sơ người dùng";
            status = "200";
        } else {
            message = "Người dùng chưa có hồ sơ hoặc người dùng không tồn tại";
            status = "404";
        }

        return ViewOtherUserProfileResponse.builder()
                .status(status)
                .message(message)
                .userProfile(userProfile)
                .build();
    }

    @Override
    public FindOtherUserProfileResponse findOtherUserProfileResponse(FindOtherUserProfileRequest request) {
        String message = "";
        String status = "";
        ArrayList<FindOtherUserProfileResponse.UserResponse> listUserName = new ArrayList<>();

        List<User> userList = userRepo.findAll();

        for (User user : userList) {
            if (user.getAccount().getName().contains(request.getUserName())
                    && checkIfAccountActive(user.getAccount())) {
                listUserName.add(FindOtherUserProfileResponse.UserResponse
                        .builder()
                        .username(user.getAccount().getName())
                        .userProfileId(user.getId())
                        .build());
            }
        }
        int totalUsers = listUserName.size();
        if (listUserName.isEmpty()) {
            message = "Không thể tìm thấy tài khoản với từ khóa " + request.getUserName();
            status = "400";
        } else {
            message = "Đã tìm thấy các tài khoản từ khóa : " + request.getUserName();
            status = "200";
        }
        return FindOtherUserProfileResponse
                .builder()
                .status(status)
                .message(message)
                .totalUsers(totalUsers)
                .users(listUserName)
                .build();

    }

    @Override
    public ProductReportResponse reportProduct(ProductReportRequest request) {
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null || product.getProductStatus().getStatus().equals(Const.PRODUCT_STATUS_DELETED)) {
            return ProductReportResponse.builder().status("400").message("Sản phẩm không tồn tại").build();
        }

        feedBackRepo.save(
                Feedback.builder()
                        .user(accountService.getCurrentLoggedAccount().getUser())
                        .product(product)
                        .isReported(true)
                        .content(request.getContent())
                        .rating(0)
                        .build()
        );
        return ProductReportResponse.builder().status("200").message("Báo cáo sản phẩm thành công").build();
    }

    @Override
    public ViewFeedbackListResponse viewFeedbackListResponse(ViewFeedbackListRequest request) {
        List<Feedback> listFeedback = feedBackRepo.findAllByProduct_Id(request.getProductId());
        List<ViewFeedbackListResponse.UserResponse> newListFeedback = new ArrayList<>();
        for (Feedback feedback : listFeedback) {
            if (!feedback.isReported()) {

                User user = feedback.getUser();
                newListFeedback.add(ViewFeedbackListResponse.UserResponse
                        .builder()
                        .username(user.getAccount().getName())
                        .avatar(user.getAccount().getAvatar())
                        .content(feedback.getContent())
                        .rating(feedback.getRating())
                        .build());
            }
        }
        return ViewFeedbackListResponse
                .builder()
                .status("200")
                .user(newListFeedback)
                .build();
    }

    @Override
    public CreateProductResponse createProduct(CreateProductRequest request) {
        Account account = accountService.getCurrentLoggedAccount();
        assert account != null;
        if (account.getShop() == null) {
            return CreateProductResponse.builder()
                    .status("400")
                    .message("Tài khoản này không phải là chủ cửa hàng")
                    .build();
        }

        ProductCategory productCategory = productCategoryRepo.findByName(request.getName());
        if (productCategory == null) {
            return CreateProductResponse.builder()
                    .status("400")
                    .message("Không tìm thấy danh mục sản phẩm này")
                    .build();
        }

        Product product = Product.builder()
                .name(request.getName())
                .productCategory(productCategory)
//                .price(request.getPrice())
//                .availableQty(request.getAvailableQuantity())
                .soldQty(0)
                .rating(0)
                .shop(account.getShop())
                .build();

        Product savedProduct = productRepo.save(product);

        return CreateProductResponse.builder()
                .status("200")
                .message("Tạo sản phẩm thành công")
                .id(savedProduct.getId())
                .category(savedProduct.getProductCategory().getName())
                .name(savedProduct.getName())
//                .price(savedProduct.getPrice())
//                .availableQuantity(savedProduct.getAvailableQty())
                .soldQuantity(savedProduct.getSoldQty())
                .rating(savedProduct.getRating())
                .images(savedProduct.getProductImageList().stream()
                        .map(image -> CreateProductResponse.Images.builder()
                                .link(image.getLink())
                                .build())
                        .collect(Collectors.toList()))
                .feedbacks(savedProduct.getFeedbackList().stream()
                        .map(feedback -> CreateProductResponse.FeedBack.builder()
                                .content(feedback.getContent())
                                .build())
                        .toList())
                .shop(CreateProductResponse.Shop.builder()
                        .id(savedProduct.getShop().getId())
                        .name(savedProduct.getShop().getAccount().getName())
                        .build())
                .build();
    }

    @Override
    public ViewShopProfileResponse viewShopProfile(ViewShopProfileRequest request) {
        String status = "";
        String message = "";
        ViewShopProfileResponse.Shop shopResponse = new ViewShopProfileResponse.Shop();
        Shop shop = shopRepo.findShopsById(request.getShopId());
        if (shop != null && checkIfShopActiveOrClose(shop)) {
            shopResponse = ViewShopProfileResponse.Shop
                    .builder()
                    .name(shop.getName())
                    .ownerName(shop.getAccount().getName())
                    .rating(shop.getRating())
                    .address(shop.getAddress())
                    .build();
            status = "200";
            message = "Xem hồ sơ cửa hàng thành công";
        } else {
            status = "400";
            message = "Không tìm thấy hồ sơ cửa hàng";
        }
        return ViewShopProfileResponse
                .builder()
                .status(status)
                .message(message)
                .shop(shopResponse)
                .build();
    }

    @Override
    public SearchShopResponse searchShop(SearchShopRequest request) {
        String message = "";
        String status = "";

        ArrayList<SearchShopResponse.ShopResponse> shopResponses = new ArrayList<>();

        List<Shop> shops = shopRepo.findAll();
        for (Shop shop : shops) {
            if (shop.getName().contains(request.getShopName()) && checkIfShopActiveOrClose(shop)) {
                shopResponses.add(SearchShopResponse.ShopResponse
                        .builder()
                        .id(shop.getId())
                        .name(shop.getName())
                        .build());
            }
        }
        int totalShop = shopResponses.size();
        if (shopResponses.isEmpty()) {
            message = "Không thể tìm thấy cửa hàng với từ khóa " + request.getShopName();
            status = "400";
        } else {
            message = "Đã tìm thấy các cửa hàng với từ khóa : " + request.getShopName();
            status = "200";
        }
        return SearchShopResponse
                .builder()
                .status(status)
                .message(message)
                .totalShop(totalShop)
                .shops(shopResponses)
                .build();
    }

    @Override
    public AddToCartResponse addProductToCart(AddToCartRequest request) {
        String status = "400";
        String message = addToCart(request);
        if (message.isEmpty()) {
            status = "200";
            message = "Add to cart successfully";
        }
        return AddToCartResponse.builder().status(status).message(message).build();
    }

    private String addToCart(AddToCartRequest request) {
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) return "Sản phẩm không tồn tại trong shop";
        if (!checkIfAllAttributesAreFilled(request.getMainAttribute(), request.getSubAttribute(), product)) return "Phân loại không hợp lệ";

        Account account = accountService.getCurrentLoggedAccount();
        User user = account.getUser();
        Cart cart = cartRepo.findByUser_IdAndCartStatus_Status(user.getId(), Const.CART_STATUS_ACTIVE).orElse(null);
        if (cart != null) {
            return controlCartFlow("update", request, user, cart);
        }

        return controlCartFlow("create", request, user, cart);
    }

    private boolean checkIfAllAttributesAreFilled(AddToCartRequest.Attribute mainAttribute, AddToCartRequest.Attribute subAttribute, Product product) {
        return checkIfAttributeIsValid(mainAttribute, product) && checkIfAttributeIsValid(subAttribute, product);
    }

    private boolean checkIfAttributeIsValid(AddToCartRequest.Attribute attribute, Product product) {
        List<String> attributeNameList = product.getProductAttributeList().stream().map(ProductAttribute::getName).toList();
        ProductAttribute productAttribute = product.getProductAttributeList().get(attributeNameList.indexOf(attribute.getName().trim()));
        if (productAttribute == null) return false;

        List<String> attributeValueList = productAttribute.getAttributeValueList().stream().map(AttributeValue::getValue).toList();
        if (!attributeValueList.contains(attribute.getValue().trim())) return false;

        return true;
    }

    private String controlCartFlow(String choice, AddToCartRequest request, User user, Cart cart) {
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        assert product != null;
        AttributeCombo combo = getAttributeCombo(request);
        if (combo == null) return "Sản phẩm với phân loại đã chọn hiện không khả dụng";
        if (!checkComboQuantity(request.getQuantity(), combo)) return "Số lượng lựa chọn không hợp lệ";


        switch (choice) {
            case "create":
                return createCartFlow(request, user, combo);

            default:
                return updateCartFlow(request, user, combo, cart);
        }
    }

    private String createCartFlow(AddToCartRequest request, User user, AttributeCombo combo) {
        Cart cart = cartRepo.save(
                Cart.builder()
                        .user(user)
                        .cartStatus(cartStatusRepo.findByStatus(Const.CART_STATUS_ACTIVE))
                        .build()
        );

        cartItemRepo.save(
                CartItem.builder()
                        .cart(cart)
                        .attributeCombo(combo)
                        .quantity(request.getQuantity())
                        .price(combo.getPrice())
                        .build()
        );

        return "";
    }

    private String updateCartFlow(AddToCartRequest request, User user, AttributeCombo combo, Cart cart) {
        List<AttributeCombo> comboList = cart.getCartItemList().stream().map(
                CartItem::getAttributeCombo
        ).toList();

        if(comboList.contains(combo)){
            return updateCartFlowExistedCombo(request, user, combo, cart);
        }
        return updateCartFlowNotExistedCombo(request, user, combo, cart);
    }

    private String updateCartFlowExistedCombo(AddToCartRequest request, User user, AttributeCombo combo, Cart cart){
        CartItem item = new CartItem();
        for(CartItem i: cart.getCartItemList()){
            if(i.getAttributeCombo().equals(combo)){
                item = i;
                break;
            }
        }
        if(!checkComboQuantity(request.getQuantity() + item.getQuantity(), combo)) return "Số lượng lựa chọn không hợp lệ";

        item.setQuantity(request.getQuantity() + item.getQuantity());
        cartItemRepo.save(item);
        return "";
    }

    private String updateCartFlowNotExistedCombo(AddToCartRequest request, User user, AttributeCombo combo, Cart cart){
        cartItemRepo.save(
                CartItem.builder()
                        .cart(cart)
                        .attributeCombo(combo)
                        .quantity(request.getQuantity())
                        .price(combo.getPrice())
                        .build()
        );

        return "";
    }

    private boolean checkComboQuantity(int inputQty, AttributeCombo combo) {
        return inputQty <= combo.getQuantity();
    }

    private AttributeCombo getAttributeCombo(AddToCartRequest request) {
        String MAN = request.getMainAttribute().getName();
        String MAVN = request.getMainAttribute().getValue();
        String SAN = request.getSubAttribute().getName();
        String SAVN = request.getSubAttribute().getValue();
        return attributeComboRepo.findByMANAndMAVNAndSANAndSAVN(MAN, MAVN, SAN, SAVN).orElse(null);
    }

    private List<ServiceCenter> getServiceCenterList(String type) {
        return serviceCenterRepo.findAllByTypeAndServiceCenterStatus_StatusOrServiceCenterStatus_StatusOrderByRatingDesc(type, Const.SERVICE_CENTER_STATUS_ACTIVE, Const.SERVICE_CENTER_STATUS_CLOSED);
    }

    private List<Services> getHealthServiceList(String type) {
        return serviceRepo.findAllByServiceCenter_TypeAndServiceStatus_StatusOrderByRatingDesc(type, Const.SERVICE_STATUS_ACTIVE);
    }


    @Override
    public CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request) {
        Account account = accountService.getCurrentLoggedAccount();
        if (account.getUser() != null) {
            return CreateUserProfileResponse.builder()
                    .status("409")
                    .message("Hồ sơ người dùng cho tài khoản này đã tồn tại")
                    .build();
        }
        User user = userRepo.save(
                User
                        .builder()
                        .gender(request.getGender())
                        .realName(request.getRealName() == null ? "" : request.getRealName())
                        .address(request.getAddress())
                        .phone(request.getPhone())
                        .account(account)
                        .build()
        );

        return CreateUserProfileResponse.builder()
                .address(user.getAddress())
                .gender(user.getGender())
                .phone(user.getPhone())
                .status("200")
                .message("Tạo hồ sơ người dùng thành công")
                .build();
    }

    @Override
    public UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest request) {
        Account account = accountService.getCurrentLoggedAccount();

        User user = account.getUser();
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        userRepo.save(user);

        return UpdateUserProfileResponse
                .builder()
                .address(user.getAddress())
                .gender(user.getGender())
                .phone(user.getPhone())
                .status("200")
                .message("Cập nhật hồ sơ người dùng thành công")
                .build();

    }


    private Page<ServiceReport> getPaginationHealthReportListByPetId(int pageNo, Integer petID, String sort) {
        Pageable pageable = PageRequest.of(pageNo, Const.PAGE_SIZE, Sort.by(sort));
        return serviceReportRepo.findAllByAppointment_Pet_IdAndAppointment_TypeAndAppointment_AppointmentStatus_Status(petID, Const.APPOINTMENT_TYPE_HEALTH, Const.APPOINTMENT_STATUS_SUCCESSFUL, pageable);
    }

    private boolean checkIfAccountActive(Account account) {
        return account.getAccountStatus().getStatus().equals(Const.ACCOUNT_STATUS_ACTIVE);
    }

    private boolean checkIfShopActiveOrClose(Shop shop) {
        return shop.getShopStatus().getStatus().equals(Const.SHOP_STATUS_ACTIVE) || shop.getShopStatus().getStatus().equals(Const.SHOP_STATUS_CLOSED);
    }
}
