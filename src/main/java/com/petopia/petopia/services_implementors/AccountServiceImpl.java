package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Role;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.AccountStatus;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.models.response_models.DeleteAccountResponse;
import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.AccountStatusRepo;
import com.petopia.petopia.models.response_models.RefreshResponse;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.TokenRepo;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.AuthenticationService;
import com.petopia.petopia.services.JWTService;
import com.petopia.petopia.services.TokenService;
import com.petopia.petopia.services.TokenStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            return LogoutResponse.builder().status("400").message("Tài khoản không hợp lệ, không thể đăng xuất").build();
        }
        Token currentAccessToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
        Token currentRefreshToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);

        assert currentAccessToken != null;
        assert currentRefreshToken != null;

        tokenStatusService.applyExpiredStatus(currentAccessToken);
        tokenStatusService.applyExpiredStatus(currentRefreshToken);

        return LogoutResponse.builder().status("200").message("Đăng xuất thành công").build();
    }

    @Override
    public RefreshResponse refresh() {
        Account currentAccount = getCurrentLoggedAccount();
        if(currentAccount == null) return RefreshResponse.builder().status("400").message("Tài khoản không hợp lệ").accessToken("").build();

        Token currentAccessToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
        Token currentRefreshToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(currentAccount.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);

        if(currentRefreshToken != null && jwtService.checkTokenIsValid(currentRefreshToken.getValue())){
            tokenStatusService.applyExpiredStatus(currentAccessToken);
            tokenStatusService.applyExpiredStatus(currentRefreshToken);
            Token access = tokenService.createNewAccessToken(currentAccount);
            tokenService.createNewRefreshToken(currentAccount);
            return RefreshResponse.builder().status("200").message("Làm mới thành công").accessToken(access.getValue()).build();
        }
        return RefreshResponse.builder().status("400").message("Refresh token hết hạn").accessToken("").build();
    }

    @Override
    public Account getCurrentLoggedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepo.findByEmail(authentication.getName()).orElse(null);
    }

    @Override
    public void deleteAccount() {
            Account account  = getCurrentLoggedAccount();
            AccountStatus deleteAccountStatus = accountStatusRepo.findByStatus(Const.ACCOUNT_STATUS_DELETED);

                account.setAccountStatus(deleteAccountStatus);
                accountRepo.save(account);
                logout();
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


}
