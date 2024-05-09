package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;

public interface AccountService {

    LogoutResponse logout();
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    RefreshResponse refresh();

    Account getCurrentLoggedAccount();

    String getAccessToken();
    String getRefreshToken();
}
