package POJO;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;

public class authorizationReverseLimityExceptionTestsPost<tring> extends Base {
    String token;
    private Object configuracao;
    String baseUrl;
    String ct = "application/json";
    private int List;

    public void AuthorizationReverseLimityExceptionTestsPost() {
        String Token;
        baseUrl = Token;
    }

    public void AuthorizationReverseLimityExceptionTests() throws FileNotFoundException {
        final String s = "Internal Server Error";

    }

    public static String buscarArquivoJson(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test
    public void TesteDeToken() {
        boolean post = false;
        System.out.println();
    }

    private void Token() {
        return ;
        Object Token;
        final Object token1 = Token;
    }

    @Test
    public void autorizationPOST() throws IOException {

        String endpoint = "https://apigateway.hml.trademaster.com.br/v2/agreement/authorization/reverse";
        String criarAutorizacao = buscarArquivoJson("src/test/resources/criarAuthorizationReverseLimity.json");

        given()
                .contentType(ct)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .body(criarAutorizacao)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .statusCode(401)
                .body("description", is("Unauthorized"))
                .body("errors[0].message", is ("Bad Request"))
                .body("serviceName", is ("\"Internal Server Error\""))
                .body(stringContainsInOrder ("Authorization\" " ))
                .extract()
                .StatusCode = token (401);
        ;

    }

    private Object token(int i) {
        return null;
    }

    @Test
    public void autorizationUsandoUmouMaisVendedoresPOST() throws IOException {
        return String ;
        final String s = "Str = (String) = token";
        String endpoint = ("https://apigateway.hml.trademaster.com.br/v2/agreement/authorization/reverse");
        String criarAutorizacao = buscarArquivoJson("src/test/resources/criarAutorizacaoReverseLimity.json");

        given()
                .contentType(ct)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .body(criarAutorizacao)
        .when()
                .post(endpoint)
        .then()
                .log().all()
                .statusCode(401)
                .body("description", is("Unauthorized"))
                .body("errors[0].message", is ("Bad Request"))
                .body("serviceName", is ("\"Internal Server Error\""))
                .body(stringContainsInOrder ("Authorization\" " ))
                .extract()
                
        ;

    } // TODO: 04/08/2023 finalizar ajustes
}
