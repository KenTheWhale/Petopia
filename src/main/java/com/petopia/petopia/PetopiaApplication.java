package com.petopia.petopia;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.enums.Role;
import com.petopia.petopia.models.entity_models.*;
import com.petopia.petopia.repositories.*;
import com.petopia.petopia.services.JWTService;
import com.petopia.petopia.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PetopiaApplication {

	private final TokenStatusRepo tokenStatusRepo;

	private final AccountStatusRepo accountStatusRepo;

	private final GroupStatusRepo groupStatusRepo;

	private final PostStatusRepo postStatusRepo;

	private final AppointmentStatusRepo appointmentStatusRepo;

	private final OrderStatusRepo orderStatusRepo;

	private final PlanStatusRepo planStatusRepo;

	private final ShopStatusRepo shopStatusRepo;

	private final ProductStatusRepo productStatusRepo;

	private final PaymentMethodRepo paymentMethodRepo;

	private final PasswordEncoder passwordEncoder;

	private final TokenService tokenService;

	private final JWTService jwtService;

	private final AccountRepo accountRepo;

	private final UserRepo userRepo;

	private final PetRepo petRepo;

	private final ServiceProviderRepo serviceProviderRepo;

	private final ProviderStatusRepo providerStatusRepo;

	private final ServiceStatusRepo serviceStatusRepo;

	private final ServiceCenterStatusRepo serviceCenterStatusRepo;

	private final GroupRepo groupRepo;

	private final CommentRepo commentRepo;

	private final PostRepo postRepo;

	private final ServiceCenterRepo serviceCenterRepo;

	private final ServiceCenterPlanRepo serviceCenterPlanRepo;

	private final AppointmentRepo appointmentRepo;

	private final ServiceReportRepo serviceReportRepo;

	private final OrderRepo orderRepo;

	private final FeedBackRepo feedBackRepo;

	private final OrderDetailRepo orderDetailRepo;

	private final ShopRepo shopRepo;

	private final ProductRepo productRepo;

	private final ServiceRepo serviceRepo;

	private final ShopPlanRepo shopPlanRepo;

	private final ProductCategoryRepo productCategoryRepo;

	private final AppointmentServiceRepo appointmentServiceRepo;

	private final NotificationRepo notificationRepo;

	public static void main(String[] args) {
		SpringApplication.run(PetopiaApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner initDate(){
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//
//				// init status-------------------------------------------------------------------//
//				// init token status
//				TokenStatus activeToken = tokenStatusRepo.save(
//						TokenStatus.builder().status(Const.TOKEN_STATUS_ACTIVE).build()
//				);
//
//				TokenStatus expiredToken = tokenStatusRepo.save(
//						TokenStatus.builder().status(Const.TOKEN_STATUS_EXPIRED).build()
//				);
//
//				// init account status
//				AccountStatus activeAccount = accountStatusRepo.save(
//						AccountStatus.builder().status(Const.ACCOUNT_STATUS_ACTIVE).build()
//				);
//
//				AccountStatus bannedAccount = accountStatusRepo.save(
//						AccountStatus.builder().status(Const.ACCOUNT_STATUS_BANNED).build()
//				);
//
//				// init group status
//				GroupStatus activeGroup = groupStatusRepo.save(
//						GroupStatus.builder().status(Const.GROUP_STATUS_ACTIVE).build()
//				);
//
//				GroupStatus inactiveGroup = groupStatusRepo.save(
//						GroupStatus.builder().status(Const.GROUP_STATUS_INACTIVE).build()
//				);
//
//				GroupStatus bannedGroup = groupStatusRepo.save(
//						GroupStatus.builder().status(Const.GROUP_STATUS_BANNED).build()
//				);;
//
//				GroupStatus deletedGroup = groupStatusRepo.save(
//						GroupStatus.builder().status(Const.GROUP_STATUS_DELETED).build()
//				);;
//
//				// init post status
//				PostStatus publicPost = postStatusRepo.save(
//						PostStatus.builder().status(Const.POST_STATUS_PUBLIC).build()
//				);
//
//				PostStatus onlyFriendPost = postStatusRepo.save(
//						PostStatus.builder().status(Const.POST_STATUS_ONLY_FRIEND).build()
//				);;
//
//				PostStatus onlyMePost = postStatusRepo.save(
//						PostStatus.builder().status(Const.POST_STATUS_ONLY_ME).build()
//				);;
//
//				PostStatus deletedPost = postStatusRepo.save(
//						PostStatus.builder().status(Const.POST_STATUS_DELETED).build()
//				);;
//
//				// init appointment status
//				AppointmentStatus pendingAppointment = appointmentStatusRepo.save(
//						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_PENDING).build()
//				);
//
//				AppointmentStatus confirmedAppointment = appointmentStatusRepo.save(
//						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_CONFIRMED).build()
//				);;
//
//				AppointmentStatus cancelledAppointment = appointmentStatusRepo.save(
//						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_CANCELLED).build()
//				);;
//
//				AppointmentStatus successfulAppointment = appointmentStatusRepo.save(
//						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_SUCCESSFUL).build()
//				);;
//
//				// init order status
//				OrderStatus processingOrder = orderStatusRepo.save(
//						OrderStatus.builder().status(Const.ORDER_STATUS_PROCESSING).build()
//				);
//
//				OrderStatus confirmedOrder = orderStatusRepo.save(
//						OrderStatus.builder().status(Const.ORDER_STATUS_CONFIRMED).build()
//				);;
//
//				OrderStatus deliveringOrder = orderStatusRepo.save(
//						OrderStatus.builder().status(Const.ORDER_STATUS_DELIVERING).build()
//				);;
//
//				OrderStatus successfulOrder = orderStatusRepo.save(
//						OrderStatus.builder().status(Const.ORDER_STATUS_SUCCESSFUL).build()
//				);;
//
//				OrderStatus cancelledOrder = orderStatusRepo.save(
//						OrderStatus.builder().status(Const.ORDER_STATUS_CANCELLED).build()
//				);;
//
//				// init plan status
//				PlanStatus activePlan = planStatusRepo.save(
//						PlanStatus.builder().status(Const.PLAN_STATUS_ACTIVE).build()
//				);
//
//				PlanStatus inactivePlan = planStatusRepo.save(
//						PlanStatus.builder().status(Const.PLAN_STATUS_INACTIVE).build()
//				);;
//
//				// init shop status
//				ShopStatus activeShop = shopStatusRepo.save(
//						ShopStatus.builder().status(Const.SHOP_STATUS_ACTIVE).build()
//				);
//
//				ShopStatus closedShop = shopStatusRepo.save(
//						ShopStatus.builder().status(Const.SHOP_STATUS_CLOSED).build()
//				);;
//
//				ShopStatus bannedShop = shopStatusRepo.save(
//						ShopStatus.builder().status(Const.SHOP_STATUS_BANNED).build()
//				);;
//
//				ShopStatus deletedShop = shopStatusRepo.save(
//						ShopStatus.builder().status(Const.SHOP_STATUS_DELETED).build()
//				);;
//
//				// init product status
//				ProductStatus availableProduct = productStatusRepo.save(
//						ProductStatus.builder().status(Const.PRODUCT_STATUS_AVAILABLE).build()
//				);
//
//				ProductStatus outOfStockProduct = productStatusRepo.save(
//						ProductStatus.builder().status(Const.PRODUCT_STATUS_OUT_OF_STOCK).build()
//				);;
//
//				ProductStatus deletedProduct = productStatusRepo.save(
//						ProductStatus.builder().status(Const.PRODUCT_STATUS_DELETED).build()
//				);;
//
//				// init payment method
//				PaymentMethod VNPayMethod = paymentMethodRepo.save(
//						PaymentMethod.builder().method("VNPay").build()
//				);
//
//				// init provider status
//				ProviderStatus availableProvider = providerStatusRepo.save(
//						ProviderStatus.builder().status(Const.PROVIDER_STATUS_AVAILABLE).build()
//				);
//
//				ProviderStatus busyProvider = providerStatusRepo.save(
//						ProviderStatus.builder().status(Const.PROVIDER_STATUS_BUSY).build()
//				);
//
//				// Init service status
//				ServiceStatus activeService = serviceStatusRepo.save(
//						ServiceStatus.builder().status(Const.SERVICE_STATUS_ACTIVE).build()
//				);
//
//				ServiceStatus closedService = serviceStatusRepo.save(
//						ServiceStatus.builder().status(Const.SERVICE_STATUS_CLOSED).build()
//				);
//
//				ServiceStatus deletedService = serviceStatusRepo.save(
//						ServiceStatus.builder().status(Const.SERVICE_STATUS_DELETED).build()
//				);
//
//				// init service center status
//				ServiceCenterStatus activeServiceCenter = serviceCenterStatusRepo.save(
//						ServiceCenterStatus.builder().status(Const.SERVICE_CENTER_STATUS_ACTIVE).build()
//				);
//
//				ServiceCenterStatus closedServiceCenter = serviceCenterStatusRepo.save(
//						ServiceCenterStatus.builder().status(Const.SERVICE_CENTER_STATUS_CLOSED).build()
//				);
//
//				ServiceCenterStatus bannedServiceCenter = serviceCenterStatusRepo.save(
//						ServiceCenterStatus.builder().status(Const.SERVICE_CENTER_STATUS_BANNED).build()
//				);
//
//				ServiceCenterStatus deletedServiceCenter = serviceCenterStatusRepo.save(
//						ServiceCenterStatus.builder().status(Const.SERVICE_CENTER_STATUS_DELETED).build()
//				);
//
//				// init account-------------------------------------------------------------------------//
//				List<Account> accountList = new ArrayList<>();
//				// Admin
//				Account adminAcc = Account.builder()
//						.email("petopia@peto.com")
//						.name("Petopia")
//						.avatarLink("")
//						.password(passwordEncoder.encode("Petopia"))
//						.accountStatus(activeAccount)
//						.role(Role.ADMIN)
//						.build();
//				accountList.add(adminAcc);
//
//				// Shop owner
//				Account shopAcc1 = Account.builder()
//						.email("Toto@peto.com")
//						.name("Toto shop")
//						.avatarLink("")
//						.password(passwordEncoder.encode("toto"))
//						.accountStatus(activeAccount)
//						.role(Role.SHOP_OWNER)
//						.build();
//				accountList.add(shopAcc1);
//
//				Account shopAcc2 = Account.builder()
//						.email("Haiconmeo@peto.com")
//						.name("Hai con mèo")
//						.avatarLink("")
//						.password(passwordEncoder.encode("Haiconmeo"))
//						.accountStatus(activeAccount)
//						.role(Role.SHOP_OWNER)
//						.build();
//				accountList.add(shopAcc2);
//
//				// Group admin
//				Account gpAdminAcc1 = Account.builder()
//						.email("kienquoc@peto.com")
//						.name("kien quoc")
//						.avatarLink("")
//						.password(passwordEncoder.encode("quoc"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_ADMIN)
//						.build();
//				accountList.add(gpAdminAcc1);
//
//				Account gpAdminAcc2 = Account.builder()
//						.email("trieunguyen@peto.com")
//						.name("trieu nguyen")
//						.avatarLink("")
//						.password(passwordEncoder.encode("trieu"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_ADMIN)
//						.build();
//				accountList.add(gpAdminAcc2);
//
//				// Group manager
//				Account gpManagerAcc1 = Account.builder()
//						.email("huytran@peto.com")
//						.name("huy tran")
//						.avatarLink("")
//						.password(passwordEncoder.encode("huy"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_MANAGER)
//						.build();
//				accountList.add(gpManagerAcc1);
//
//				Account gpManagerAcc2 = Account.builder()
//						.email("phuonganh@peto.com")
//						.name("phuong anh")
//						.avatarLink("")
//						.password(passwordEncoder.encode("pa"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_MANAGER)
//						.build();
//				accountList.add(gpManagerAcc2);
//
//				Account gpManagerAcc3 = Account.builder()
//						.email("thuytien@peto.com")
//						.name("nguyen thuc thuy tien")
//						.avatarLink("")
//						.password(passwordEncoder.encode("tien"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_MANAGER)
//						.build();
//				accountList.add(gpManagerAcc3);
//
//				Account gpManagerAcc4 = Account.builder()
//						.email("pamela@peto.com")
//						.name("palmela Hai Duong")
//						.avatarLink("")
//						.password(passwordEncoder.encode("palm"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_MANAGER)
//						.build();
//				accountList.add(gpManagerAcc4);
//
//				// Service center manager
//				Account scmAcc1 = Account.builder()
//						.email("vitaminmeo@peto.com")
//						.name("vitamin C")
//						.avatarLink("")
//						.password(passwordEncoder.encode("meo"))
//						.accountStatus(activeAccount)
//						.role(Role.SERVICE_CENTER_MANAGER)
//						.build();
//				accountList.add(scmAcc1);
//
//				Account scmAcc2 = Account.builder()
//						.email("supabase@peto.com")
//						.name("supabase")
//						.avatarLink("")
//						.password(passwordEncoder.encode("supa"))
//						.accountStatus(activeAccount)
//						.role(Role.SERVICE_CENTER_MANAGER)
//						.build();
//				accountList.add(scmAcc2);
//
//				// Service provider
//				Account spAcc1 = Account.builder()
//						.email("chichushop@peto.com")
//						.name("chi chu")
//						.avatarLink("")
//						.password(passwordEncoder.encode("shop"))
//						.accountStatus(activeAccount)
//						.role(Role.SERVICE_PROVIDER)
//						.build();
//				accountList.add(spAcc1);
//
//				Account spAcc2 = Account.builder()
//						.email("quanlishop@peto.com")
//						.name("quan li")
//						.avatarLink("")
//						.password(passwordEncoder.encode("quanli"))
//						.accountStatus(activeAccount)
//						.role(Role.SERVICE_PROVIDER)
//						.build();
//				accountList.add(spAcc2);
//
//				Account spAcc3 = Account.builder()
//						.email("nhanvienhotro@peto.com")
//						.name("nhan vien ho tro")
//						.avatarLink("")
//						.password(passwordEncoder.encode("hotro"))
//						.accountStatus(activeAccount)
//						.role(Role.SERVICE_PROVIDER)
//						.build();
//				accountList.add(spAcc3);
//
//				Account spAcc4 = Account.builder()
//						.email("nhanvienbanhang@peto.com")
//						.name("nhan vien ban hang")
//						.avatarLink("")
//						.password(passwordEncoder.encode("banhang"))
//						.accountStatus(activeAccount)
//						.role(Role.SERVICE_PROVIDER)
//						.build();
//				accountList.add(spAcc4);
//
//				// User
//				Account userAcc1 = Account.builder()
//						.email("peppa@peto.com")
//						.name("Peppa")
//						.avatarLink("")
//						.password(passwordEncoder.encode("peppa"))
//						.accountStatus(activeAccount)
//						.role(Role.USER)
//						.build();
//				accountList.add(userAcc1);
//				accountRepo.saveAll(accountList);
//
//				Account userAcc2 = Account.builder()
//						.email("harrystyle@peto.com")
//						.name("Harry Style")
//						.avatarLink("")
//						.password(passwordEncoder.encode("harry"))
//						.accountStatus(activeAccount)
//						.role(Role.USER)
//						.build();
//				accountList.add(userAcc2);
//				accountRepo.saveAll(accountList);
//
//				// Save user from account--------------------------------------------------------//
//				User user1 = userRepo.save(
//						User.builder()
//								.account(userAcc1)
//								.gender(Const.HUMAN_GENDER_MALE)
//								.address("43 Ông Ích Khiêm, quận 10, tp Hồ Chí Minh")
//								.phone("0913253221")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				User user2 = userRepo.save(
//						User.builder()
//								.account(userAcc2)
//								.gender(Const.HUMAN_GENDER_FEMALE)
//								.address("11/12 Vĩnh Viễn, quận 10, tp Hồ Chí Minh")
//								.phone("0833552464")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				// Save group admin user from account------------------------------------------------------//
//				User gauser1 = userRepo.save(
//						User.builder()
//								.account(gpAdminAcc1)
//								.gender(Const.HUMAN_GENDER_MALE)
//								.address("406 Tran Van Kieu, quận 6, tp Hồ Chí Minh")
//								.phone("0123268894")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				User gauser2 = userRepo.save(
//						User.builder()
//								.account(gpAdminAcc2)
//								.gender(Const.HUMAN_GENDER_FEMALE)
//								.address("C16/60 Võ Văn Vân, quận Bình Tân, tp Hồ Chí Minh")
//								.phone("0343748912")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				// Save group manager user from account-----------------------------------------------//
//				User gmuser1 = userRepo.save(
//						User.builder()
//								.account(gpManagerAcc1)
//								.gender(Const.HUMAN_GENDER_MALE)
//								.address("16 đường 12, quận 2, tp Hồ Chí Minh")
//								.phone("0837400952")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				User gmuser2 = userRepo.save(
//						User.builder()
//								.account(gpManagerAcc2)
//								.gender(Const.HUMAN_GENDER_MALE)
//								.address("167 Nguyễn Văn Cừ, quận 5, tp Hồ Chí Minh")
//								.phone("0838357347")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				User gmuser3 = userRepo.save(
//						User.builder()
//								.account(gpManagerAcc3)
//								.gender(Const.HUMAN_GENDER_FEMALE)
//								.address("148 Hai Bà Trưng, quận 1, tp Hồ Chí Minh	")
//								.phone("0838227648")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				User gmuser4 = userRepo.save(
//						User.builder()
//								.account(gpManagerAcc4)
//								.gender(Const.HUMAN_GENDER_FEMALE)
//								.address("12/B24 Phan Huy ich, Gò Vấp, tp Hồ Chí Minh")
//								.phone("0854272338")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				// init service center plan-----------------------------------------------------------//
//
//				ServiceCenterPlan scPlan1 = serviceCenterPlanRepo.save(
//						ServiceCenterPlan.builder()
//								.planStatus(activePlan)
//								.name("PetVip1")
//								.fee(3000000)
//								.duration(150)
//								.description("Gói VIP thú cưng")
//								.build()
//				);
//
//				ServiceCenterPlan scPlan2 = serviceCenterPlanRepo.save(
//						ServiceCenterPlan.builder()
//								.planStatus(activePlan)
//								.name("PetVip2")
//								.fee(5000000)
//								.duration(300)
//								.description("Gói VIP thú cưng")
//								.build()
//				);
//
//				// init service center----------------------------------------------------------------//
//
//				ServiceCenter sc1 = serviceCenterRepo.save(
//						ServiceCenter.builder()
//								.account(scmAcc1)
//								.serviceCenterPlan(scPlan1)
//								.serviceCenterStatus(activeServiceCenter)
//								.planPurchasedDate(LocalDateTime.now().minusWeeks(2))
//								.name("Mèo Spa")
//								.address(" 38/1/95 Trường Chinh, quận Tân Bình, tp Hồ Chí Minh")
//								.rating(3.5)
//								.type(Const.SERVICE_CENTER_TYPE_SERVICE)
//								.build()
//				);
//
//				ServiceCenter sc2 = serviceCenterRepo.save(
//						ServiceCenter.builder()
//								.account(scmAcc2)
//								.serviceCenterPlan(scPlan2)
//								.serviceCenterStatus(activeServiceCenter)
//								.planPurchasedDate(LocalDateTime.now().minusDays(28))
//								.name("Puppy Mart")
//								.address("244 Khánh Hội, quận 4, tp Hồ Chí Minh")
//								.rating(4)
//								.type(Const.SERVICE_CENTER_TYPE_HEALTH)
//								.build()
//				);
//
//				// Save service provider from account-------------------------------------------------//
//				ServiceProvider sp1 = serviceProviderRepo.save(
//						ServiceProvider.builder()
//								.account(spAcc1)
//								.providerStatus(availableProvider)
//								.serviceCenter(sc1)
//								.build()
//				);
//
//				ServiceProvider sp2 = serviceProviderRepo.save(
//						ServiceProvider.builder()
//								.account(spAcc2)
//								.providerStatus(availableProvider)
//								.serviceCenter(sc1)
//								.build()
//				);
//
//				ServiceProvider sp3 = serviceProviderRepo.save(
//						ServiceProvider.builder()
//								.account(spAcc3)
//								.providerStatus(availableProvider)
//								.serviceCenter(sc2)
//								.build()
//				);
//
//				ServiceProvider sp4 = serviceProviderRepo.save(
//						ServiceProvider.builder()
//								.account(spAcc4)
//								.providerStatus(availableProvider)
//								.serviceCenter(sc2)
//								.build()
//				);
//
//				// init and print token for all accounts----------------------------------------------------//
//				List<Token> tokenList = new ArrayList<>();
//				System.out.println("TOKEN LIST:\n");
//				for(Account account: accountList){
//					tokenList.add(tokenService.createNewAccessToken(account));
//					Token lastToken = tokenList.get(tokenList.size() - 1);
//					tokenList.add(tokenService.createNewRefreshToken(account));
//					System.out.println(
//							(accountList.indexOf(account) + 1)
//									+ ". (" + account.getRole().name().toUpperCase()
//									+ ") " + account.getName() + ": "
//									+ lastToken.getValue());
//				}
//
//				// init notification---------------------------------------------------------------------//
//				Notification notification1 = notificationRepo.save(
//						Notification.builder()
//								.user(user1)
//								.imgListLink(Collections.emptyList())
//								.content("Phương Anh đã block bạn")
//								.build()
//				);
//
//				Notification notification2 = notificationRepo.save(
//						Notification.builder()
//								.user(user1)
//								.imgListLink(Collections.emptyList())
//								.content("Phương Anh đã phẫn nộ bài post của bạn")
//								.build()
//				);
//
//				Notification notification3 = notificationRepo.save(
//						Notification.builder()
//								.user(user2)
//								.imgListLink(Collections.emptyList())
//								.content("Phương Anh đã theo dõi bạn")
//								.build()
//				);
//
//				Notification notification4 = notificationRepo.save(
//						Notification.builder()
//								.user(user2)
//								.imgListLink(Collections.emptyList())
//								.content("Phương Anh đã mời bạn thích trang \"1001 cách chửi thề khiến đối phương xón ra quần\" ")
//								.build()
//				);
//
//				// init group----------------------------------------------------------------------------//
//				List<Integer> group1ManagerIdList = new ArrayList<>();
//				List<Integer> group1MemberIdList = new ArrayList<>();
//				List<Integer> group2ManagerIdList = new ArrayList<>();
//				List<Integer> group2MemberIdList = new ArrayList<>();
//
//				group1ManagerIdList.add(gmuser1.getId());
//				group1ManagerIdList.add(gmuser2.getId());
//				group2ManagerIdList.add(gmuser3.getId());
//				group2ManagerIdList.add(gmuser4.getId());
//
//				group1MemberIdList.add(user1.getId());
//				group2MemberIdList.add(user2.getId());
//
//				Group group1 = groupRepo.save(
//						Group.builder()
//								.groupStatus(activeGroup)
//								.user(gauser1)
//								.name("Đảo Mèow")
//								.imgLinkList(Collections.emptyList())
//								.managerIdList(group1ManagerIdList)
//								.memberIdList(group1MemberIdList)
//								.build()
//				);
//
//				Group group2 = groupRepo.save(
//						Group.builder()
//								.groupStatus(activeGroup)
//								.user(gauser2)
//								.name("Đảo Chó")
//								.imgLinkList(Collections.emptyList())
//								.managerIdList(group2ManagerIdList)
//								.memberIdList(group2MemberIdList)
//								.build()
//				);
//
//				// init post-------------------------------------------------------------------------//
//				List<Integer> post1likedUserIdList = new ArrayList<>();
//				List<Integer> post2likedUserIdList = new ArrayList<>();
//
//				post1likedUserIdList.add(user2.getId());
//				post2likedUserIdList.add(user1.getId());
//
//				Post post1 = postRepo.save(
//						Post.builder()
//								.user(user1)
//								.postStatus(publicPost)
//								.canComment(true)
//								.content("Người chơi hệ Meow")
//								.postDate(LocalDateTime.now().minusHours(2))
//								.imgLinkList(Collections.emptyList())
//								.likedUserIdList(post1likedUserIdList)
//								.build()
//				);
//
//				Post post2 = postRepo.save(
//						Post.builder()
//								.user(user1)
//								.postStatus(publicPost)
//								.canComment(false)
//								.content("Người chơi hệ Choá")
//								.postDate(LocalDateTime.now())
//								.imgLinkList(Collections.emptyList())
//								.likedUserIdList(post1likedUserIdList)
//								.build()
//				);
//
//				Post post3 = postRepo.save(
//						Post.builder()
//								.user(user2)
//								.postStatus(publicPost)
//								.canComment(true)
//								.content("Người chơi hệ DuaPet, ko bao giờ nói phét")
//								.postDate(LocalDateTime.now().minusHours(20))
//								.imgLinkList(Collections.emptyList())
//								.likedUserIdList(post2likedUserIdList)
//								.build()
//				);
//
//				Post post4 = postRepo.save(
//						Post.builder()
//								.user(user2)
//								.postStatus(publicPost)
//								.canComment(false)
//								.content("Người Dơi ko sợ mưa rơi. Hahaha")
//								.postDate(LocalDateTime.now().minusYears(1))
//								.imgLinkList(Collections.emptyList())
//								.likedUserIdList(post2likedUserIdList)
//								.build()
//				);
//
//				// init comment--------------------------------------------------------------------------//
//				Comment comment1 = commentRepo.save(
//						Comment.builder()
//								.post(post1)
//								.user(user2)
//								.content("Mèo này giống nào vậy bạn iu")
//								.build()
//				);
//
//				Comment comment2 = commentRepo.save(
//						Comment.builder()
//								.post(post1)
//								.user(user1)
//								.content("Chó mèo nuôi chung đuợc shao?")
//								.build()
//				);
//
//				Comment comment3 = commentRepo.save(
//						Comment.builder()
//								.post(post3)
//								.user(user1)
//								.content("Mèo của bạn múp quá")
//								.build()
//				);
//
//				Comment comment4 = commentRepo.save(
//						Comment.builder()
//								.post(post3)
//								.user(user2)
//								.content("Bạn nên tỉa lông cho bạn chó đi, mùa này nguy cơ sốc nhiệt ấo lắm ^_^!")
//								.build()
//				);
//
//				// init pet------------------------------------------------------------------------//
//				Pet pet1 = petRepo.save(
//						Pet.builder()
//								.user(user1)
//								.name("Sofia")
//								.gender(Const.PET_GENDER_MALE)
//								.age(3)
//								.type("Mèo Mau Ai Cập")
//								.necklaceId("A001")
//								.description("Tui là chó, hum pải mèo. Mowf Mowf....!")
//								.imgLinkList(Collections.emptyList())
//								.createdAt(LocalDateTime.now().minusWeeks(3))
//								.updateAt(LocalDateTime.now().minusWeeks(3))
//								.build()
//				);
//
//				Pet pet2 = petRepo.save(
//						Pet.builder()
//								.user(user1)
//								.name("Ngúi")
//								.gender(Const.PET_GENDER_FEMALE)
//								.age(18)
//								.type("Mèo Tam Thể")
//								.necklaceId("A002")
//								.description("Tui tên Ngúi, họ tên đầy đủ là Ngusi")
//								.imgLinkList(Collections.emptyList())
//								.createdAt(LocalDateTime.now().minusWeeks(1))
//								.updateAt(LocalDateTime.now().minusWeeks(1))
//								.build()
//				);
//
//				Pet pet3 = petRepo.save(
//						Pet.builder()
//								.user(user2)
//								.name("Đum")
//								.gender(Const.PET_GENDER_MALE)
//								.age(10)
//								.type("Mèo Anh Lông Ngắn")
//								.necklaceId("A003")
//								.description("Tuy chân tui ngắn nhưng mà tui xinh")
//								.imgLinkList(Collections.emptyList())
//								.createdAt(LocalDateTime.now().minusWeeks(2))
//								.updateAt(LocalDateTime.now().minusWeeks(1))
//								.build()
//				);
//
//				Pet pet4 = petRepo.save(
//						Pet.builder()
//								.user(user2)
//								.name("Sol")
//								.gender(Const.PET_GENDER_FEMALE)
//								.age(19)
//								.type("Mèo Ba Tư")
//								.necklaceId("A004")
//								.description("Tui xinh, tui đẹp nung ninh")
//								.imgLinkList(Collections.emptyList())
//								.createdAt(LocalDateTime.now().minusWeeks(6))
//								.updateAt(LocalDateTime.now().minusWeeks(2))
//								.build()
//				);
//
//				// init service------------------------------------------------------------//
//				Services services1 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc1)
//								.serviceStatus(activeService)
//								.fee(250000)
//								.name("Combo Siêu Sạch")
//								.rating(5)
//								.build()
//				);
//
//				Services services2 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc1)
//								.serviceStatus(activeService)
//								.fee(350000)
//								.name("Combo Siêu Đẹp")
//								.rating(4)
//								.build()
//				);
//
//				Services services3 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc1)
//								.serviceStatus(activeService)
//								.fee(350000)
//								.name("Combo nhuộm tai đuôi, 4 chân")
//								.rating(4)
//								.build()
//				);
//
//				Services services4 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc1)
//								.serviceStatus(activeService)
//								.fee(400000)
//								.name("Combo Siêu Thư Giãn")
//								.rating(3)
//								.build()
//				);
//
//				Services services5 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc2)
//								.serviceStatus(activeService)
//								.fee(110000)
//								.name("Vacxin 7 bệnh cho chó - VANGUARD ZOETIS ")
//								.rating(5)
//								.build()
//				);
//
//				Services services6 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc2)
//								.serviceStatus(activeService)
//								.fee(350000)
//								.name("Vacxin 7 bệnh cho Mèo - Vaccine Nga")
//								.rating(5)
//								.build()
//				);
//
//				Services services7 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc2)
//								.serviceStatus(activeService)
//								.fee(550000)
//								.name("Triệt sản Mèo")
//								.rating(4)
//								.build()
//				);
//
//				Services services8 = serviceRepo.save(
//						Services.builder()
//								.serviceCenter(sc2)
//								.serviceStatus(activeService)
//								.fee(700000)
//								.name("Triệt sản chó")
//								.rating(4)
//								.build()
//				);
//
//				// init appointment--------------------------------------------------------//
//				Appointment appointment1 = appointmentRepo.save(
//						Appointment.builder()
//								.pet(pet1)
//								.serviceProvider(sp1)
//								.appointmentStatus(successfulAppointment)
//								.type(Const.APPOINTMENT_TYPE_SERVICE)
//								.date(LocalDateTime.now().minusDays(3))
//								.fee(600000)
//								.build()
//				);
//
//				Appointment appointment2 = appointmentRepo.save(
//						Appointment.builder()
//								.pet(pet2)
//								.serviceProvider(sp2)
//								.appointmentStatus(successfulAppointment)
//								.type(Const.APPOINTMENT_TYPE_SERVICE)
//								.date(LocalDateTime.now().minusDays(12))
//								.fee(750000)
//								.build()
//				);
//
//				Appointment appointment3 = appointmentRepo.save(
//						Appointment.builder()
//								.pet(pet3)
//								.serviceProvider(sp3)
//								.appointmentStatus(successfulAppointment)
//								.type(Const.APPOINTMENT_TYPE_HEALTH)
//								.date(LocalDateTime.now().minusDays(30))
//								.fee(460000)
//								.build()
//				);
//
//				Appointment appointment4 = appointmentRepo.save(
//						Appointment.builder()
//								.pet(pet4)
//								.serviceProvider(sp4)
//								.appointmentStatus(successfulAppointment)
//								.type(Const.APPOINTMENT_TYPE_HEALTH)
//								.date(LocalDateTime.now().minusDays(9))
//								.fee(1250000)
//								.build()
//				);
//
//				// init appointment service------------------------------------------------//
//				AppointmentService appointmentService1 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment1)
//								.services(services1)
//								.build()
//				);
//
//				AppointmentService appointmentService2 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment1)
//								.services(services2)
//								.build()
//				);
//
//				AppointmentService appointmentService3 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment2)
//								.services(services3)
//								.build()
//				);
//
//				AppointmentService appointmentService4 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment2)
//								.services(services4)
//								.build()
//				);
//
//				AppointmentService appointmentService5 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment3)
//								.services(services5)
//								.build()
//				);
//
//				AppointmentService appointmentService6 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment3)
//								.services(services6)
//								.build()
//				);
//
//				AppointmentService appointmentService7 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment4)
//								.services(services7)
//								.build()
//				);
//
//				AppointmentService appointmentService8 = appointmentServiceRepo.save(
//						AppointmentService.builder()
//								.appointment(appointment4)
//								.services(services8)
//								.build()
//				);
//
//				// init service report-----------------------------------------------------//
//				ServiceReport report1 = serviceReportRepo.save(
//						ServiceReport.builder()
//								.appointment(appointment1)
//								.report("Đã tiêm phòng vaccine dại")
//								.extraContent("Sau khi tiêm xong cần chăm sóc chó tốt hơn, kiêng tắm; Kiêng thức ăn có chứa nhiều mỡ, sữa, đồ tanh ít nhất là 1 tuần.")
//								.date(appointment1.getDate())
//								.location(appointment1.getServiceProvider().getServiceCenter().getAddress())
//								.build()
//				);
//
//				ServiceReport report2 = serviceReportRepo.save(
//						ServiceReport.builder()
//								.appointment(appointment2)
//								.report("Đã triệt sản")
//								.extraContent("Không để thú cưng bị ướt. Không cho ăn quá no, hạn chế những loại thức ăn có thể gây kích ứng như cá biển, thịt gà.")
//								.date(appointment2.getDate())
//								.location(appointment2.getServiceProvider().getServiceCenter().getAddress())
//								.build()
//				);
//
//				ServiceReport report3 = serviceReportRepo.save(
//						ServiceReport.builder()
//								.appointment(appointment3)
//								.report("Đã cạo lông toàn thân")
//								.extraContent("Nếu có triệu chứng ngứa hay nhưng biểu hiện lạ hãy liên hệ với trung tâm nhanh nhất có thể để được hỗ trợ kịp thời.")
//								.date(appointment3.getDate())
//								.location(appointment3.getServiceProvider().getServiceCenter().getAddress())
//								.build()
//				);
//
//				ServiceReport report4 = serviceReportRepo.save(
//						ServiceReport.builder()
//								.appointment(appointment4)
//								.report("Đã nhuộm tai màu hồng và tỉa lông toàn thân")
//								.extraContent("Nếu có triệu chứng ngứa hay nhưng biểu hiện lạ hãy liên hệ với trung tâm nhanh nhất có thể để được hỗ trợ kịp thời.")
//								.date(appointment4.getDate())
//								.location(appointment4.getServiceProvider().getServiceCenter().getAddress())
//								.build()
//				);
//
//				// init shop plan------------------------------------------------------------------//
//				ShopPlan shopPlan1 = shopPlanRepo.save(
//						ShopPlan.builder()
//								.planStatus(activePlan)
//								.name("PetVip1")
//								.fee(450000)
//								.duration(150)
//								.description("Gói VIP 1")
//								.build()
//				);
//
//				ShopPlan shopPlan2 = shopPlanRepo.save(
//						ShopPlan.builder()
//								.planStatus(activePlan)
//								.name("PetVip2")
//								.fee(700000)
//								.duration(360)
//								.description("Gói VIP 2")
//								.build()
//				);
//
//				// init shop-----------------------------------------------------------------------//
//				Shop shop1 = shopRepo.save(
//						Shop.builder()
//								.account(shopAcc1)
//								.shopPlan(shopPlan1)
//								.shopStatus(activeShop)
//								.planPurchasedDate(LocalDateTime.now().minusWeeks(5))
//								.name("Pet Station")
//								.address("369A Tân Sơn Nhì, Phường Tân Thành, Quận Tân Phú, TP.Hồ Chí Minh")
//								.rating(4)
//								.build()
//				);
//
//				Shop shop2 = shopRepo.save(
//						Shop.builder()
//								.account(shopAcc2)
//								.shopPlan(shopPlan2)
//								.shopStatus(activeShop)
//								.planPurchasedDate(LocalDateTime.now().minusMonths(2))
//								.name("Paddiers")
//								.address("116 Nguyễn Văn Thủ, Phường Đa Kao, Quận 1, Thành phố Hồ Chí Minh")
//								.rating(5)
//								.build()
//				);
//
//				// init product category--------------------------------------------------------//
//				ProductCategory category1 = productCategoryRepo.save(
//						ProductCategory.builder()
//								.name("Thức Ăn")
//								.build()
//				);
//
//				ProductCategory category2 = productCategoryRepo.save(
//						ProductCategory.builder()
//								.name("Phụ kiện")
//								.build()
//				);
//
//				ProductCategory category3 = productCategoryRepo.save(
//						ProductCategory.builder()
//								.name("Đồ Chơi")
//								.build()
//				);
//
//				ProductCategory category4 = productCategoryRepo.save(
//						ProductCategory.builder()
//								.name("Thời Trang")
//								.build()
//				);
//
//				// init product-----------------------------------------------------------------//
//				Product product1 = productRepo.save(
//						Product.builder()
//								.shop(shop1)
//								.productStatus(availableProduct)
//								.productCategory(category1)
//								.name("Hạt Tươi Taste Of The Wild Cho Chó Trưởng Thành")
//								.price(125000)
//								.soldQty(100)
//								.availableQty(50)
//								.rating(3)
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				Product product2 = productRepo.save(
//						Product.builder()
//								.shop(shop1)
//								.productStatus(availableProduct)
//								.productCategory(category2)
//								.name("Vòng Cổ Chó Mèo Có Chuông Dày 1cm	")
//								.price(15000)
//								.soldQty(20)
//								.availableQty(100)
//								.rating(4)
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				Product product3 = productRepo.save(
//						Product.builder()
//								.shop(shop1)
//								.productStatus(availableProduct)
//								.productCategory(category3)
//								.name("Cỏ Mèo Bạc Hà Catnip Cho Mèo")
//								.price(30000)
//								.soldQty(100)
//								.availableQty(50)
//								.rating(4)
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				Product product4 = productRepo.save(
//						Product.builder()
//								.shop(shop1)
//								.productStatus(availableProduct)
//								.productCategory(category4)
//								.name("Áo Cho Chó Mèo Sơ Mi Trái Cây - Chuối Vàng")
//								.price(60000)
//								.soldQty(21)
//								.availableQty(30)
//								.rating(4)
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				// init order--------------------------------------------------------------//
//				Order order1 = orderRepo.save(
//						Order.builder()
//								.user(user1)
//								.paymentMethod(VNPayMethod)
//								.orderStatus(confirmedOrder)
//								.orderDate(LocalDateTime.now().minusWeeks(7))
//								.totalPrice(775000)
//								.build()
//				);
//
//				Order order2 = orderRepo.save(
//						Order.builder()
//								.user(user2)
//								.paymentMethod(VNPayMethod)
//								.orderStatus(successfulOrder)
//								.orderDate(LocalDateTime.now().minusWeeks(1))
//								.totalPrice(120000)
//								.build()
//				);
//
//				// init order detail-----------------------------------------------------//
//				OrderDetail detail1 = orderDetailRepo.save(
//						OrderDetail.builder()
//								.order(order1)
//								.product(product1)
//								.productName(product1.getName())
//								.productPrice(product1.getPrice())
//								.productQty(5)
//								.build()
//				);
//
//				OrderDetail detail2 = orderDetailRepo.save(
//						OrderDetail.builder()
//								.order(order1)
//								.product(product2)
//								.productName(product2.getName())
//								.productPrice(product2.getPrice())
//								.productQty(10)
//								.build()
//				);
//
//				OrderDetail detail3 = orderDetailRepo.save(
//						OrderDetail.builder()
//								.order(order2)
//								.product(product3)
//								.productName(product3.getName())
//								.productPrice(product3.getPrice())
//								.productQty(2)
//								.build()
//				);
//
//				OrderDetail detail4 = orderDetailRepo.save(
//						OrderDetail.builder()
//								.order(order2)
//								.product(product4)
//								.productName(product4.getName())
//								.productPrice(product4.getPrice())
//								.productQty(1)
//								.build()
//				);
//
//				//init feedback------------------------------------------------------------------//
//				Feedback feedback1 = feedBackRepo.save(
//						Feedback.builder()
//								.user(user1)
//								.product(product1)
//								.isReported(false)
//								.content("Hạt rất thơm.Bé nhà mình ăn được nhiều mà cũng không bị tiêu chảy nữa")
//								.rating(5)
//								.build()
//				);
//
//				Feedback feedback2 = feedBackRepo.save(
//						Feedback.builder()
//								.user(user1)
//								.product(product2)
//								.isReported(true)
//								.content("Vòng cổ mới sử dụng đợc 2 ngày đã bong chốt, bạn mèo của mình đeo vào thì bị stress !!!")
//								.rating(0)
//								.build()
//				);
//
//				Feedback feedback3 = feedBackRepo.save(
//						Feedback.builder()
//								.user(user2)
//								.product(product3)
//								.isReported(false)
//								.content("Mèo mình ngửi vào xong sủa lun mà")
//								.rating(5)
//								.build()
//				);
//
//				Feedback feedback4 = feedBackRepo.save(
//						Feedback.builder()
//								.user(user2)
//								.product(product4)
//								.isReported(true)
//								.content("Đường chỉ ko bền, vừa mua về ã rơi cúc.Nói chung là dởm, ko nên mua")
//								.rating(0)
//								.build()
//				);
//			}
//		};
//	}

}
