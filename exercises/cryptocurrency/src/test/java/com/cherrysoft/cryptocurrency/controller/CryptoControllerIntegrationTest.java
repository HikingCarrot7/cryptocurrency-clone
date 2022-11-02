package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.AbstractIntegrationTest;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.util.TestUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class CryptoControllerIntegrationTest extends AbstractIntegrationTest {

  @Test
  void givenAValidCoin_thenCreatesCoin_andReturns201StatusCode_andIdIsNotNull() {
    given(requestSpecification)
        .with()
        .body(TestUtils.Crypto.createCryptoCoin())
        .when()
        .post(CryptoCoinController.BASE_URL)
        .then()
        .statusCode(201)
        .contentType(ContentType.JSON)
        .body("id", is(notNullValue()));
  }

  @Test
  void givenCoinWithInvalidName_thenReturns400StatusCode_andAValidationErrorOnNameAttrib() {
    CryptoCoin cryptoCoin = TestUtils.Crypto.createCryptoCoin();
    cryptoCoin.setName(null);

    given(requestSpecification)
        .with()
        .body(cryptoCoin)
        .when()
        .post(CryptoCoinController.BASE_URL)
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("validationErrors", hasKey("name"));
  }

}
