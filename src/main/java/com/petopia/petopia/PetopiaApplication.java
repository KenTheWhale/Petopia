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

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	private final AppointmentRepo appointmentRepo;

	private final ServiceReportRepo serviceReportRepo;

	private final TokenRepo tokenRepo;

	public static void main(String[] args) {
		SpringApplication.run(PetopiaApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDate(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

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
//				// init account
//				List<Account> accountList = new ArrayList<>();
//
//				Account adminAcc = Account.builder()
//						.email("admin@peto.com")
//						.name("admin")
//						.avatarLink("")
//						.password(passwordEncoder.encode("admin"))
//						.accountStatus(activeAccount)
//						.role(Role.ADMIN)
//						.build();
//				accountList.add(adminAcc);
//
//				Account shopAcc = Account.builder()
//						.email("shop@peto.com")
//						.name("shop")
//						.avatarLink("")
//						.password(passwordEncoder.encode("shop"))
//						.accountStatus(activeAccount)
//						.role(Role.SHOP_OWNER)
//						.build();
//				accountList.add(shopAcc);
//
//				Account gpAdminAcc = Account.builder()
//						.email("gpad@peto.com")
//						.name("group admin")
//						.avatarLink("")
//						.password(passwordEncoder.encode("gpad"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_ADMIN)
//						.build();
//				accountList.add(gpAdminAcc);
//
//				Account gpManagerAcc = Account.builder()
//						.email("gpma@peto.com")
//						.name("group manager")
//						.avatarLink("")
//						.password(passwordEncoder.encode("gpma"))
//						.accountStatus(activeAccount)
//						.role(Role.GROUP_MANAGER)
//						.build();
//				accountList.add(gpManagerAcc);
//
//				Account vetAcc = Account.builder()
//						.email("vet@peto.com")
//						.name("vet")
//						.avatarLink("")
//						.password(passwordEncoder.encode("vet"))
//						.accountStatus(activeAccount)
//						.role(Role.VET)
//						.build();
//				accountList.add(vetAcc);
//
//				Account userAcc = Account.builder()
//						.email("user@peto.com")
//						.name("user")
//						.avatarLink("")
//						.password(passwordEncoder.encode("user"))
//						.accountStatus(activeAccount)
//						.role(Role.USER)
//						.build();
//				accountList.add(userAcc);
//				accountRepo.saveAll(accountList);
//
//				userRepo.save(
//						User.builder()
//								.account(userAcc)
//								.gender(Const.HUMAN_GENDER_MALE)
//								.address("")
//								.phone("")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//
//				// init token
//				List<Token> tokenList = new ArrayList<>();
//				for(Account account: accountList){
//					tokenList.add(tokenService.createNewAccessToken(account, jwtService.generateAccessToken(account)));
//					tokenList.add(tokenService.createNewRefreshToken(account, jwtService.generateRefreshToken(account)));
//				}
//
//				System.out.println("# ADMIN TOKEN: " + tokenList.get(0).getValue());
//				System.out.println("# SHOP OWNER TOKEN: " + tokenList.get(2).getValue());
//				System.out.println("# GROUP ADMIN TOKEN: " + tokenList.get(4).getValue());
//				System.out.println("# GROUP MANAGER TOKEN: " + tokenList.get(6).getValue());
//				System.out.println("# VET TOKEN: " + tokenList.get(8).getValue());
//				System.out.println("# USER TOKEN: " + tokenList.get(10).getValue());
//
//
//				// init 10 health report
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//				String[] dateList = {
//						"2020-06-02 12:00:00", "2020-05-10 09:30:00",
//						"2021-03-15 14:45:00", "2021-06-20 10:15:00",
//						"2021-09-05 16:30:00","2021-12-10 11:45:00",
//						"2022-02-25 09:00:00","2022-05-15 15:30:00",
//						"2022-08-20 13:15:00","2022-11-30 16:45:00"
//				};
//
//				String[] reportList = {
//						"Tiêm vắc-xin bạch cầu","Tiêm vắc-xin hạt nhân",
//						"Tiêm vắc-xin cúm","Tiêm vắc-xin dại",
//						"Tiêm vắc-xin tụ huyết trùng","Tiêm vắc-xin viêm gan B",
//						"Tiêm vắc-xin bệnh cảm cúm","Tiêm vắc-xin viêm gan C",
//						"Tiêm vắc-xin bạch hầu","Tiêm vắc-xin viêm gan A"
//				};
//
//				String[] extraContentList = {
//						"Sức khỏe yếu, cần khám lại","Không có vấn đề đáng ngại",
//						"Không có vấn đề đáng ngại","Không có vấn đề đáng ngại",
//						"Không có vấn đề đáng ngại","Không có vấn đề đáng ngại",
//						"Không có vấn đề đáng ngại","Không có vấn đề đáng ngại",
//						"Không có vấn đề đáng ngại","Không có vấn đề đáng ngại"
//				};
//
//				String[] placeList = {
//						"Tại nhà","Tại phòng khám",
//						"Tại phòng khám","Tại phòng khám",
//						"Tại phòng khám","Tại phòng khám",
//						"Tại phòng khám","Tại phòng khám",
//						"Tại phòng khám","Tại phòng khám"
//				};
//
//				String[] doctorNameList = {
//						"Nguyễn Văn A","Trần Thị B",
//						"Lê Thị C","Phạm Văn D",
//						"Hoàng Thị E","Nguyễn Văn F",
//						"Trần Văn G","Lê Thị H",
//						"Phạm Thị I","Hoàng Văn J"
//				};
//
//				String[] avatarList = {
//						"https://via.placeholder.com/150","https://via.placeholder.com/150",
//						"https://via.placeholder.com/150","https://via.placeholder.com/150",
//						"https://via.placeholder.com/150","https://via.placeholder.com/150",
//						"https://via.placeholder.com/150","https://via.placeholder.com/150",
//						"https://via.placeholder.com/150","https://via.placeholder.com/150"
//				};
//
//				Account hoang_anh_account = accountRepo.save(
//						Account.builder()
//								.accountStatus(activeAccount)
//								.name("Lương Hoàng Anh")
//								.email("anhlhse170179@fpt.edu.vn")
//								.password(passwordEncoder.encode("Iamnothoanganh"))
//								.role(Role.USER)
//								.avatarLink("https://avatars.githubusercontent.com/u/131256206?v=4")
//								.build()
//				);
//
//				tokenRepo.save(Token.builder().account(hoang_anh_account).tokenStatus(activeToken).value(jwtService.generateAccessToken(hoang_anh_account)).type(Const.TOKEN_TYPE_ACCESS).build());
//				tokenRepo.save(Token.builder().account(hoang_anh_account).tokenStatus(activeToken).value(jwtService.generateRefreshToken(hoang_anh_account)).type(Const.TOKEN_TYPE_REFRESH).build());
//
//				List<Account> accounts = new ArrayList<>();
//
//				for(int i = 0; i < 10; i++){
//					accounts.add(
//							Account.builder()
//									.accountStatus(activeAccount)
//									.name(doctorNameList[i])
//									.email(doctorNameList[i].replace(" ", "") + "@gmail.com")
//									.password(passwordEncoder.encode("pass" + i))
//									.role(Role.VET)
//									.avatarLink("https://via.placeholder.com/150")
//									.build()
//					);
//				}
//
//				accountRepo.saveAll(accounts);
//
//				for(int i = 0; i < 10; i++){
//					tokenRepo.save(Token.builder().account(accounts.get(i)).tokenStatus(activeToken).value(jwtService.generateAccessToken(accounts.get(i))).type(Const.TOKEN_TYPE_ACCESS).build());
//					tokenRepo.save(Token.builder().account(accounts.get(i)).tokenStatus(activeToken).value(jwtService.generateRefreshToken(accounts.get(i))).type(Const.TOKEN_TYPE_REFRESH).build());
//				}
//
//				User hoang_anh_user = userRepo.save(
//						User.builder()
//								.account(hoang_anh_account)
//								.gender(Const.HUMAN_GENDER_MALE)
//								.address("Nhà Trắng")
//								.phone("0977545450")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				Pet tran_dan = petRepo.save(
//						Pet.builder()
//								.user(hoang_anh_user)
//								.name("Trần Dần")
//								.gender(Const.PET_GENDER_FEMALE)
//								.age(69)
//								.createdAt(LocalDateTime.parse("2013-12-31 21:30:00", formatter))
//								.updateAt(LocalDateTime.parse("2020-01-01 09:40:03", formatter))
//								.type("Hổ")
//								.necklaceId("BLOCK3W")
//								.description("Pet tiên tri vũ trụ")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				Pet johnny_dang = petRepo.save(
//						Pet.builder()
//								.user(hoang_anh_user)
//								.name("Johnny Đặng")
//								.gender(Const.PET_GENDER_MALE)
//								.age(21)
//								.createdAt(LocalDateTime.parse("2015-05-15 10:12:07", formatter))
//								.updateAt(LocalDateTime.parse("2024-02-29 23:59:59", formatter))
//								.type("Poppy")
//								.necklaceId("BLOCK100W")
//								.description("Pet vô tri vũ trụ")
//								.imgLinkList(Collections.emptyList())
//								.build()
//				);
//
//				List<Appointment> appointments = new ArrayList<>();
//				for(int i = 0; i < 5; i++){
//					appointments.add(
//							Appointment.builder()
//									.pet(tran_dan)
//									.account(accounts.get(i))
//									.appointmentStatus(successfulAppointment)
//									.type(Const.APPOINTMENT_TYPE_HEALTH)
//									.date(LocalDateTime.parse(dateList[i], formatter))
//									.location(placeList[i])
//									.fee((i + 10) * 1000 + 20000)
//									.build()
//					);
//				}
//
//				for(int i = 5; i < 10; i++){
//					appointments.add(
//							Appointment.builder()
//									.pet(johnny_dang)
//									.account(accounts.get(i))
//									.appointmentStatus(successfulAppointment)
//									.type(Const.APPOINTMENT_TYPE_HEALTH)
//									.date(LocalDateTime.parse(dateList[i], formatter))
//									.location(placeList[i])
//									.fee((i + 10) * 1000 + 20000)
//									.build()
//					);
//				}
//
//				appointmentRepo.saveAll(appointments);
//
//				List<ServiceReport> serviceReports = new ArrayList<>();
//				for(int i = 0; i < 10; i++){
//					serviceReports.add(
//							ServiceReport.builder()
//									.appointment(appointments.get(i))
//									.report(reportList[i])
//									.date(appointments.get(i).getDate())
//									.extraContent(extraContentList[i])
//									.build()
//					);
//				}
//
//				serviceReportRepo.saveAll(serviceReports);
			}
		};
	}

}
