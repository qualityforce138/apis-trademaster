import POJO.AuthConfig;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Base {

    // Atributos
    Gson gson = new Gson(); // leitor de Json
    // Ler o arquivo de configuração
    Reader reader = new FileReader("./src/test/resources/config.json");
    AuthConfig configuracao = gson.fromJson(reader, AuthConfig.class);

    // Construtor para tratar de problemas na leitura do arquivo de configuração
    public Base() throws FileNotFoundException {
    }

    // Funções de Teste

    public String getToken() {

        //Configura
        String endpoint = "/auth/realms/api-management/protocol/openid-connect/token";
        String baseURL = "https://apigateway.hml.trademaster.com.br";

        //Executa
        String token = given()
                .contentType(ContentType.URLENC)
                .formParam("client_id",configuracao.getClient_id() )
                .formParam("client_secret", configuracao.getClient_secret())
                .formParam("grant_type","client_credentials" )
                .formParam("scope","convenio-api/.default" )

                .baseUri(baseURL)
                .when()
                .post(endpoint)

                //Valida
                .then()
                .statusCode(200)
                .body("token_type", equalTo("Bearer"))
                .extract()
                .path("access_token");

        return token;
    }
}
