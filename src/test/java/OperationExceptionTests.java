import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class OperationExceptionTests extends Base {
    String token;
    String baseUrl = configuracao.getBaseUrl();
    String ct = "application/json";

    public OperationExceptionTests() throws FileNotFoundException {
    }

    public static String buscarArquivoJson(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

   @Test
   public void TesteDeToken() {
        System.out.println(getToken());
   }

    @Test
    public void autorizationPOST() throws IOException {
        token = getToken();
        String endpoint = "https://apigateway.hml.trademaster.com.br/v2/agreement/authorization";
        String criarAutorizacao = buscarArquivoJson("src/test/resources/criarAutorizacao.json");

        given()
                .contentType(ct)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .body(criarAutorizacao)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .statusCode(400)
                .body("description", is("Bad Request"))
                .body("errors[0].message", is ("Customer not found"))
                .body("serviceName", is ("tm-int-ms-create-authorize-v2"))
                .extract();
    }
}