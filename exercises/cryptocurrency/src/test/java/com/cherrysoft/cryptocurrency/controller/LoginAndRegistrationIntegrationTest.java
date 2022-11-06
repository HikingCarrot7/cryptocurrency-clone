package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.AbstractIntegrationTest;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.util.TestUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginAndRegistrationIntegrationTest extends AbstractIntegrationTest {
  private static CryptoUser cryptoUser;

  @BeforeAll
  static void init() {
    cryptoUser = TestUtils.Crypto.createCryptoUser();
  }

  @Test
  @Order(1)
  void givenValidUser_thenRegistrationIsSuccessfully() {
    given(requestSpecification)
        .with()
        .body(cryptoUser)
        .when()
        .post("/register")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("id", is(notNullValue()))
        .body("username", is(cryptoUser.getUsername()))
        .body(not(hasKey("password")));
  }

  @Test
  @Order(2)
  void givenAnAlreadyRegisterUsername_thenReturns400StatusCodeWithError() {
    given(requestSpecification)
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
    CryptoUser cryptoUser = TestUtils.Crypto.createCryptoUser();
    cryptoUser.setUsername("billowybead");

    given(requestSpecification)
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
