package com.petopia.petopia.services_implementors;

import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Override
    public LogoutResponse logout() {
        return null;
    }
}
