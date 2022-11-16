package com.gangwondog.openAPI.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class CommonUtil {

  public static PageRequest pagable(Integer page, Integer size, String sort) {
    int pageNumber = 0, pageSize = 50;
    if(page != null && page > 0) {
      pageNumber = page;
    }
    if(size != null && size > 0) {
      pageSize = size;
    }

    List<Sort.Order> orders = new ArrayList<>();
    if(sort != null && !sort.isEmpty()) {
      for (String str: Arrays.asList(sort.split(","))) {
        if (str.toUpperCase(Locale.US).endsWith("DESC")){
          orders.add(new Sort.Order(Sort.Direction.DESC, str.split("\\.")[0]));
        }else{
          orders.add(new Sort.Order(Sort.Direction.ASC, str.split("\\.")[0]));
        }
      }
    }
    Sort pageSort = Sort.by(orders);

    return PageRequest.of(pageNumber -1, pageSize, pageSort);
  }
}
