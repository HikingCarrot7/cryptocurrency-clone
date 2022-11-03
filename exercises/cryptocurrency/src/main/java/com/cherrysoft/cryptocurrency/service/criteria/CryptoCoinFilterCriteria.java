package com.cherrysoft.cryptocurrency.service.criteria;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class CryptoCoinFilterCriteria {
  private final Map<String, String> filterCriteria;

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

  public int page() {
    String pageString = filterCriteria.getOrDefault("page", "0");
    return Integer.parseInt(pageString);
  }

  public int pageSize() {
    String pageSizeString = filterCriteria.getOrDefault("size", "10");
    return Integer.parseInt(pageSizeString);
  }

}
