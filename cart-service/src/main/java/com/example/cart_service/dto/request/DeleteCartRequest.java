package com.example.cart_service.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class DeleteCartRequest {
    private String userId;
    private List<Integer> listProductId;
}
