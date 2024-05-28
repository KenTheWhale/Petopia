package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.response_models.DeleteAccountResponse;
import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;

public interface AccountService {

    LogoutResponse logout();

    RefreshResponse refresh();

    Account getCurrentLoggedAccount();

    DeleteAccountResponse deleteAccount(int id);

    String getAccessToken();
    String getRefreshToken();
}
