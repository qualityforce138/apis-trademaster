import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class TestOperationExceptionExtension extends Base {
    String token;
    String baseUrl = configuracao.getBaseUrl();
    String ct = "application/json";
    String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/operationException/extension";

    public TestOperationExceptionExtension() throws FileNotFoundException {
    }

    //    public TestOperationExceptionExtension(AuthConfig configuracao) throws FileNotFoundException {
//        this.configuracao = configuracao;
//    }
    public static String readJsonFileContent(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test(priority = 1, description = "Teste para verificar se o token está sendo gerado corretamente.")
    public void TesteDeToken() {
        System.out.println(getToken());
    }

    @Test
    public void test_PostOperationExceptionExtension() throws IOException {
        String token = getToken();
        String postOperationExceptionExtension = readJsonFileContent("src/test/resources/postOperationExceptionExtension.json");

        given()
                .contentType(ct)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .body(postOperationExceptionExtension)
            .when()
                .post(ENDPOINT)
            .then()
                .log().all()
                .statusCode(400)
                .body("description", is("Bad Request"))  //este numero seria a confirmação do post, mas como foi utilizado nao teria mais como utilizar ... entao, dara bad request...47306"))
                .body("errors[0].message", is("Billet not found"))
                .body("serviceName", is("tm-int-ms-update-prorrogation-operation-v2"))
                .extract();
    }

}