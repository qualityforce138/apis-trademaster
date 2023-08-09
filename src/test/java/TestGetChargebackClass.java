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
        //String unauthorized = readJsonFileContent("src/test/resources/unauthorized.json");
        // Executa
        given()
                .log().all()
                .contentType(ct)
                // Token enviado incorretamente de proposito para verificar comportamento do acesso da API
                .header("Authorization", "Bearer " + token + "erro")
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
                .statusCode(200)
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

        .when()

                .get(endpoint)
                // Valida
        .then()
                .statusCode(200)
                .log().all()
                // Parametros que foram retirados da API
                .body("pageNumber", equalTo(1))
                .body("pageSize", equalTo(10))
                .body("totalCount", equalTo(0))
                .log().all()
                .extract()

                ;
        }


    }



