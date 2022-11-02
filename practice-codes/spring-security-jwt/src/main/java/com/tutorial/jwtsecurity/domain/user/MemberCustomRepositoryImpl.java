package com.tutorial.jwtsecurity.domain.user;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tutorial.jwtsecurity.domain.user.QMember;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import static com.tutorial.jwtsecurity.domain.user.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Member> getMemberPage(PageRequest pageable, String email) {

    List<Member> results = jpaQueryFactory
        .selectFrom(member)
        .where(member.email.contains(email))
        .orderBy(
            orderByQuery(member,pageable.getSort())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(member.count())
        .from(member)
        .where(member.email.contains(email));
    return PageableExecutionUtils.getPage(results,pageable,countQuery::fetchOne);
  }

  private OrderSpecifier[] orderByQuery(QMember member, Sort sort) {

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
