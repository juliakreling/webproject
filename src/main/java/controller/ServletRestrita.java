package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.DadosTabelaCliente;
import model.DAO;

/**
 * Servlet implementation class ServletRestrita
 */
public class ServletRestrita extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRestrita() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void addCORSHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Accept, X-PINGOTHER, Origin");
		response.addHeader("Access-Control-Max-Age", "1728000");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		addCORSHeaders(response);
		List<DadosTabelaCliente> tabelaCliente;
		try {
			tabelaCliente = dao.listaClientes();
			Object[] json = new Object[] {tabelaCliente};
			Gson gson = new Gson();
			String jsonOutput = gson.toJson(json);
			response.setStatus(200);
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			response.getWriter().append(jsonOutput);
		} catch (IOException | SQLException e) {
			System.out.println("Erro no método doGet !!");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
