package com.cherrysoft.cryptocurrency.support;

import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class SharedFieldDescriptors {

  public static FieldDescriptor[] ERROR_FIELD_DESCRIPTOR = new FieldDescriptor[]{
      fieldWithPath("message").description("A description of the cause of the error"),
      fieldWithPath("exception").description("The exception thrown"),
      fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
      fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred")
  };

}
