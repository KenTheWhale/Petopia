package com.petopia.petopia.services;

import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.response_models.LoginResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    CreateAccountResponse Register(CreateAccountRequest createAccountRequest);

}
