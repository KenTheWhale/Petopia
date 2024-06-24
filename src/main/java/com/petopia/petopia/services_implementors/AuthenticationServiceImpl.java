package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.enums.Role;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.AccountStatus;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.models.entity_models.User;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.request_models.ResetPasswordRequest;
import com.petopia.petopia.models.request_models.SendOtpRequest;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.AccountStatusRepo;
import com.petopia.petopia.repositories.TokenRepo;
import com.petopia.petopia.repositories.UserRepo;
import com.petopia.petopia.services.AuthenticationService;
import com.petopia.petopia.services.JWTService;
import com.petopia.petopia.services.TokenService;
import com.petopia.petopia.services.TokenStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenRepo tokenRepo;

    private final TokenService tokenService;

    private final TokenStatusService tokenStatusService;

    private final AccountRepo accountRepo;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AccountStatusRepo accountStatusRepo;

    private final JavaMailSender mailSender;

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
    private final UserRepo userRepo;


    @Override
    public LoginResponse login(LoginRequest request) {
        if (checkIfStringIsValid(request.getEmail()) && checkIfStringIsValid(request.getPassword())) {
            if (checkIfMailIsValid(request.getEmail())) {
                Account account = accountRepo.findByEmail(request.getEmail()).orElse(null);
                if (account != null && passwordEncoder.matches(request.getPassword(), account.getPassword())) {
                    if (account.getAccountStatus().getStatus().equals(Const.ACCOUNT_STATUS_ACTIVE)) {
                        Token oldAccess = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(account.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
                        Token oldRefresh = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(account.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);

                        if (oldAccess != null) tokenStatusService.applyExpiredStatus(oldAccess);
                        if (oldRefresh != null) tokenStatusService.applyExpiredStatus(oldRefresh);

                        Token accessToken = tokenService.createNewAccessToken(account);
                        tokenService.createNewRefreshToken(account);

                        String address = "";

                        switch (account.getRole()) {
                            case USER:
                                address = account.getUser().getAddress();
                                break;
                            case SHOP_OWNER:
                                address = account.getShop().getAddress();
                                break;
                            case SERVICE_CENTER_MANAGER:
                                address = account.getServiceCenter().getAddress();
                                break;
                            case SERVICE_PROVIDER:
                                address = account.getServiceProvider().getServiceCenter().getAddress();
                                break;
                        }

                        return LoginResponse.builder()
                                .status("200")
                                .message("Đăng nhập thành công")
                                .account(
                                        LoginResponse.AccountResponse.builder()
                                                .status(account.getAccountStatus().getStatus())
                                                .name(account.getName())
                                                .email(account.getEmail())
                                                .role(account.getRole().name())
                                                .avatar(account.getAvatar())
                                                .background(account.getBackground())
                                                .address(address)
                                                .accessToken(accessToken.getValue())
                                                .build()
                                )
                                .build();
                    }
                    return LoginResponse.builder()
                            .status("400")
                            .message("Tài khoản này không khả dụng")
                            .account(
                                    LoginResponse.AccountResponse.builder()
                                            .status("")
                                            .name("")
                                            .email("")
                                            .role("")
                                            .avatar("")
                                            .background("")
                                            .address("")
                                            .accessToken("")
                                            .build()
                            )
                            .build();
                }
                return LoginResponse.builder()
                        .status("400")
                        .message("Tên người dùng hoặc mật khẩu không đúng")
                        .account(
                                LoginResponse.AccountResponse.builder()
                                        .status("")
                                        .name("")
                                        .email("")
                                        .role("")
                                        .avatar("")
                                        .background("")
                                        .address("")
                                        .accessToken("")
                                        .build()
                        )
                        .build();
            }
            return LoginResponse.builder()
                    .status("400")
                    .message("Email sai định dạng")
                    .account(
                            LoginResponse.AccountResponse.builder()
                                    .status("")
                                    .name("")
                                    .email("")
                                    .role("")
                                    .avatar("")
                                    .background("")
                                    .address("")
                                    .accessToken("")
                                    .build()
                    )
                    .build();
        }
        return LoginResponse.builder()
                .status("400")
                .message("Email hoặc mật khẩu đang trống")
                .account(
                        LoginResponse.AccountResponse.builder()
                                .status("")
                                .name("")
                                .email("")
                                .role("")
                                .avatar("")
                                .background("")
                                .address("")
                                .accessToken("")
                                .build()
                )
                .build();
    }

    private boolean checkIfStringIsValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean checkIfMailIsValid(String mail) {
        String regex1 = "^[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z0-9]+\\.[a-z]+\\.[a-z]+$";
        String regex2 = "^[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z0-9]+\\.[a-z]+$";
        return Pattern.compile(regex1).matcher(mail).matches() || Pattern.compile(regex2).matcher(mail).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[0-9]{10}$";

        return Pattern.compile(regex).matcher(phoneNumber).matches();
    }


    @Override
    public CreateAccountResponse Register(CreateAccountRequest request) {
        Optional<Account> optionalAccount = accountRepo.findByEmail(request.getEmail());

        if (!checkIfMailIsValid(request.getEmail())) {
            return CreateAccountResponse.builder()
                    .status("400")
                    .message("Email không hợp lệ")
                    .build();
        }

        if (optionalAccount.isPresent()) {
            return CreateAccountResponse.builder()
                    .status("409")
                    .message("Email đã tồn tại")
                    .build();
        }
        if(!isValidPhoneNumber(request.getPhoneNumber())){
            return CreateAccountResponse.builder()
                    .status("400")
                    .message("Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại có 10 chữ số")
                    .build();
        }

        User optionalUser = userRepo.findUserByPhone(request.getPhoneNumber());
        if (optionalUser != null) {
            return CreateAccountResponse.builder()
                    .status("409")
                    .message("Số điện thoại đã tồn tại")
                    .build();
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return CreateAccountResponse
                    .builder()
                    .status("400")
                    .message("Mật khẩu xác nhận không đúng vui lòng thử lại")
                    .build();
        }



        AccountStatus accountStatus = accountStatusRepo.findByStatus(Const.ACCOUNT_STATUS_ACTIVE);

        Account newAccount = Account.builder()
                .name(request.getName())
                .background("")
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .avatar("https://via.placeholder.com/150")
                .role(Role.USER)
                .accountStatus(accountStatus)
                .build();

        accountRepo.save(newAccount);

        User user = User.builder()
                .account(newAccount)
                .address("")
                .phone(request.getPhoneNumber())
                .realName("")
                .gender("")
                .build();

        userRepo.save(user);

        return CreateAccountResponse.builder()
                .status("200")
                .message("Tạo tài khoản thành công")
                .build();
    }

    @Override
    public SendOTPResponse sendOTP(SendOtpRequest request) {
        Account account = accountRepo.findByEmail(request.getEmail()).orElse(null);
        if (account == null) {
            return SendOTPResponse
                    .builder()
                    .status("400")
                    .message("Không tìm thấy tài khoản với email" + request.getEmail())
                    .build();
        } else {
            String otp = generateOTP(account);
            sendOTPEmail(account.getEmail(), otp);
            return SendOTPResponse
                    .builder()
                    .status("200")
                    .message("Đã gửi OTP đến gmail " + request.getEmail())
                    .build();
        }
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        Account account = accountRepo.findByEmail(request.getEmail()).orElse(null);
        if (account == null) {
            return ResetPasswordResponse
                    .builder()
                    .status("400")
                    .message("Không tìm thấy tài khoản với email " + request.getEmail())
                    .build();
        } else {
            String otp = generateOTP(account);

            if (otp.equals(request.getOtp().trim())) {
                if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                    return ResetPasswordResponse
                            .builder()
                            .status("400")
                            .message("Mật khẩu xác nhận không trùng khớp")
                            .build();
                }
                account.setPassword(passwordEncoder.encode(request.getNewPassword()));
                accountRepo.save(account);
                return ResetPasswordResponse
                        .builder()
                        .status("200")
                        .message("Đã đặt lại mật khẩu thành công cho email " + request.getEmail())
                        .build();
            }
            return ResetPasswordResponse
                    .builder()
                    .status("400")
                    .message("OTP không chính xác")
                    .build();

        }
    }


    public static String generateOTP(Account account) {
        long currentTimeMillis = System.currentTimeMillis();
        long otpValidity = currentTimeMillis / OTP_VALID_DURATION;

        String data = account.getEmail() + otpValidity;
        String hash = hashData(data);

        long otp = Math.abs(hash.hashCode()) % 1000000;

        return String.format("%06d", otp);
    }

    private static String hashData(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating OTP", e);
        }
    }

    public void sendOTPEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("MÃ OPT CỦA BẠN");
        message.setText("Mã OTP của bạn là: " + otp + ". OTP sẽ có hiệu lực trong vòng 1 phút");

        mailSender.send(message);
    }


}
