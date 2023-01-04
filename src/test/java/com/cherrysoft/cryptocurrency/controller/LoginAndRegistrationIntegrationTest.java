package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.AbstractIntegrationTest;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.util.TestUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.FieldDescriptor;

import static com.cherrysoft.cryptocurrency.support.SharedFieldDescriptors.ERROR_FIELD_DESCRIPTOR;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginAndRegistrationIntegrationTest extends AbstractIntegrationTest {
  @Autowired private TestUtils testUtils;
  private CryptoUser cryptoUser;

  FieldDescriptor[] login = new FieldDescriptor[]{
      fieldWithPath("username").description("The user's username"),
      fieldWithPath("accessToken").description("The token for accessing the API endpoints")
  };

  @BeforeAll
  void init() {
    cryptoUser = testUtils.createCryptoUser();
  }

  @Test
  @Order(1)
  void givenValidUser_thenRegistrationIsSuccessfully() {
    given(requestSpecification)
        .filter(document("register", responseFields(login)))
        .with()
        .body(cryptoUser)
        .when()
        .post("/register")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("username", is(cryptoUser.getUsername()))
        .body("accessToken", is(notNullValue()))
        .body(not(hasProperty("password")));
  }

  @Test
  @Order(2)
  void givenAnAlreadyRegisterUsername_whenAttemptingRegister_thenReturns400StatusCodeWithError() {
    given(requestSpecification)
        .filter(document("error", responseFields(ERROR_FIELD_DESCRIPTOR)))
        .with()
        .body(cryptoUser)
        .when()
        .post("/register")
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("message", is(String.format("Username %s is already taken", cryptoUser.getUsername())));
  }

  @Test
  @Order(3)
  void givenAnExistingUser_thenLoginIsSuccessfully() {
    given(requestSpecification)
        .filter(document("login", responseFields(login)))
        .with()
        .body(cryptoUser)
        .when()
        .post("/login")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("username", is(cryptoUser.getUsername()))
        .body("accessToken", is(notNullValue()));
  }

  @Test
  @Order(4)
  void givenNonExistingUser_thenReturns400StatusCode_andAnError() {
    CryptoUser cryptoUser = testUtils.createCryptoUser();
    cryptoUser.setUsername("billowybead");

    given(requestSpecification)
        .filter(document("error", responseFields(ERROR_FIELD_DESCRIPTOR)))
        .with()
        .body(cryptoUser)
        .when()
        .post("/login")
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("message", is("Incorrect login or password entered. Try again"));
  }

}
