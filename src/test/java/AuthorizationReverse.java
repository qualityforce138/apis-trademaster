import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AuthorizationReverse extends Base {
    String token;
    // String BASE_URL = configuracao.getBaseUrl();
    String ct = "application/json";
    String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/authorization/reverse";

    public AuthorizationReverse() throws FileNotFoundException {
    }

    public static String readJsonFileContent(String arquivoJson) throws IOException {
        return new String (Files.readAllBytes (Paths.get (arquivoJson)));
    }

    @Test(priority = 1, description = "Teste para verificar se o token está sendo gerado corretamente.")
    public void test_Token() {
        System.out.println (getToken ());
    }

    @Test(priority = 2, description = "Teste para verificar se o retorno é o 400 com as informações passadas.")
    public void test_PostAuthorizationReverseLimity_ShouldReturn400() throws IOException {
        token = getToken ();
        String criarAutorizacaoReverse = readJsonFileContent ("src/test/resources/criarAuthorizationReverseLimity.json");
        given ()
                .contentType (ct)
                .header ("Authorization", "Bearer " + token)
                .log ().all ()
                .body (criarAutorizacaoReverse)
                .when ()
                .post (ENDPOINT)
                .then ()
                .log ().all ()
                .statusCode (400) //como não tinhamos um formato para upar, validamos resposta do postman
                .body ("description", is ("Bad Request"))
                .body("errors[0].message", is ("Invalid field format: Body - Invalid type. Expected: array, given: object"))
                .body ("serviceName", is ("tm-int-ms-cancel-authorize-v2"))
                .extract ();
    }
}
