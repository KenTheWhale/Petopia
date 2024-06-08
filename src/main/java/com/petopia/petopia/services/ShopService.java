package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateProductRequest;
import com.petopia.petopia.models.response_models.CreateProductResponse;

public interface ShopService {
    CreateProductResponse createProduct(CreateProductRequest request);
}
