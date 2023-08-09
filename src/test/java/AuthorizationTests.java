import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AuthorizationTests extends Base {
    String token;
   // String BASE_URL = configuracao.getBaseUrl();
    String ct = "application/json";

    public AuthorizationTests() throws FileNotFoundException {
    }

    public static String readJsonFileContent(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test(priority = 1, description = "Teste para verificar se o token está sendo gerado corretamente.")
   public void test_Token() {
        System.out.println(getToken());
   }

    @Test(priority = 2, description = "Teste para verificar se o retorno é o 400 com as informações passadas.")
    public void test_PostAuthorization_ShouldReturn400() throws IOException {
        token = getToken();
        String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/authorization";
        String postAuthorization = readJsonFileContent("src/test/resources/postAuthorization.json");

        given()
                .contentType(ct)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .body(postAuthorization)
                .when()
                .post(ENDPOINT)
                .then()
                .log().all()
                .statusCode(400)
                .body("description", is("Bad Request"))
                .body("errors[0].message", is ("Customer not found"))
                .body("serviceName", is ("tm-int-ms-create-authorize-v2"))
                .extract();
    }
}