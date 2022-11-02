package com.tutorial.jwtsecurity.domain.user;

import com.tutorial.jwtsecurity.domain.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MemberCustomRepository {

  Page<Member> getMemberPage(PageRequest pageRequest, String email);
}
