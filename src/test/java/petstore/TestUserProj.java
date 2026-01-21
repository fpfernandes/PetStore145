// 1 - Pacote (grupo de classes reunidas)
package petstore;

// 2 - Imports / Framework e Bibliotecas
import java.io.IOException;
import java.nio.file.Files; // Cuida de operações com pastas e arquivos
import java.nio.file.Paths; // Cuida de direcionar pastas e arquivos
 
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

// 3 - Classe
public class TestUserProj {
    // Definir a classe User para a estrutura de dados dos usuários
    // 3.1 - Atributos
    static String uri = "https://petstore.swagger.io/v2/user";
    //String baseUrl = "https://petstore.swagger.io/v2" // Base URL do swagger petstore
    static String ct = "application/json"; // content-type
    static int userId = 160126;  // id do usuário
    static String username = "bsimpson"; // nome do usuário
    static String firstName = "Bart";
    static String lastName = "Simpson"; 
    static String email = "bsimpson@email.com";
    static String password = "Test12345678";
    static String phone = "08896524123";



    // 3.2 - Funções de Apoio
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    } 


    // 3.3 - Métodos e Funções em sí
    
    //Incluir - Create - Post
    // visibilidade Tipo
    // public       String = Texto
    // private      int    = número inteiro
    @Test
    public void postUser() throws IOException {
        String jsonBody = lerJson(caminhoJson:"data/user.json");

        // Grande linha de comando para realizar um metodo de API
        given()                                // DADO
            .contentType(ct)   // Formato transmissao
            .log().all()                       // Exibir o envio (ver o que esta mandando)
            .body(jsonBody)                    // Conteudo mensagem (get e delete nao tem essa linha)
        .when()                                // QUANDO
            .post(uri)                         // Post (endpoint) (no caso do get e delete passa o codigo da pessoa no parenteses)
            //.post(baseUrl + "/user")        // se fosse usar o Url
        .then()
            .log().all()                       // Exibir a resposta(requisicao)
            .statusCode(200)                   // Comunicacao
            .body("message", is("160126"));       // Verificacoes
    

    } // Final Post

    public void GetPet() throws IOException{
        
        given()                                           // DADO
            .contentType(ct)                              // Formato transmissao
            .log().all()
        .when()                                           // QUANDO
            .get(uri + "/" + username)                    // Get (endpoint)  // Consulta URL/user/username
            .log().all()                                // Exibir a resposta(requisicao)
            .statusCode(200)                              // Comunicacao
            .body("id", is(userId))                       // Verificacoes
            .body("username", is(username))
            .body("firstName", is(firstName))
            .body("lastName", is(lastName))
            .body("email", is(email))
            .body("password", is(password))
            .body("phone", is(phone));



    }

}