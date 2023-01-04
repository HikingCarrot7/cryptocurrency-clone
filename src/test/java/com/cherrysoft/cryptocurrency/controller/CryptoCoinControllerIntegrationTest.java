package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.AbstractIntegrationTest;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.service.CryptoUserService;
import com.cherrysoft.cryptocurrency.util.TestUtils;
import com.cherrysoft.cryptocurrency.web.controller.CryptoCoinController;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CryptoCoinControllerIntegrationTest extends AbstractIntegrationTest {
  @Autowired private TestUtils testUtils;
  @Autowired private CryptoUserService cryptoUserService;
  private CryptoUser cryptoUser;
  private String accessToken;

  @BeforeAll
  void init() {
    cryptoUser = testUtils.createCryptoUser();
    String password = cryptoUser.getPassword();
    cryptoUserService.createCryptoUser(cryptoUser);
    cryptoUser.setPassword(password);
  }

  void doLogin() {
    Map<String, String> body = Map.of(
        "username", cryptoUser.getUsername(),
        "password", cryptoUser.getPassword()
    );

    accessToken = given(requestSpecification)
        .body(body)
        .when()
        .post("/login")
        .jsonPath()
        .get("accessToken");
  }

  @Test
  void givenAValidCoin_thenCreatesCoin_andReturns201StatusCode_andIdIsNotNull() {
    doLogin();

    given(requestSpecification)
        .filter(document("coins", relaxedLinks(
                halLinks(),
                linkWithRel("owner").description("The <<resources_user, owner>> of this currency")
            ),
            relaxedResponseFields(
                fieldWithPath("id").description("The ID of the currency"),
                fieldWithPath("name").description("The name of the currency"),
                fieldWithPath("imageUrl").description("The URL of the image that represents this currency"),
                fieldWithPath("currentPrice").description("The current price of the currency"),
                fieldWithPath("marketCapital").description("The market capital of the currency"),
                fieldWithPath("totalVolume").description("The total volume of the currency"),
                fieldWithPath("marketCapitalRanking").description("The market capital ranking of the currency"),
                fieldWithPath("higherIn24Hours").description("How high will the currency be in the next 24 hours"),
                fieldWithPath("lowerIn24Hours").description("How low will the currency be in the next 24 hours")
            )))
        .header(authHeader())
        .accept(MediaTypes.HAL_JSON_VALUE)
        .with()
        .body(testUtils.createCryptoCoin())
        .when()
        .post(CryptoCoinController.BASE_URL)
        .then()
        .contentType(MediaTypes.HAL_JSON_VALUE)
        .statusCode(201)
        .header("Location", is(notNullValue()))
        .body("id", is(notNullValue()))
        .body("_links.owner.href", is(notNullValue()));
  }

  @Test
  void givenCoinWithInvalidName_thenReturns400StatusCode_andAValidationErrorOnNameAttrib() {
    CryptoCoin cryptoCoin = testUtils.createCryptoCoin();
    cryptoCoin.setName(null);

    doLogin();

    given(requestSpecification)
        .filter(document("validation_errors", relaxedResponseFields(
            fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
            fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"),
            fieldWithPath("validationErrors").description("Request validation errors")
        )))
        .header(authHeader())
        .with()
        .body(cryptoCoin)
        .when()
        .post(CryptoCoinController.BASE_URL)
        .then()
        .contentType(ContentType.JSON)
        .statusCode(400)
        .body("validationErrors", hasKey("name"));
  }

  private Header authHeader() {
    return new Header("Authorization", String.format("Bearer %s", accessToken));
  }

}
