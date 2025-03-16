package com.example.cart_service.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User
 */
@Getter()
@AllArgsConstructor()
public class User {
  private String username;
  private String email;
  private String password;
}
