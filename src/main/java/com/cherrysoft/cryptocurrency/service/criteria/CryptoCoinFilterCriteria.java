package com.cherrysoft.cryptocurrency.service.criteria;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Map;

@RequiredArgsConstructor
public class CryptoCoinFilterCriteria {
  private final Map<String, String> filterCriteria;
  private final Pageable pageable;

  public boolean filterByAllCoins() {
    return filterCriteria.containsKey("all");
  }

  public boolean filterByPublicCoins() {
    return filterCriteria.containsKey("public");
  }

  public boolean filterByOwnedCoins() {
    return filterCriteria.containsKey("owned");
  }

  public boolean filterByFavoriteCoins() {
    return filterCriteria.containsKey("favorite");
  }

  public Pageable getPageable() {
    return pageable;
  }

}
