package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.response_models.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    CreateAccountResponse Register(CreateAccountRequest createAccountRequest);

}
