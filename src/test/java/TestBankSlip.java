import helpers.TestParametersBankSlip;
import helpers.TestParametersOperationExceptions;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


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

           /* "amount": 2.34,
                "barcodeNumber": "99999.99999 99999.999999 99999.999999 9 99999999999999",
                "billetNumber": "31884",
                "discount": null,
                "dueDate": "2023-09-30",
                "installmentNumber": 2,
                "originalDueDate": "2023-09-30",
                "paidDate": null,
                "status": "REGISTERED",
                "ticketNumber": "30990P"*/
        ;
    }

    @Test(priority = 2)     //Bad Request - Status Code 400
    public void testGetBankSlipBadRequest() throws IOException{
        token = getToken();
        given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .pathParam("operationCode", TestParametersBankSlip.OPERATION_CODE_BAD_REQUEST)
        .when()
                .get(endpoint)
        .then()
                .statusCode(400)
                .log().all()
                //.body("[0].billetNumber", equalTo("31884"))
                //.body("[1].billetNumber", equalTo("31885"))
                //.body("[0].amount", equalTo(2.34F))
        ;
    }

    @Test(priority = 3)     //Unauthorized - Status Code 401
    public void testGetBankSlipUnauthorized(){
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
                .statusCode(401)
                .log().all()
        ;

    }

    @Test(priority = 4)     //Not Found - Status Code 404
    public void testGetBankSlipNotFound(){
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
                .statusCode(404)
                .log().all()
        ;
    }

    @Test(priority = 4)     //Internal Server Error - Status Code 500
    public void testGetBankSlipInternalServerError(){
        // Configura
        token = getToken();
        // Executa
        given()
                .log().all()
                .contentType(ct)
                .header("Authorization", "Bearer" + token)
        .when()
                .get(endpoint)
        // Valida
        .then()
                .statusCode(500)
                .log().all()
        ;
    }

}
