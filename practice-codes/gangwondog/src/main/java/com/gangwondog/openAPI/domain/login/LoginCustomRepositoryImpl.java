package com.gangwondog.openAPI.domain.login;

import com.gangwondog.openAPI.domain.user.QUserEntityP;
import com.gangwondog.openAPI.domain.user.UserEntityP;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoginCustomRepositoryImpl implements LoginCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<UserEntityP> getMemberPage(PageRequest pageable, String email) {

    List<UserEntityP> results = jpaQueryFactory
        .selectFrom(QUserEntityP.userEntityP)
        .where(QUserEntityP.userEntityP.email.contains(email))
        .orderBy(
            orderByQuery(QUserEntityP.userEntityP,pageable.getSort())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(QUserEntityP.userEntityP.count())
        .from(QUserEntityP.userEntityP)
        .where(QUserEntityP.userEntityP.email.contains(email));
    return PageableExecutionUtils.getPage(results,pageable,countQuery::fetchOne);
  }

  private OrderSpecifier[] orderByQuery(QUserEntityP member, Sort sort) {

    List<OrderSpecifier> orders = new ArrayList<>();

    sort.get().forEach(order -> {
      if(order.getProperty().equals("member_id")) {
        if(order.isDescending()){
          orders.add(member.id.desc());
        }else{
          orders.add(member.id.asc());
        }
      }
      if(order.getProperty().equals("email")) {
        if(order.isDescending()){
          orders.add(member.email.desc());
        }else{
          orders.add(member.email.asc());
        }
      }
    });

    return orders.toArray(new OrderSpecifier[0]);
  }


}
