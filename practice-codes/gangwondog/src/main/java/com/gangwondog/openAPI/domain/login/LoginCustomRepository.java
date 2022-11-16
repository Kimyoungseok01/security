package com.gangwondog.openAPI.domain.login;

import com.gangwondog.openAPI.domain.user.UserEntityP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface LoginCustomRepository {

  Page<UserEntityP> getMemberPage(PageRequest pageRequest, String email);
}
