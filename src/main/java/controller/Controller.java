package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import java.io.IOException;
import java.util.ArrayList;
import model.JavaBeans;

import java.io.IOException;

@WebServlet( urlPatterns = {"/Controller", "/main", "/insert", "/select", "/update"})
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DAO dao = new DAO();
    JavaBeans contato = new JavaBeans();
    public Controller() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        System.out.println(action);

        if (action.equals("/main")){
            contatos(req, resp);
        } else if (action.equals("/insert")) { //se o conteudo da variavel action for = insert
            novoContato(req, resp);

        } else if (action.equals("/select")) { //se o conteudo da variavel action for = insert
            ListarContato(req, resp);

        } else if (action.equals("/update")) { //se o conteudo da variavel action for = insert
            editarContato(req, resp);

        } else {
            resp.sendRedirect("index.html");
        }


    }

    // Listar Contatos
    protected void contatos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Criando um obj que ira receber os dados javabeans:

        ArrayList<JavaBeans> lista = dao.listarContatos();

        //Encaminhar a lista ao documento agenda.jsp;
        req.setAttribute("contatos", lista);
        RequestDispatcher rd = req.getRequestDispatcher("agenda.jsp");
        rd.forward(req, resp);
    }

    // Novo Contato
    protected void novoContato(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dao.testeConexao();

        // setar as variaveis JavaBeans
        contato.setNome(req.getParameter("nome"));
        contato.setFone(req.getParameter("fone"));
        contato.setEmail(req.getParameter("email"));

        // Invocar o método inserirContato passando o objt contato:
        dao.inserirContato(contato);

        System.out.println("INSERT OK!");

        // redirecionar para o documento agenda.jsp
        resp.sendRedirect("main");

    }


    protected void ListarContato(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // recebimento do id do elemento a ser editado
        String idcon = req.getParameter("idcon");
        // Setar a variavel javaBeans
        contato.setIdcon(idcon);
        // Executar o metodo seleciona contato:
        dao.selecionarContato(contato);

        System.out.println("teste recebimento");

        System.out.println(contato.getIdcon());
        System.out.println(contato.getNome());
        System.out.println(contato.getFone());
        System.out.println(contato.getEmail());

        // Setar o conteudo do formulario com o conteúdo JavaBeans
        req.setAttribute("idcon", contato.getIdcon());
        req.setAttribute("nome", contato.getNome());
        req.setAttribute("fone", contato.getFone());
        req.setAttribute("email", contato.getEmail());
        // Encaminhar o documento editar.jsp

        RequestDispatcher rd = req.getRequestDispatcher("editar.jsp");
        rd.forward(req, resp);

    }
    protected void editarContato(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("teste de recebimento do metodo editar contato: ");

        System.out.println(req.getParameter("idcon"));
        System.out.println(req.getParameter("nome"));
        System.out.println(req.getParameter("fone"));
        System.out.println(req.getParameter("email"));

        // Setar a variavel javaBeans
        contato.setIdcon(req.getParameter("idcon"));
        contato.setNome(req.getParameter("nome"));
        contato.setFone(req.getParameter("fone"));
        contato.setEmail(req.getParameter("email"));

        // executar o metedo alterar contato

        dao.alterarcontato(contato); //update no banco.

        // redirecionar para o documento agenda.jsp:

        resp.sendRedirect("main");


    }

}
