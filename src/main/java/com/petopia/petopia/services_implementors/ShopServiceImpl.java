package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.*;
import com.petopia.petopia.models.request_models.CreateProductRequest;
import com.petopia.petopia.models.response_models.CreateProductResponse;
import com.petopia.petopia.repositories.*;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final AccountService accountService;
    private final ProductCategoryRepo productCategoryRepo;
    private final ProductRepo productRepo;
    private final ProductAttributeRepo productAttributeRepo;
    private final AttributeValueRepo attributeValueRepo;
    private final AttributeComboRepo attributeComboRepo;



    @Override
    public CreateProductResponse createProduct(CreateProductRequest request) {
        List<Feedback> feedbackList = new ArrayList<>();

        Account currentAccount = accountService.getCurrentLoggedAccount();
        assert currentAccount != null;
        if (currentAccount.getShop() == null) {
            return CreateProductResponse.builder()
                    .status("400")
                    .message("Tài khoản này không phải là chủ cửa hàng")
                    .build();
        }

        ProductCategory productCategory = productCategoryRepo.findById(request.getCategoryId()).orElse(null);
        if (productCategory == null) {
            return CreateProductResponse.builder()
                    .status("400")
                    .message("Không tìm thấy danh mục sản phẩm này")
                    .build();
        }

        Product product = Product.builder()
                .shop(currentAccount.getShop())
                .productCategory(productCategory)
                .name(request.getName())
                .soldQty(0)
                .rating(0)
                .feedbackList(feedbackList)
                .build();

        Product savedProduct = productRepo.save(product);
        //add product image
        List<ProductImage> productImages = addProductImages(request.getImages(), product);
        product.setProductImageList(productImages);
        //add attribute
        List<ProductAttribute> listAtt = addAttributes(request, product);
        product.setProductAttributeList(listAtt);
        //add attribute combo
        List<AttributeCombo> listAttCombos = addAttributeCombos(request, product, listAtt);
        product.setAttributeComboList(listAttCombos);

        return CreateProductResponse.builder()
                .status("200")
                .message("Tạo sản phẩm thành công")
                .id(savedProduct.getId())
                .category(savedProduct.getProductCategory().getName())
                .name(savedProduct.getName())
                .images(getProductImage(savedProduct))
                .shop(currentAccount.getShop().getName())
                .productStatus(Const.PRODUCT_STATUS_AVAILABLE)
                .attributeCombos(getAttributeCombos(listAttCombos))
                .build();
    }

    private List<CreateProductResponse.AttributeCombo> getAttributeCombos(List<AttributeCombo> attributeCombos) {
        return attributeCombos.stream()
                .map(combo -> CreateProductResponse.AttributeCombo.builder()
                        .mainAttributeValue(combo.getMAVN())
                        .subAttributeValue(combo.getSAVN())
                        .price(combo.getPrice())
                        .stockQuantity(combo.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    private List<CreateProductResponse.Images> getProductImage(Product product){
        return product.getProductImageList().stream()
                .map(image -> CreateProductResponse.Images.builder().link(image.getLink()).build())
                .collect(Collectors.toList());
    }

    private List<ProductImage> addProductImages(List<String> imageLinks, Product product) {
        List<ProductImage> productImages = new ArrayList<>();
        for (String link : imageLinks) {
            ProductImage productImage = ProductImage.builder()
                    .link(link)
                    .product(product)
                    .build();
            productImages.add(productImage);
        }
        return productImages;
    }

    private List<ProductAttribute> addAttributes(CreateProductRequest request, Product product){
        List<ProductAttribute> productAttributes = new ArrayList<>();
        addAttribute(request.getMainAttributeName(), request.getMainAttributeValues(), product, productAttributes);
        if(request.getSubAttributeName() != null && !request.getSubAttributeName().isEmpty()){
            addAttribute(request.getSubAttributeName(), request.getSubAttributeValues(), product, productAttributes);
        }
        return productAttributes;
    }

    private void addAttribute(String attributeName, List<CreateProductRequest.Value> values, Product product, List<ProductAttribute> productAttributes) {
        ProductAttribute attribute = ProductAttribute.builder()
                .name(attributeName)
                .product(product)
                .build();
        productAttributeRepo.save(attribute);
        List<AttributeValue> attributeValues = new ArrayList<>();
        for (CreateProductRequest.Value valueDTO : values) {
            AttributeValue attributeValue = AttributeValue.builder()
                    .value(valueDTO.getValueName())
                    .productAttribute(attribute)
                    .build();
            attributeValueRepo.save(attributeValue);
            attributeValues.add(attributeValue);
        }
        attribute.setAttributeValueList(attributeValues);
        productAttributes.add(attribute);
    }

    private List<AttributeCombo> addAttributeCombos(CreateProductRequest request, Product product, List<ProductAttribute> productAttributes) {
        List<AttributeCombo> savedAttributeCombos = new ArrayList<>();
        if (productAttributes.size() >= 2) {
            ProductAttribute mainAttribute = productAttributes.get(0);
            ProductAttribute subAttribute = productAttributes.get(1);

            Map<String, AttributeValue> mainAttributeValueMap = mainAttribute.getAttributeValueList().stream()
                    .collect(Collectors.toMap(AttributeValue::getValue, Function.identity()));
            Map<String, AttributeValue> subAttributeValueMap = subAttribute.getAttributeValueList().stream()
                    .collect(Collectors.toMap(AttributeValue::getValue, Function.identity()));

            for (CreateProductRequest.AttributeCombo comboRequest : request.getAttributeCombos()) {
                AttributeValue mainValue = mainAttributeValueMap.get(comboRequest.getMainAttributeValue());
                AttributeValue subValue = subAttributeValueMap.get(comboRequest.getSubAttributeValue());

                if (mainValue != null && subValue != null) {
                    AttributeCombo combo = AttributeCombo.builder()
                            .product(product)
                            .MAVId(mainValue.getId())
                            .MAN(mainAttribute.getName())
                            .MAVN(mainValue.getValue())
                            .SAVId(subValue.getId())
                            .SAN(subAttribute.getName())
                            .SAVN(subValue.getValue())
                            .quantity(comboRequest.getStockQuantity())
                            .price(comboRequest.getPrice())
                            .build();
                    savedAttributeCombos.add(attributeComboRepo.save(combo));
                }
            }
        } else if (productAttributes.size() == 1) {
            ProductAttribute mainAttribute = productAttributes.get(0);
            Map<String, AttributeValue> mainAttributeValueMap = mainAttribute.getAttributeValueList().stream()
                    .collect(Collectors.toMap(AttributeValue::getValue, Function.identity()));

            for (CreateProductRequest.AttributeCombo comboRequest : request.getAttributeCombos()) {
                AttributeValue mainValue = mainAttributeValueMap.get(comboRequest.getMainAttributeValue());

                if (mainValue != null) {
                    AttributeCombo combo = AttributeCombo.builder()
                            .product(product)
                            .MAVId(mainValue.getId())
                            .MAN(mainAttribute.getName())
                            .MAVN(mainValue.getValue())
                            .SAVId(null)
                            .SAN(null)
                            .SAVN(null)
                            .quantity(comboRequest.getStockQuantity())
                            .price(comboRequest.getPrice())
                            .build();
                    savedAttributeCombos.add(attributeComboRepo.save(combo));
                }
            }
        }

        return savedAttributeCombos;
    }
}
