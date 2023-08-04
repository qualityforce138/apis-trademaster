
import org.testng.annotations.Test;
import java.io.FileNotFoundException;


public class TokenTest extends Base {
    String token;
    String baseUrl = configuracao.getBaseUrl();

    public TokenTest() throws FileNotFoundException {
    }

    @Test
    public void TesteDeToken() {
        System.out.println(getToken());
    }



}
