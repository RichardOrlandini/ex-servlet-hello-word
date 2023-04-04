package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
    // modulo de conexão.
    // parametros de conexão.
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://127.0.0.1:3306/dbagendajava?useTimezone=true&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "1234";


    //método de conexão:
    private Connection conectar(){
        Connection con; // =  null

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL,USER,PASSWORD);
            return con;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    // CRUD CREATE:
    public void inserirContato(JavaBeans contato) {
        String create = "INSERT INTO contatos (nome, fone, email) values (?, ?, ?)";

        try {
            // abrir conexão com o banco:
            Connection con = conectar();

            // preparar a query para execução.
            PreparedStatement pst = con.prepareStatement(create);
            // Substituir os parâmetros (?) pelas variaveis

            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            // executar a query:
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println("parei no insert");
            System.out.println(e);

        }
    }

    // CRUD READ:
    public ArrayList<JavaBeans> listarContatos() {
        // Criando um obj para acessar a classe JavaBeans
        ArrayList<JavaBeans> contatos = new ArrayList<>();

        String read = "SELECT * FROM contatos ORDER BY nome";

        try {
           Connection con = conectar();
           PreparedStatement pst = con.prepareStatement(read);
            ResultSet rs = pst.executeQuery();
            // o laço vai ser executado enquanto tiver contatos:
            while (rs.next()){ // ext e um metodo dentro do objt ResultSet usado para listar os dados
                // variaveis de apoio que recebem os dados do banco:

                String idcon = rs.getString(1);
                String nome  = rs.getString(2);
                String fone = rs.getString(3);
                String email = rs.getString(4);
                // populando o array list
                contatos.add(new JavaBeans(idcon, nome, fone, email));
            }
            con.close();
            return contatos;
        } catch (Exception e) {
            System.out.println(e);
            return  null;
        }
    }

    // CRUD UPDATE
    public void selecionarContato(JavaBeans contato){
        String read2 = "SELECT * FROM contatos WHERE idcon = ?";

        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(read2);
            pst.setString(1,contato.getIdcon());
            ResultSet rs = pst.executeQuery();

            // setar as variaveis java beans
            while (rs.next()){
                contato.setIdcon(rs.getString(1));
                contato.setNome(rs.getString(2));
                contato.setFone(rs.getString(3));
                contato.setEmail(rs.getString(4));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // editar o contato :
    public void alterarcontato(JavaBeans contato) {
        String create = "UPDATE contatos SET  nome=?, fone=?, email=? WHERE idcon =?";

        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(create);
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.setString(4, contato.getIdcon());

            pst.executeUpdate();
            con.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    // teste de conexão
    public void  testeConexao(){
        try {
            Connection con = conectar();
            System.out.println(con);
            con.close();

            System.out.println("Teste de conexão Dao ok!");
        } catch (Exception e){
            System.out.println(e);
        }
    }



}
