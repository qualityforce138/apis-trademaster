import helpers.TestParametersChargeback;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Test
public class TestGetChargebackClass extends Base {
    // Atributos
    String token;
    String baseUrl = configuracao.getBaseUrl();
    String ct = "application/json";
    String endpoint = "https://apigateway.hml.trademaster.com.br/v2/agreement/chargeback?pageSize=10&pageNumber=1&beginDate=2022-04-14&endDate=2022-04-14";


    public TestGetChargebackClass() throws IOException {
    }

    @Test(priority = 1)
    public void TesteDeToken() {
        System.out.println(getToken());
    }


    public static String readJsonFileContent(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test(priority = 2) // 401 "Unauthorized"
    public void testGetChargebackUnauthorized() throws IOException {
        // Configura
        token = getToken();
        String unauthorized = readJsonFileContent("src/test/resources/unauthorized.json");
        // Executa
        given()
                .log().all()
                .contentType(ct)
                .header("Authorization", "Bearer" + token)
                .body(unauthorized)
                .when()
                //.baseUri()
                .get(endpoint)
                // Valida
                .then()
                .statusCode(401)
                .log().all()
        ;


    }

    @Test(priority = 3) // 400 "Bad Request"
    public void testGetChargebackBadRequest() throws IOException {
        // Configura
        token = getToken();
        String badRequest = readJsonFileContent("src/test/resources/badRequest.json");
        // Executa
        given()
                .log().all()
                .contentType(ct)
                .header("Authorization", "Bearer" + token)
                .body(badRequest)
        .when()
                //.baseUri()
                .get(endpoint)
                // Valida
        .then()
                //.statusCode(400)
                .log().all()
                .extract()
        ;


    }

    @Test(priority = 4) // 200 "SucessComunication"
    public void testGetChargebackSuccess() throws IOException {
        // Configura
        token = getToken();
        String sucessComunication = readJsonFileContent("src/test/resources/successComunication.json");
        // Executa
        given()
                .log().all()
                .contentType(ct)
                .header("Authorization", "Bearer" + token)
                .queryParams("pageSize", TestParametersChargeback.PARTIAL_RETURN_PAGE_SIZE)
                .queryParams("pageNumber", TestParametersChargeback.PARTIAL_RETURN_PAGE_NUMBER)
                .queryParams("exceptionType", TestParametersChargeback.PARTIAL_RETURN_EXCEPTION_TYPE)
                .queryParams("productName", TestParametersChargeback.PARTIAL_RETURN_PRODUCT_NAME)
                .queryParams("sellerDocument",TestParametersChargeback.PARTIAL_RETURN_SELLER_DOCUMENT)
                .queryParams("buyerDocument", TestParametersChargeback.PARTIAL_RETURN_BUYER_DOCUMENT)
                .queryParams("beginDate", TestParametersChargeback.PARTIAL_RETURN_BEGIN_DATE)
                .queryParams("endDate", TestParametersChargeback.PARTIAL_RETURN_END_DATE)
                .body(sucessComunication)
        .when()
                //.baseUri()
                .get(endpoint)
                // Valida
        .then()
                //.statusCode(200)
                .log().all()
                .body("pageSize", equalTo("10"))
                .body("pageNumber", equalTo(1))
                .body("data[0].exceptionCode", equalTo("15712"))
                .body("data[0].productName", equalTo("TRADE MAX"))
                .body("data[0].sellerDocument", equalTo("48668819000158"))
                .body("data[0].buyerDocument", equalTo("60311954000147"))
        ;

    }
}


