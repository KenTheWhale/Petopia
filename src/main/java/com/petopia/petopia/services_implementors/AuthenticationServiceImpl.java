package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.enums.Role;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.AccountStatus;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.response_models.LoginResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.AccountStatusRepo;
import com.petopia.petopia.repositories.TokenRepo;
import com.petopia.petopia.services.AuthenticationService;
import com.petopia.petopia.services.JWTService;
import com.petopia.petopia.services.TokenService;
import com.petopia.petopia.services.TokenStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public LoginResponse login(LoginRequest request) {
        if(checkIfStringIsValid(request.getEmail()) && checkIfStringIsValid(request.getPassword())){
            if(checkIfMailIsValid(request.getEmail())){
                Account account = accountRepo.findByEmail(request.getEmail()).orElse(null);
                if(account != null && passwordEncoder.matches(request.getPassword(), account.getPassword())){
                    if(account.getAccountStatus().getStatus().equals(Const.ACCOUNT_STATUS_ACTIVE)){
                        Token oldAccess = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(account.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
                        Token oldRefresh = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(account.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);

                        if(oldAccess != null) tokenStatusService.applyExpiredStatus(oldAccess);
                        if(oldRefresh != null) tokenStatusService.applyExpiredStatus(oldRefresh);

                        Token accessToken = tokenService.createNewAccessToken(account);
                        tokenService.createNewRefreshToken(account);

                        return LoginResponse.builder()
                                .status("200")
                                .message("Login successfully")
                                .account(
                                        LoginResponse.AccountResponse.builder()
                                                .status(account.getAccountStatus().getStatus())
                                                .name(account.getName())
                                                .email(account.getEmail())
                                                .role(account.getRole().name())
                                                .avatar(account.getAvatar())
                                                .background(account.getBackground())
                                                .accessToken(accessToken.getValue())
                                                .build()
                                )
                                .build();
                    }
                    return LoginResponse.builder()
                            .status("400")
                            .message("This account has been banned")
                            .account(
                                    LoginResponse.AccountResponse.builder()
                                            .status("")
                                            .name("")
                                            .email("")
                                            .role("")
                                            .avatar("")
                                            .background("")
                                            .accessToken("")
                                            .build()
                            )
                            .build();
                }
                return LoginResponse.builder()
                        .status("400")
                        .message("Username or password is incorrect")
                        .account(
                                LoginResponse.AccountResponse.builder()
                                        .status("")
                                        .name("")
                                        .email("")
                                        .role("")
                                        .avatar("")
                                        .background("")
                                        .accessToken("")
                                        .build()
                        )
                        .build();
            }
            return LoginResponse.builder()
                    .status("400")
                    .message("Email is wrong formatted")
                    .account(
                            LoginResponse.AccountResponse.builder()
                                    .status("")
                                    .name("")
                                    .email("")
                                    .role("")
                                    .avatar("")
                                    .background("")
                                    .accessToken("")
                                    .build()
                    )
                    .build();
        }
        return LoginResponse.builder()
                .status("400")
                .message("Email or password is empty")
                .account(
                        LoginResponse.AccountResponse.builder()
                                .status("")
                                .name("")
                                .email("")
                                .role("")
                                .avatar("")
                                .accessToken("")
                                .build()
                )
                .build();
    }

    private boolean checkIfStringIsValid(String value){
        return value != null && !value.trim().isEmpty();
    }

    private boolean checkIfMailIsValid(String mail) {
        String regex1 = "^[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z0-9]+\\.[a-z]+\\.[a-z]+$";
        String regex2 = "^[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z0-9]+\\.[a-z]+$";
        return Pattern.compile(regex1).matcher(mail).matches() || Pattern.compile(regex2).matcher(mail).matches();
    }

    @Override
    public CreateAccountResponse Register(CreateAccountRequest request) {
        Optional<Account> optionalAccount = accountRepo.findByEmail(request.getEmail());
        if (optionalAccount.isPresent()) {
            return CreateAccountResponse.builder()
                    .status("409")
                    .message("Email already exists")
                    .build();
        }

        if(!checkIfMailIsValid(request.getEmail())){
            return  CreateAccountResponse.builder()
                    .status("400")
                    .message("Email not vaild")
                    .build();
        }

        AccountStatus accountStatus = accountStatusRepo.findById(1).orElse(null);

        if (accountStatus == null) {
            return CreateAccountResponse.builder()
                    .status("404")
                    .message("Default account status not found")
                    .build();
        }

        Account newAccount = Account.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .avatarLink("https://via.placeholder.com/150")
                .role(Role.USER)
                .accountStatus(accountStatus)
                .build();

        accountRepo.save(newAccount);

        return CreateAccountResponse.builder()
                .status("200")
                .message("Account created successfully")
                .build();
    }
}
