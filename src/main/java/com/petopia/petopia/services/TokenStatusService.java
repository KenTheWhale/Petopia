package com.petopia.petopia.services;

import com.petopia.petopia.models.entity_models.Token;

public interface TokenStatusService {

    Token applyActiveStatus(Token token);

    Token applyExpiredStatus(Token token);
}
