package com.petopia.petopia.controllers;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.request_models.CreateProductRequest;
import com.petopia.petopia.models.response_models.CreateProductResponse;
import com.petopia.petopia.services.ShopService;
import com.petopia.petopia.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
@Tag(name = "Shop Owner")
public class ShopOwnerController {

    private final ShopService shopService;


    @PostMapping("/product/create")
    @PreAuthorize("hasAuthority('owner:create')")
    @Operation(description = Const.CREATOR_TRIEU)
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return shopService.createProduct(request);
    }

}
