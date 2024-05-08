package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.services.JWTService;
import com.petopia.petopia.services.TokenService;
import com.petopia.petopia.services.TokenStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenStatusService tokenStatusService;

    private final JWTService jwtService;

    @Override
    public Token createNewAccessToken(Account account) {
        Token newAccessToken =  Token.builder()
                .account(account)
                .tokenStatus(null)
                .value(jwtService.generateAccessToken(account))
                .type(Const.TOKEN_TYPE_ACCESS)
                .build();

        return tokenStatusService.applyActiveStatus(newAccessToken);
    }

    @Override
    public Token createNewRefreshToken(Account account) {
        Token newRefreshToken =  Token.builder()
                .account(account)
                .tokenStatus(null)
                .value(jwtService.generateRefreshToken(account))
                .type(Const.TOKEN_TYPE_REFRESH)
                .build();

        return tokenStatusService.applyActiveStatus(newRefreshToken);
    }
}
