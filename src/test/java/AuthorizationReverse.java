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
    public void test_PostAuthorization_ShouldReturn400() throws IOException {
        token = getToken ();

        String endpoint = "https://apigateway.hml.trademaster.com.br/v2/agreement/authorization/reverse";
        String criarAutorizacao = readJsonFileContent ("src/test/resources/criarAuthorizationReverseLimity.json");

        given ()
                .contentType (ct)
                .header ("Authorization", "Bearer " + token)
                .log ().all ()
                .body (criarAutorizacao)
                .when ()
                .post (endpoint)
                .then ()
                .log ().all ()
                .statusCode (400)
                .body ("description", is ("Bad Request"))
                //.body("errors[0].message", is ("Authorization not found"))
                //.body ("errors.message", is ("Authorization not found"))
                .body ("serviceName", is ("tm-int-ms-cancel-authorize-v2"))
                .extract ()
        ;
    }
}
