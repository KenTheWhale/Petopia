package com.petopia.petopia.services;

import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.request_models.ResetPasswordRequest;
import com.petopia.petopia.models.request_models.SendOtpRequest;
import com.petopia.petopia.models.response_models.*;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    CreateAccountResponse Register(CreateAccountRequest createAccountRequest);

    SendOTPResponse sendOTP(SendOtpRequest request);

    ResetPasswordResponse resetPassword(ResetPasswordRequest request);

}
