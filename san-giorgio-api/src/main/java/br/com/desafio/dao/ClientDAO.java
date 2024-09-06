package br.com.desafio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.desafio.exception.DatabaseException;
import br.com.desafio.repository.SQLite;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClientDAO {

	/**
	 * createTableClient
	 */
	public void createTableClient() {

		String query = "CREATE TABLE IF NOT EXISTS CLIENT( CLIENT_ID VARCHAR, CLIENT_NAME VARCHAR )";
		SQLite sQLite = SQLite.getInstance();

		try {
			Connection connection = sQLite.connect();
			Statement statement = connection.createStatement();
			statement.execute(query);
		} catch (SQLException e) {
			log.error("ClientDAO - createTableClient: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Insere Client (vendedor)
	 * @param clientId
	 * @param clientName
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean createClient(String clientId, String clientName) throws SQLException {

		log.info("ClientDAO - createClient: " + clientId);

		String query = "INSERT INTO CLIENT(CLIENT_ID, CLIENT_NAME) VALUES (?, ?)";

		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;

		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);

			pst.setString(1, clientId);
			pst.setString(2, clientName);

			pst.executeUpdate();

		} catch (SQLException e) {
			log.error("ClientDAO - Error createClient: " + e.getMessage());
			return false;
		} finally {
			pst.close();
			con.close();
		}
		return true;
	}
	
	/**
	 * busca todos os clientes (vendedores)
	 * @return List<String>
	 * @throws SQLException
	 */
	public List<String> getClients() throws SQLException{

		log.info("ClientDAO - getClients");
		
		String query = "SELECT * FROM CLIENT";
		
		List<String> listClients = new ArrayList<String>();
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				
				String clientId = rs.getString("CLIENT_ID");

				listClients.add(clientId);
			}
			
		} catch (SQLException e) {
			log.error("ClientDAO - Error getClients: " + e.getMessage());
			throw new DatabaseException("Error getClients:", e);
		} finally {
			pst.close();
			con.close();
			rs.close();
		}
		return listClients;
	}
	
	/**
	 * Busca o cliente ou vendedor pelo ID
	 * @param clientId
	 * @return
	 * @throws SQLException
	 */
	public String getClientById(String clientId) throws SQLException {

		log.info("ClientDAO - getClientById: " + clientId);
		
		String query = "SELECT * FROM CLIENT WHERE CLIENT_ID = ?";
		
		String idReturned = null;
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			pst.setString(1, clientId);
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				
				idReturned = rs.getString("CLIENT_ID");

				return idReturned;
			}
			
		} catch (SQLException e) {
			log.error("ClientDAO - Error getClientById: " + e.getMessage());
			throw new DatabaseException("Error getClientById:", e);
		} finally {
			pst.close();
			con.close();
			rs.close();
		}
		return idReturned;
	}
	
	/**
	 * dropTableClient
	 * @return
	 * @throws SQLException
	 */
	public boolean dropTableClient() throws SQLException{

		log.info("ClientDAO - dropTableClient");
		
		String query = "DROP TABLE CLIENT";
		
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			pst.executeUpdate();
			System.out.println(" TABLE CLIENT DELETED ");
			
		} catch (SQLException e) {
			log.error("ClientDAO - Error dropTableClient: " + e.getMessage());
			throw new DatabaseException("Error dropTableClient", e);
		} finally {
			pst.close();
			con.close();
		}
		return true;
	}

	public static void main(String[] args) throws SQLException {
		//new ClientDAO().dropTableClient();
		List<String> listClients = new ClientDAO().getClients();
		for (String clientId : listClients) {
			System.out.println(clientId);
		}
	}

}
