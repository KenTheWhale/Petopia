package com.petopia.petopia.services;

import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;

public interface AccountService {

    LogoutResponse logout();

    RefreshResponse refresh();

    Account getCurrentLoggedAccount();

    void deleteAccount();

    String getAccessToken();
    String getRefreshToken();
}
