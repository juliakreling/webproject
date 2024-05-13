package controller;

import model.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//import entities.Cliente;
import entities.DadosTabelaAdministrador;

public class VerificaLogin {

	DAO dao = new DAO();
	
	public boolean verificaLogin(String login, String senha) throws IOException, SQLException {
		List<DadosTabelaAdministrador> lista = dao.listaAdministradores();
		String[][] dados = new String[lista.size()][2];
		int i = 0;
		
		for(DadosTabelaAdministrador c: lista) {
			dados[i][0] = c.getEMAIL();
			dados[i][1] = c.getSENHA();
			i++;
		}
		
		for(int j = 0; j<lista.size(); j++) {
			if(dados[j][0].equals(login) && dados[j][1].equals(senha)) {
				return true;
			}
		}
		return false;
	}
	
//	public boolean verificaLogin(String login, String senha) throws IOException, SQLException {
//		List<Cliente> lista = dao.consultarClientes();
//		String[][] dados = new String[lista.size()][2];
//		int i = 0;
//		
//		for(Cliente c: lista) {
//			dados[i][0] = c.getEmail();
//			dados[i][1] = c.getSenha();
//			i++;
//		}
//		
//		for(int j = 0; j<lista.size(); j++) {
//			if(dados[j][0].equals(login) && dados[j][1].equals(senha)) {
//				return true;
//			}
//		}
//		return false;
//	}
}
