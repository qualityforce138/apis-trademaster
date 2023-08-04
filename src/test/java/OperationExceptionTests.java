import helpers.TestParametersOperationExceptions;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OperationExceptionTests extends Base {
    String token;
    String baseUrl = configuracao.getBaseUrl();
    String ct = "application/json";

    public OperationExceptionTests() throws FileNotFoundException {
    }

    public static String readJsonFileContent(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test(priority = 1, description = "Teste para verificar se o token está sendo gerado corretamente.")
    public void TesteDeToken() {
        System.out.println(getToken());
    }

    @Test(priority = 2, description = "Teste para confirmar os tipos disponiveis das Operações de Exceção.")
    public void test_GetOperationExceptionaALL() throws IOException {
        token = getToken();
        String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/operationException";

        given()
                .log().all()
                .header("Authorization", "Bearer " + token)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .log().all()

                .body("data[0].exceptionType", equalTo("PARTIAL_RETURN"))
                .body("data[0].exceptionCode", equalTo("42774"))
                .body("data[0].operationCode", equalTo("42770"))

                .body("data[1].exceptionType", equalTo("PRORROGATION"))
                .body("data[1].exceptionCode", equalTo("42775"))
                .body("data[1].operationCode", equalTo("42771"))
                ;
    }

    @Test(priority = 3)
    public void test_GetOperationExceptionPARTIAL_RETURN() throws IOException {
        token = getToken();
        String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/operationException";
        given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .queryParams("exceptionCode", TestParametersOperationExceptions.PARTIAL_RETURN_EXCEPTION_CODE)
                .queryParams("operationCode", TestParametersOperationExceptions.PARTIAL_RETURN_OPERATION_CODE)
                .queryParams("operationCode", TestParametersOperationExceptions.PARTIAL_RETURN_EXCEPTION_TYPE)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .log().all()
                .body("data[0].exceptionType", equalTo("PARTIAL_RETURN"))
                .body("data[0].exceptionCode", equalTo("42774"))
                .body("data[0].operationCode", equalTo("42770"))
                .body("data[0].amount", equalTo(3500))
                .body("data[0].createdAt", equalTo("2023-07-19T17:30:23.266Z"))
                .body("data[0].dueDate", equalTo("2023-08-14"))
                .body("data[0].newDueDate", equalTo("2023-08-14"))
                .body("data[0].reason", equalTo("SALE #42770 - PARTIAL RETURN APPLYED"))
                .body("data[0].returnAmount", equalTo(500))
                .body("data[0].ticketNumber", equalTo("31784"));
    }

    @Test(priority = 4)
    public void test_GetOperationExceptionPRORROGATION() throws IOException {
        token = getToken();
        String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/operationException";
        given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .queryParams("exceptionCode", TestParametersOperationExceptions.PRORROGATION_EXCEPTION_CODE)
                .queryParams("operationCode", TestParametersOperationExceptions.PRORROGATION_OPERATION_CODE)
                .queryParams("operationCode", TestParametersOperationExceptions.PRORROGATION_EXCEPTION_TYPE)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .log().all()
                .body("data[0].exceptionType", equalTo("PRORROGATION"))
                .body("data[0].exceptionCode", equalTo("42775"))
                .body("data[0].operationCode", equalTo("42771"))
                .body("data[0].amount", equalTo(9000))
                .body("data[0].createdAt", equalTo("2023-07-19T17:31:45.425Z"))
                .body("data[0].dueDate", equalTo("2023-07-30"))
                .body("data[0].newDueDate", equalTo("2023-10-14"))
                .body("data[0].reason", equalTo("SALE #42771 - INSTALLMENT #undefined EXTENDED TO 2023-10-14"))
                .body("data[0].returnAmount", equalTo(0))
                .body("data[0].ticketNumber", equalTo("31786"));
    }

    @Test(priority = 5)
    public void test_PostOperationExceptionTED() throws IOException {
        token = getToken();
        String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/operationException/ted";
        String postOperationExceptionTED = readJsonFileContent("src/test/resources/postOperationExceptionTED.json");

        given()
                .given()
                .contentType(ct)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .body(postOperationExceptionTED)
                .when()
                .post(ENDPOINT)
                .then()
                .log().all()
                .statusCode(400)
                .body("description", is("Bad Request"))  //este numero seria a confirmação do post, mas como foi utilizado nao teria mais como utilizar ... entao, dara bad request...47306"))
                .body("errors[0].message", is("Operation invalid for billet #31784"))
                .body("serviceName", is("tm-int-ms-create-manual-writeoff-operation-v2"))
                .extract();
    }
}