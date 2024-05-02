package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.models.entity_models.TokenStatus;
import com.petopia.petopia.repositories.TokenRepo;
import com.petopia.petopia.repositories.TokenStatusRepo;
import com.petopia.petopia.services.TokenStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenStatusServiceImpl implements TokenStatusService {

    private final TokenStatusRepo tokenStatusRepo;

    private final TokenRepo tokenRepo;

    @Override
    public Token applyActiveStatus(Token token) {
        TokenStatus active = tokenStatusRepo.findByStatus(Const.TOKEN_STATUS_ACTIVE).orElse(null);
        assert active != null;
        token.setTokenStatus(active);
        return tokenRepo.save(token);
    }

    @Override
    public Token applyExpiredStatus(Token token) {
        TokenStatus expired = tokenStatusRepo.findByStatus(Const.TOKEN_STATUS_EXPIRED).orElse(null);
        assert expired != null;
        token.setTokenStatus(expired);
        return tokenRepo.save(token);
    }
}
