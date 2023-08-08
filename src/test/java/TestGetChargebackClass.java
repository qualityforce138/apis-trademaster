import helpers.TestParametersChargeback;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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
        //String unauthorized = readJsonFileContent("src/test/resources/unauthorized.json");
        // Executa
        given()
                .log().all()
                .contentType(ct)
                .header("Authorization", "Bearer" + token) // Token enviado incorretamente de proposito
                //.body(unauthorized)
                .when()
                //.baseUri()
                .get(endpoint)
                // Valida
                .then()
                .statusCode(401)
                .log().all()
                //.body("message", is("Unauthorized"))
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
                .header("Authorization", "Bearer " + token)
                .body(badRequest)
        .when()
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
        // Executa
        given()
                .log().all()
                .contentType(ct)
                .header("Authorization", "Bearer " + token)
                //.queryParams("pageSize", TestParametersChargeback.PARTIAL_RETURN_PAGE_SIZE)
                //.queryParams("pageNumber", TestParametersChargeback.PARTIAL_RETURN_PAGE_NUMBER)
                //.queryParams("exceptionType", TestParametersChargeback.PARTIAL_RETURN_EXCEPTION_TYPE)
                //.queryParams("productName", TestParametersChargeback.PARTIAL_RETURN_PRODUCT_NAME)
                //.queryParams("sellerDocument",TestParametersChargeback.PARTIAL_RETURN_SELLER_DOCUMENT)
                //.queryParams("buyerDocument", TestParametersChargeback.PARTIAL_RETURN_BUYER_DOCUMENT)
                //.queryParams("beginDate", TestParametersChargeback.PARTIAL_RETURN_BEGIN_DATE)
                //.queryParams("endDate", TestParametersChargeback.PARTIAL_RETURN_END_DATE)

        .when()

                .get(endpoint)
                // Valida
        .then()
                .statusCode(200)
                .log().all()
                //.body("pageSize", equalTo("10"))
                //.body("pageNumber", equalTo(1))
                //.body("data[0].exceptionCode", equalTo("15712"))
                //.body("data[0].productName", equalTo("TRADE MAX"))
                //.body("data[0].sellerDocument", equalTo("48668819000158"))
                //.body("data[0].buyerDocument", equalTo("60311954000147"))

//                .body("data[0].amount", equalTo(9000))
//                .body("data[0].billetNumber", equalTo(31786))
//                .body("data[0].buyerDocument", equalTo("22767849000128"))
//                .body("data[0].dateCreated", equalTo("2023-08-04T00:13:29.412Z"))
//                .body("data[0].discount", equalTo(4000))
//                .body("data[0].dueDate", equalTo("2023-07-30"))
//                .body("data[0].exceptionCode", equalTo("71775"))
//                .body("data[0].exceptionType", equalTo("CB_WRITE_OFF"))
//                .body("data[0].nNfe", equalTo("5121250"))
//                .body("data[0].newDueDate", equalTo("2023-10-14"))
//                .body("data[0].operationCode", equalTo("42771"))
//                .body("data[0].operationDateCreated", equalTo("2023-07-19T17:20:06.470Z"))
//                .body("data[0].productName", equalTo("TRADE MAX"))
//                .body("data[0].sellerDocument", equalTo("65072595000136"))
//                .body("data[0].titleAmount", equalTo(3000))
                ;
        }


    }



