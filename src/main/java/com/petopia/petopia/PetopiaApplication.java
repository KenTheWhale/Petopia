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
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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

	private final TokenRepo tokenRepo;

	public static void main(String[] args) {
		SpringApplication.run(PetopiaApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDate(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

				// init token status
				TokenStatus activeToken = tokenStatusRepo.save(
						TokenStatus.builder().status(Const.TOKEN_STATUS_ACTIVE).build()
				);

				TokenStatus expiredToken = tokenStatusRepo.save(
						TokenStatus.builder().status(Const.TOKEN_STATUS_EXPIRED).build()
				);

				// init account status
				AccountStatus activeAccount = accountStatusRepo.save(
						AccountStatus.builder().status(Const.ACCOUNT_STATUS_ACTIVE).build()
				);

				AccountStatus bannedAccount = accountStatusRepo.save(
						AccountStatus.builder().status(Const.ACCOUNT_STATUS_BANNED).build()
				);

				// init group status
				GroupStatus activeGroup = groupStatusRepo.save(
						GroupStatus.builder().status(Const.GROUP_STATUS_ACTIVE).build()
				);

				GroupStatus inactiveGroup = groupStatusRepo.save(
						GroupStatus.builder().status(Const.GROUP_STATUS_INACTIVE).build()
				);

				GroupStatus bannedGroup = groupStatusRepo.save(
						GroupStatus.builder().status(Const.GROUP_STATUS_BANNED).build()
				);;

				GroupStatus deletedGroup = groupStatusRepo.save(
						GroupStatus.builder().status(Const.GROUP_STATUS_DELETED).build()
				);;

				// init post status
				PostStatus publicPost = postStatusRepo.save(
						PostStatus.builder().status(Const.POST_STATUS_PUBLIC).build()
				);

				PostStatus onlyFriendPost = postStatusRepo.save(
						PostStatus.builder().status(Const.POST_STATUS_ONLY_FRIEND).build()
				);;

				PostStatus onlyMePost = postStatusRepo.save(
						PostStatus.builder().status(Const.POST_STATUS_ONLY_ME).build()
				);;

				PostStatus deletedPost = postStatusRepo.save(
						PostStatus.builder().status(Const.POST_STATUS_DELETED).build()
				);;

				// init appointment status
				AppointmentStatus pendingAppointment = appointmentStatusRepo.save(
						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_PENDING).build()
				);

				AppointmentStatus confirmedAppointment = appointmentStatusRepo.save(
						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_CONFIRMED).build()
				);;

				AppointmentStatus cancelledAppointment = appointmentStatusRepo.save(
						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_CANCELLED).build()
				);;

				AppointmentStatus successfulAppointment = appointmentStatusRepo.save(
						AppointmentStatus.builder().status(Const.APPOINTMENT_STATUS_SUCCESSFUL).build()
				);;


				// init order status
				OrderStatus processingOrder = orderStatusRepo.save(
						OrderStatus.builder().status(Const.ORDER_STATUS_PROCESSING).build()
				);

				OrderStatus confirmedOrder = orderStatusRepo.save(
						OrderStatus.builder().status(Const.ORDER_STATUS_CONFIRMED).build()
				);;

				OrderStatus deliveringOrder = orderStatusRepo.save(
						OrderStatus.builder().status(Const.ORDER_STATUS_DELIVERING).build()
				);;

				OrderStatus successfulOrder = orderStatusRepo.save(
						OrderStatus.builder().status(Const.ORDER_STATUS_SUCCESSFUL).build()
				);;

				OrderStatus cancelledOrder = orderStatusRepo.save(
						OrderStatus.builder().status(Const.ORDER_STATUS_CANCELLED).build()
				);;

				// init plan status
				PlanStatus activePlan = planStatusRepo.save(
						PlanStatus.builder().status(Const.PLAN_STATUS_ACTIVE).build()
				);

				PlanStatus inactivePlan = planStatusRepo.save(
						PlanStatus.builder().status(Const.PLAN_STATUS_INACTIVE).build()
				);;

				// init shop status
				ShopStatus activeShop = shopStatusRepo.save(
						ShopStatus.builder().status(Const.SHOP_STATUS_ACTIVE).build()
				);

				ShopStatus closedShop = shopStatusRepo.save(
						ShopStatus.builder().status(Const.SHOP_STATUS_CLOSED).build()
				);;

				ShopStatus bannedShop = shopStatusRepo.save(
						ShopStatus.builder().status(Const.SHOP_STATUS_BANNED).build()
				);;

				ShopStatus deletedShop = shopStatusRepo.save(
						ShopStatus.builder().status(Const.SHOP_STATUS_DELETED).build()
				);;

				// init product status
				ProductStatus availableProduct = productStatusRepo.save(
						ProductStatus.builder().status(Const.PRODUCT_STATUS_AVAILABLE).build()
				);

				ProductStatus outOfStockProduct = productStatusRepo.save(
						ProductStatus.builder().status(Const.PRODUCT_STATUS_OUT_OF_STOCK).build()
				);;

				ProductStatus deletedProduct = productStatusRepo.save(
						ProductStatus.builder().status(Const.PRODUCT_STATUS_DELETED).build()
				);;

				// init payment method
				PaymentMethod VNPayMethod = paymentMethodRepo.save(
						PaymentMethod.builder().method("VNPay").build()
				);

				// init account
				List<Account> accountList = new ArrayList<>();

				Account adminAcc = Account.builder()
						.email("admin@peto.com")
						.password(passwordEncoder.encode("admin"))
						.accountStatus(activeAccount)
						.role(Role.ADMIN)
						.build();
				accountList.add(adminAcc);

				Account shopAcc = Account.builder()
						.email("shop@peto.com")
						.password(passwordEncoder.encode("shop"))
						.accountStatus(activeAccount)
						.role(Role.SHOP_OWNER)
						.build();
				accountList.add(shopAcc);

				Account gpAdminAcc = Account.builder()
						.email("gpad@peto.com")
						.password(passwordEncoder.encode("gpad"))
						.accountStatus(activeAccount)
						.role(Role.GROUP_ADMIN)
						.build();
				accountList.add(gpAdminAcc);

				Account gpManagerAcc = Account.builder()
						.email("gpma@peto.com")
						.password(passwordEncoder.encode("gpma"))
						.accountStatus(activeAccount)
						.role(Role.GROUP_MANAGER)
						.build();
				accountList.add(gpManagerAcc);

				Account vetAcc = Account.builder()
						.email("vet@peto.com")
						.password(passwordEncoder.encode("vet"))
						.accountStatus(activeAccount)
						.role(Role.VET)
						.build();
				accountList.add(vetAcc);

				Account userAcc = Account.builder()
						.email("user@peto.com")
						.password(passwordEncoder.encode("user"))
						.accountStatus(activeAccount)
						.role(Role.USER)
						.build();
				accountList.add(userAcc);
				accountRepo.saveAll(accountList);

				// init token
				List<Token> tokenList = new ArrayList<>();
				for(Account account: accountList){
					tokenList.add(tokenService.createNewAccessToken(account, jwtService.generateAccessToken(account)));
					tokenList.add(tokenService.createNewRefreshToken(account, jwtService.generateRefreshToken(account)));
				}

				System.out.println("# ADMIN TOKEN: " + tokenList.get(0).getValue());
				System.out.println("# SHOP OWNER TOKEN: " + tokenList.get(2).getValue());
				System.out.println("# GROUP ADMIN TOKEN: " + tokenList.get(4).getValue());
				System.out.println("# GROUP MANAGER TOKEN: " + tokenList.get(6).getValue());
				System.out.println("# VET TOKEN: " + tokenList.get(8).getValue());
				System.out.println("# USER TOKEN: " + tokenList.get(10).getValue());

			}
		};
	}

}
