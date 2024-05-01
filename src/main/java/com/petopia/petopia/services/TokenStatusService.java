package com.petopia.petopia.services;

import com.petopia.petopia.models.entity_models.Token;

public interface TokenStatusService {

    void applyActiveStatus(Token token);

    void applyExpiredStatus(Token token);
}
