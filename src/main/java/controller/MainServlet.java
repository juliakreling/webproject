package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import entities.DadosTabelaAdministrador;
import entities.DadosTabelaCliente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import model.DAO;

/**
 * Servlet implementation class MainServlet
 */

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	VerificaLogin verificar = new VerificaLogin();
	DAO dao = new DAO();
	JwtCreator jwtCreator = new JwtCreator();
	public final static String SECRET_KEY = "123456789";

    /**
     * Default constructor. 
     */
    public MainServlet() {
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin","*");
		response.addHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE,HEAD");
		response.addHeader("Access-Control-Allow-Headers","X-PINGOTHER,Origin,X-Requested-With,Content-Type,Accept");
		response.addHeader("Access-Control-Max-Age","1728000");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		try {
			if(verificar.verificaLogin(login, password) == true) {
				DadosTabelaAdministrador idAdministrador = dao.buscarAdmPorLogin(login);
				String jwtId = idAdministrador.getIDADMINISTRADOR();
				String jwtIssuer = "PlaceHolder";
				String jwtSubject = idAdministrador.getEMAIL();
				long jwtTimeToLive = 3600*1000;
				String jwtFinal = jwtCreator.createJWT(jwtId, jwtIssuer, jwtSubject, jwtTimeToLive);
				dao.insereToken(idAdministrador.getIDADMINISTRADOR(), jwtFinal);
				
				List<DadosTabelaCliente> tabelaCliente = dao.listaClientes();
				Object[] json = new Object[] {tabelaCliente};
				Gson gson = new Gson();
				String jsonOutput = gson.toJson(json);
				response.setStatus(200);
				response.setHeader("content-Type", "application/JSON");
				response.getWriter().append(jsonOutput);
	
			} else {
				response.setStatus(401);
				response.getWriter().append("Login ou Senha inválidos!");
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Claims decodeJWT(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(jwt).getBody();
		return claims;
	}
	
	

}
