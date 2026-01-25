// 1 - Pacote (grupo de classes reunidas)
package petstore;

// 2 - Imports / Framework e Bibliotecas
import java.io.IOException;
import java.nio.file.Files; // Cuida de operações com pastas e arquivos
import java.nio.file.Paths; // Cuida de direcionar pastas e arquivos

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.matchesPattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

// 3 - Classe
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ativa a ordenacao dos testes
public class TestUserExercicio {
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

    @Test @Order(1)
    public void postUser() throws IOException {
        String jsonBody = lerJson("data/user.json");

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

    @Test @Order(2)
    public void GetUser() throws IOException{
        
        given()                                           // DADO
            .contentType(ct)                              // Formato transmissao
            .log().all()
        .when()                                           // QUANDO
            .get(uri + "/" + username)                    // Get (endpoint)  // Consulta URL/user/username
        .then()
            .log().all()                                // Exibir a resposta(requisicao)
             // Teste Basico
            .statusCode(200)                             
            // Testes de Contrato
            .body("id", allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(999999)))
            .body("username", instanceOf(String.class))
            .body("firstName", instanceOf(String.class))
            .body("lastName", instanceOf(String.class))
            .body("email", allOf( 
                instanceOf(String.class), 
                containsString("@"), 
                containsString(".com") ))
            .body("phone", matchesPattern("^\\d{11}$"))
             // Testes Funcionais
            .body("id", is(userId))                       
            .body("username", is(username))
            .body("firstName", is(firstName))
            .body("lastName", is(lastName))
            .body("email", is(email))
            .body("password", is(password))
            .body("phone", is(phone));

    } // Final do Get

    @Test @Order(3)
    public void PutUser() throws IOException {
        String jsonBody = lerJson("data/userput.json");
        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .put(uri + "/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("type", is("unknown"));
    } // Final do Put

    @Test @Order(4)
    public void DeleteUser() throws IOException {
        given()
            .contentType(ct)
            .log().all()
        .when()                                           
            .delete(uri + "/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("type", is("unknown"))
            .body("message", is("bsimpson"));

    } // Final do Delete

    // DDT - Data Driven Testing
    // Teste com Json (Body) parametrizado
    @ParameterizedTest @Order(5)
    @CsvFileSource (resources = "/users.csv", numLinesToSkip = 1, delimiter = ',')
    public void PostUserDDT(
        int id,
        String username,
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        int userStatus
    ) // fim do csv
    {  // inicio do postuser

      // Classe User para receber os dados (funciona como um template)
    User user = new User(); // instancia objeto user a partir da classe User
    user.id = id;
    user.username = username;
    user.firstName = firstName;
    user.lastName = lastName;
    user.email = email;
    user.password = password;
    user.phone = phone;
    user.userStatus = userStatus;

    // Json a partir do objeto user preenchido com os dados do csv
    Gson gson = new Gson();
    String jsonBody = gson.toJson(user);

    given()                               
        .contentType(ct)   
        .log().all()                       
        .body(jsonBody)                    
    .when()                                
        .post(uri)                         
    .then()
        .log().all()                       
        .statusCode(200)                  
        .body("message", is(String.valueOf(id)));       
    
    }

}
