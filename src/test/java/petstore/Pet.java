package petstore;

public class Pet {
    // Definir a classe Pet para a estrutura de dados dos animais
    public int id;
    public class Category{
        public int id;
        public String name;
    }
    public Category category;
    public String name;
    public String[] photoUrls;
    public class Tag{
        public int id;
        public String name;
    }
    public Tag tags[];
    public String status;  
}