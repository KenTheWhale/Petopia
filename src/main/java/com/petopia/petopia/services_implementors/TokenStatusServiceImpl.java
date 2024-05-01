package com.petopia.petopia.services_implementors;

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
    public void applyActiveStatus(Token token) {
        if(token.getTokenStatus().getStatus().equals("expired")){
            TokenStatus active = tokenStatusRepo.findByStatus("active").orElse(null);
            assert active != null;
            token.setTokenStatus(active);
            tokenRepo.save(token);
        }
    }

    @Override
    public void applyExpiredStatus(Token token) {
        if(token.getTokenStatus().getStatus().equals("active")){
            TokenStatus expired = tokenStatusRepo.findByStatus("expired").orElse(null);
            assert expired != null;
            token.setTokenStatus(expired);
            tokenRepo.save(token);
        }
    }
}
