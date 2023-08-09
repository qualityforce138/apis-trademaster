import helpers.TestParametersPaymentReconciliation;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PaymentReconciliationTests extends Base {
    String token;
    String ct = "application/json";
    String ENDPOINT = "https://apigateway.hml.trademaster.com.br/v2/agreement/paymentReconciliation";

    public PaymentReconciliationTests() throws IOException {
    }


    //public static String readJsonFileContent(String arquivoJson) throws IOException {
    //    return new String(Files.readAllBytes(Paths.get(arquivoJson)));
   // }

    @Test(priority = 1, description = "Teste para verificar se o token está sendo gerado corretamente.")
    public void test_Token() {
        System.out.println(getToken());
    }

    @Test(priority = 2, description = "Teste para verificar retorno do GET da reconciliação")
    public void test_GetPaymentReconciliation() {
        token = getToken();
        given()
                .log().all()
                //.contentType(ct)
                .header("Authorization", "Bearer " + token)
                //.queryParams("pageSize", TestParametersPaymentReconciliation.PAYMENT_PAGE_SIZE)
                //.queryParams("pageNumber", TestParametersPaymentReconciliation.PAYMENT_PAGE_NUMBER)
                .queryParams("transferDate", TestParametersPaymentReconciliation.PAYMENT_TRANSFER_DATE)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .log().all()
                //.extract()

                .body("data[0].agency", equalTo("1299X"))
                .body("data[0].amount", equalTo(3000))
                .body("data[0].bank", equalTo(341))
                .body("data[0].bankAccount", equalTo("12345"))
                .body("data[0].nfeNumber", equalTo("5121247"))
                .body("data[0].operationCode", equalTo("42768"))
                .body("data[0].ticketNumber", equalTo("308830"))
        ;
    }
}
