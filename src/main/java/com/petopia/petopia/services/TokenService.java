package com.petopia.petopia.services;

import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Token;

public interface TokenService {

    Token createNewAccessToken(Account account, String value);

    Token createNewRefreshToken(Account account, String value);
}
