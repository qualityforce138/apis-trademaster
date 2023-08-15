import helpers.TestParametersBankSlip;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestBankSlip extends Base{

    // Atributos
    String token;
    String ct = "application/json";
    String endpoint = "https://apigateway.hml.trademaster.com.br/v2/agreement/bankSlip/{operationCode}";

    public TestBankSlip() throws FileNotFoundException {
    }

    @Test(priority = 1)     //OK - Status Code 200
    public void testGetBankSlipSuccess() throws IOException {
        token = getToken();
        given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .pathParam("operationCode", TestParametersBankSlip.OPERATION_CODE)
        .when()
                .get(endpoint)
        .then()
                .statusCode(200)
                .log().all()
                .body("[0].billetNumber", equalTo("31884"))
                .body("[1].billetNumber", equalTo("31885"))
                .body("[0].amount", equalTo(2.34F))
                .body("[0].barcodeNumber", equalTo("99999.99999 99999.999999 99999.999999 9 99999999999999"))
                .body("[1].barcodeNumber", equalTo("23793.39001 90000.003096 91000.043504 9 94580000000234"))
                .body("[0].discount", equalTo(null))
                .body("[0].dueDate", equalTo("2023-09-30"))
                .body("[1].dueDate", equalTo("2023-08-30"))
                .body("[0].installmentNumber", equalTo(2))
                .body("[1].installmentNumber", equalTo(1))
                .body("[0].originalDueDate", equalTo("2023-09-30"))
                .body("[1].originalDueDate", equalTo("2023-08-30"))
                .body("[0].paidDate", equalTo(null))
                .body("[1].paidDate", equalTo(null))
                .body("[0].status", equalTo("REGISTERED"))
                .body("[1].status", equalTo("REGISTERED"))
                .body("[0].ticketNumber", equalTo("30990P"))
                .body("[1].ticketNumber", equalTo("309918"))
        ;
    }

}
