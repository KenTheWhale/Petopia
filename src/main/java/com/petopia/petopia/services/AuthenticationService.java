package com.petopia.petopia.services;

import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.response_models.LoginResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;

public interface AuthenticationService {
    String getAccessToken(Integer accountID);
    String getRefreshToken(Integer accountID);

    Account getCurrentLoggedUser();

    LoginResponse login(LoginRequest request);

    RefreshResponse refresh();
}
