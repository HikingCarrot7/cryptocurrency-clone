package com.cherrysoft.cryptocurrency.util;

import com.cherrysoft.cryptocurrency.exception.ApplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToStringUtils {
  private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

  private static final DefaultPrettyPrinter PRETTY_PRINTER = new DefaultPrettyPrinter()
      .withObjectIndenter(new DefaultIndenter().withIndent("  "))
      .withArrayIndenter(new DefaultIndenter().withIndent("  "));

  public static String toJsonString(final Object object) {
    return toJsonString(object, JsonStyle.DEFAULT);
  }

  public static String toJsonString(final Object object, final JsonStyle jsonStyle) {
    try {
      if (jsonStyle == JsonStyle.PRETTY) {
        return MAPPER.writer(PRETTY_PRINTER).writeValueAsString(object);
      } else {
        return MAPPER.writeValueAsString(object);
      }
    } catch (JsonProcessingException e) {
      throw new ApplicationException(e);
    }
  }

}
