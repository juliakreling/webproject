package model;

import java.io.File;
import entities.Cliente;
import entities.DadosTabelaAdministrador;
import entities.DadosTabelaCliente;
import entities.Divida;
import entities.Endereco;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	
	private String getconfigValueByKey(String key) throws IOException {
		//File f = new File("/Users/juliagabrielekreling/Downloads/WebProject3/admin/config.ini");
		File f = new File("/Users/juliagabrielekreling/TERCEIRAPROVA/WebProject3/admin/config.ini");
		FileInputStream fis = new FileInputStream(f);
		byte[] content = new byte[fis.available()];
		fis.read(content);
		String fileContent = new String(content);
		String[] variables = fileContent.split("\r\n");
			for(String variable: variables) {
				int indexSeparator = variable.indexOf("=");
				String keyTemp = variable.substring(0, indexSeparator);
				if(keyTemp.equals(key)) {
					fis.close();
					return variable.substring(indexSeparator+1, variable.length());
				}
			}
			fis.close();
			return null;
	}
	
	
	public java.sql.Connection connectDB() throws IOException, SQLException{
		String url = this.getconfigValueByKey("url");
		String user = this.getconfigValueByKey("user");
		String password = this.getconfigValueByKey("password");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection(url, user, password);
			System.out.println("Conexão com MYSQL ok !!");
			return con;
		} catch (ClassNotFoundException e) {
			System.out.println("ERRO E NAO CONECTOU");
			e.printStackTrace();
		}
		return null;
	}
	
	
	public DadosTabelaAdministrador buscarAdmPorLogin(String email) throws SQLException, IOException {
		DadosTabelaAdministrador adm = new DadosTabelaAdministrador();
		String sql = "SELECT IDADMINISTRADOR FROM ADMINISTRADOR WHERE EMAIL=?";
		Connection c = this.connectDB();
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		rs.next();
		adm.setIDADMINISTRADOR(rs.getString(1));
		c.close();
		return adm;
	}
	
	
	public List<DadosTabelaAdministrador> listaAdministradores() throws SQLException, IOException {
		List<DadosTabelaAdministrador> listaAdministrador = new ArrayList<DadosTabelaAdministrador>();
		String sql = "SELECT IDADMINISTRADOR, EMAIL, TOKEN, SENHA FROM ADMINISTRADOR";
		Connection c = this.connectDB();
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			DadosTabelaAdministrador administrador = new DadosTabelaAdministrador();
			administrador.setIDADMINISTRADOR(rs.getString(1));
			administrador.setEMAIL(rs.getString(2));
			administrador.setTOKEN(rs.getString(3));
			administrador.setSENHA(rs.getString(4));
			listaAdministrador.add(administrador);
		}
		c.close();
		return listaAdministrador;
	}
	
	
	public List<DadosTabelaCliente> listaClientes() throws IOException, SQLException{
		List<DadosTabelaCliente> listaCliente = new ArrayList<DadosTabelaCliente>();
		String sql = "SELECT IDCLIENTE, NOME, CPF, EMAIL, CEP, COMPLEMENTO, NUMEROCASA, NUMEROPROCESSO, VALORDIVIDA, SITUACAO FROM CLIENTE";
		Connection c = this.connectDB();
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			DadosTabelaCliente cliente = new DadosTabelaCliente();
			cliente.setIDCLIENTE(rs.getString(1));
			cliente.setNOME(rs.getString(2));
			cliente.setCPF(rs.getString(3));
			cliente.setEMAIL(rs.getString(4));
			cliente.setCEP(rs.getString(5));
			cliente.setCOMPLEMENTO(rs.getString(6));
			cliente.setNUMEROCASA(rs.getString(7));
			cliente.setNUMEROPROCESSO(rs.getString(8));
			cliente.setVALORDIVIDA(rs.getString(9));
			cliente.setSITUACAO(rs.getString(10));
			listaCliente.add(cliente);
		}
		return listaCliente;
	}
	
				
	public boolean insereToken(String idAdministrador, String token) throws SQLException, IOException {
		try {
			String sql = "UPDATE ADMINISTRADOR SET TOKEN =? WHERE IDADMINISTRADOR =?";
			Connection c = this.connectDB();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, token);
			ps.setString(2, idAdministrador);
			int linhasAfetadas = ps.executeUpdate();
			System.out.println("DEU BOA O TOKEN !!");
			return linhasAfetadas > 0 ? true: false;
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
		

	
	
//	public List<Cliente> consultarClientes() throws IOException, SQLException{
//		List<Cliente> clientes = new ArrayList<Cliente>();
//		String sql = "SELECT IDCLIENTE, NOME, CPF, EMAIL, TOKEN, SENHA FROM CLIENTE";
//		Connection c = this.connectDB();
//		PreparedStatement ps = c.prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		while(rs.next()) {
//			Cliente cliente = new Cliente();
//			cliente.setIdClient(rs.getString(1));
//			cliente.setNome(rs.getString(2));
//			cliente.setCpf(rs.getString(3));
//			cliente.setEmail(rs.getString("email"));
//			cliente.setToken(rs.getString(5));
//			cliente.setSenha(rs.getString(6));
//			clientes.add(cliente);
//			cliente.getNome();
//		}
//		c.close();
//		return clientes;
//	}
	
//	public Cliente buscarClientePorLogin(String email) throws SQLException, IOException {
//		Cliente cliente = new Cliente();
//		String sql = "SELECT IDCLIENTE, NOME, CPF, EMAIL FROM CLIENTE WHERE EMAIL=?";
//		Connection c = this.connectDB();
//		PreparedStatement ps = c.prepareStatement(sql);
//		ps.setString(1, email);
//		ResultSet rs = ps.executeQuery();
//		rs.next();
//		cliente.setIdClient(rs.getString(1));
//		cliente.setNome(rs.getString(2));
//		cliente.setCpf(rs.getString(3));
//		cliente.setEmail(rs.getString(4));
//		c.close();
//		return cliente;
//	}
	
//	public Endereco consultarEnderecos(String idCliente) throws IOException, SQLException{
//		Endereco endereco = new Endereco();
//		String sql = "SELECT IDENDERECO, CEP, NUMEROCASA, COMPLEMENTO FROM ENDERECO WHERE IDCLIENTE=?";
//		Connection c = this.connectDB();
//		PreparedStatement ps = c.prepareStatement(sql);
//		ps.setString(1, idCliente);
//		ResultSet rs = ps.executeQuery();
//		try {
//			endereco.setIdEndereco(rs.getString(1));
//			endereco.setCep(rs.getString(2));
//			endereco.setNumeroCasa(rs.getString(3));
//			endereco.setComplemento(rs.getString(4));
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		c.close();
//		return endereco;
//	}
//	
//	public List<Divida> consultarDividas(String idCliente) throws IOException, SQLException {
//		List<Divida> dividas = new ArrayList<Divida>();
//		String sql = "SELECT IDDIVIDA, VALORDIVIDA, SITUACAO, NUMEROPROCESSO FROM DIVIDA WHERE IDCLIENTE=?";
//		Connection c = this.connectDB();
//		PreparedStatement ps = c.prepareStatement(sql);
//		ps.setString(1, idCliente);
//		ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//			Divida divida = new Divida();
//			divida.setIdDivida(rs.getString(1));
//			divida.setValor(rs.getString(2));
//			divida.setSituacao(rs.getString(3));
//			divida.setNumeroProcesso(rs.getString(4));
//			dividas.add(divida);
//			}
//		return dividas;
//	}
	
//	public boolean insereToken(String idCliente, String token) throws SQLException, IOException {
//		try {
//		String sql = "UPDATE CLIENTE SET TOKEN =? WHERE IDCLIENTE =?";
//		Connection c = this.connectDB();
//		PreparedStatement ps = c.prepareStatement(sql);
//		ps.setString(1, token);
//		ps.setString(2, idCliente);
//		int linhasAfetadas = ps.executeUpdate();
//		return linhasAfetadas > 0 ? true: false;
//		}catch(Exception e) {
//			System.err.println(e.getMessage());
//		}
//		return false;
//	}
}
