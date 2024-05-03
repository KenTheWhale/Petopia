package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.response_models.LoginResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;
import com.petopia.petopia.repositories.AccountRepo;
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

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenRepo tokenRepo;

    private final TokenService tokenService;

    private final TokenStatusService tokenStatusService;

    private final AccountRepo accountRepo;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;
    @Override
    public String getAccessToken(Integer accountID) {
        Token accessToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(accountID, Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
        if(accessToken != null){
            return accessToken.getValue();
        }
        return "";
    }

    @Override
    public String getRefreshToken(Integer accountID) {
        Token refreshToken = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(accountID, Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);
        if(refreshToken != null){
            return refreshToken.getValue();
        }
        return "";
    }

    @Override
    public Account getCurrentLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepo.findByEmail(authentication.getName()).orElse(null);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if(checkIfStringIsValid(request.getUsername()) && checkIfStringIsValid(request.getPassword())){
            Account account = accountRepo.findByEmail(request.getUsername()).orElse(null);
            if(account != null && passwordEncoder.matches(request.getPassword(), account.getPassword())){
                if(account.getAccountStatus().getStatus().equals(Const.ACCOUNT_STATUS_ACTIVE)){
                    Token oldAccess = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(account.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_ACCESS).orElse(null);
                    Token oldRefresh = tokenRepo.findByAccount_IdAndTokenStatus_StatusAndType(account.getId(), Const.TOKEN_STATUS_ACTIVE, Const.TOKEN_TYPE_REFRESH).orElse(null);

                    if(oldAccess != null) tokenStatusService.applyExpiredStatus(oldAccess);
                    if(oldRefresh != null) tokenStatusService.applyExpiredStatus(oldRefresh);

                    String accessToken = jwtService.generateAccessToken(account);
                    String refreshToken = jwtService.generateRefreshToken(account);

                    tokenService.createNewAccessToken(account, accessToken);
                    tokenService.createNewRefreshToken(account, refreshToken);

                    return LoginResponse.builder()
                            .status("200")
                            .message("Login successfully")
                            .accessToken(accessToken)
                            .build();
                }
                return LoginResponse.builder()
                        .status("400")
                        .message("This account has been banned")
                        .accessToken("")
                        .build();
            }

            return LoginResponse.builder()
                    .status("400")
                    .message("Username or password is incorrect")
                    .accessToken("")
                    .build();
        }
        return LoginResponse.builder()
                .status("400")
                .message("Username or password is empty")
                .accessToken("")
                .build();
    }

    @Override
    public RefreshResponse refresh() {
        return null;
    }

    private boolean checkIfStringIsValid(String value){
        return value != null && !value.trim().isEmpty();
    }
}
