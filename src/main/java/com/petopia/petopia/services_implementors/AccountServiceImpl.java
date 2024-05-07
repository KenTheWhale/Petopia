package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Role;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.AccountStatus;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.AccountStatusRepo;
import com.petopia.petopia.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountStatusRepo accountStatusRepo;


    @Override
    public LogoutResponse logout() {
        return null;
    }
    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        Optional<Account> optionalAccount = accountRepo.findByEmail(request.getEmail());
        if (optionalAccount.isPresent()) {
            return CreateAccountResponse.builder()
                    .status("409")
                    .message("Email already exists")
                    .build();
        }

        AccountStatus accountStatus = accountStatusRepo.findById(1).orElse(null);

        if (accountStatus == null) {
            return CreateAccountResponse.builder()
                    .status("404")
                    .message("Default account status not found")
                    .build();
        }

        // Tạo mới đối tượng Account
        Account newAccount = Account.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .avatarLink("https://via.placeholder.com/150")
                .role(Role.VET)
                .accountStatus(accountStatus)
                .build();

        accountRepo.save(newAccount);

        return CreateAccountResponse.builder()
                .status("200")
                .message("Account created successfully")
                .build();
    }
}
