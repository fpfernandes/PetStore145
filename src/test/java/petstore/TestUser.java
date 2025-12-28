package petstore;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

public class TestUser {
    static String uri = "https://petstore.swagger.io/v2/user";
    static String ct = "application/json";
    static String token;

    @Test
    public static String login() {
        String username = "charlie";
        String password = "abcdef";

        String resultadoEsperado = "logged in user session:";

        Response resposta = (Response) given()
            .contentType(ct)
            .log().all()
        .when()
            .get(uri + "/login?username=" + username + "&password=" + password)
        .then()
            .log().all()
            .statusCode(200)                      // Valida status code
            .body("message", containsString(resultadoEsperado)) // valida parte da mensagem 
        .extract()                                                   // extrai resposta
        ;

        // extracao
        token = resposta.jsonPath().getString("message").substring(23);
        System.out.println("Conteudo do Token/API Key: " + token);
        return token;

    }
    
}
