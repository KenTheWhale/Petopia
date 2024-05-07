package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.response_models.LogoutResponse;

public interface AccountService {

    LogoutResponse logout();
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
}
