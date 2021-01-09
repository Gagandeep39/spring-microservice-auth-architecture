/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2021-01-09 05:23:11
 * @modify date 2021-01-09 05:23:11
 * @desc [description]
 */
package com.gagan.authservice.services;

import com.gagan.authservice.dto.LoginRequest;
import com.gagan.authservice.dto.LoginResponse;

public interface AuthService {
  LoginResponse login(LoginRequest loginRequest);

}
