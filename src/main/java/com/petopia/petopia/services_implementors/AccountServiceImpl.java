package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Role;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.AccountStatus;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.AccountStatusRepo;
import com.petopia.petopia.models.response_models.RefreshResponse;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.TokenRepo;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.JWTService;
import com.petopia.petopia.services.TokenService;
import com.petopia.petopia.services.TokenStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    private final TokenRepo tokenRepo;

    private final TokenStatusService tokenStatusService;

    private final TokenService tokenService;

    private final JWTService jwtService;

    private final AccountStatusRepo accountStatusRepo;


    @Override
    public LogoutResponse logout() {
        Account currentAccount = getCurrentLoggedAccount();
        if(currentAccount == null){
            return LogoutResponse.builder().status("400").message("Invalid account, can not logout").build();
        }
        Token currentAccessToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
        Token currentRefreshToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);

        assert currentAccessToken != null;
        assert currentRefreshToken != null;

        tokenStatusService.applyExpiredStatus(currentAccessToken);
        tokenStatusService.applyExpiredStatus(currentRefreshToken);

        return LogoutResponse.builder().status("200").message("Logout successfully").build();
    }

    @Override
    public RefreshResponse refresh() {
        Account currentAccount = getCurrentLoggedAccount();
        if(currentAccount == null) return RefreshResponse.builder().status("400").message("Invalid account").accessToken("").build();

        Token currentAccessToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
        Token currentRefreshToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);

        if(currentRefreshToken != null && jwtService.checkTokenIsValid(currentRefreshToken.getValue())){
            tokenStatusService.applyExpiredStatus(currentAccessToken);
            tokenStatusService.applyExpiredStatus(currentRefreshToken);
            Token access = tokenService.createNewAccessToken(currentAccount);
            tokenService.createNewRefreshToken(currentAccount);
            return RefreshResponse.builder().status("200").message("Refresh successfully").accessToken(access.getValue()).build();
        }
        return RefreshResponse.builder().status("400").message("Refresh token is already expired").accessToken("").build();
    }

    @Override
    public Account getCurrentLoggedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepo.findByEmail(authentication.getName()).orElse(null);
    }

    @Override
    public String getAccessToken() {
        Account currentAccount = getCurrentLoggedAccount();
        if(currentAccount == null){
            return "";
        }
        Token accessToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
        if(accessToken != null){
            return accessToken.getValue();
        }
        return "";
    }

    @Override
    public String getRefreshToken() {
        Account currentAccount = getCurrentLoggedAccount();
        if(currentAccount == null){
            return "";
        }
        Token refreshToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);
        if(refreshToken != null){
            return refreshToken.getValue();
        }
        return "";
    }
    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        Optional<Account> optionalAccount = accountRepo.findByEmail(request.getEmail());
        if (optionalAccount.isPresent()) {
            return CreateAccountResponse.builder()
                    .status("409")
                    .message("Email already exists")
                    .build();
        }

        AccountStatus accountStatus = accountStatusRepo.findById(1).orElse(null);

        if (accountStatus == null) {
            return CreateAccountResponse.builder()
                    .status("404")
                    .message("Default account status not found")
                    .build();
        }

        // Tạo mới đối tượng Account
        Account newAccount = Account.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .avatarLink("https://via.placeholder.com/150")
                .role(Role.VET)
                .accountStatus(accountStatus)
                .build();

        accountRepo.save(newAccount);

        return CreateAccountResponse.builder()
                .status("200")
                .message("Account created successfully")
                .build();
    }
}
