package br.com.desafio.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.desafio.dto.PaymentItem;
import br.com.desafio.exception.DatabaseException;
import br.com.desafio.repository.SQLite;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PaymentDAO {
	
	/**
	 * createTablePayment
	 */
	public void createTablePayment() {

		String query = "CREATE TABLE IF NOT EXISTS PAYMENT( PAYMENT_ID VARCHAR, PAYMENT_VALUE DECIMAL, PAYMENT_STATUS VARCHAR )";
		SQLite sQLite = SQLite.getInstance();
		
		try {
			Connection connection = sQLite.connect();
			Statement statement = connection.createStatement();
			statement.execute(query);
		} catch (SQLException e) {
			log.error("PaymentDAO - createTablePayment: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Insere o pagamento na tabela
	 * @param paymentId
	 * @param paymentValue
	 * @param paymentStatus
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean createPayment(String paymentId, BigDecimal paymentValue, String paymentStatus) throws SQLException {

		log.info("PaymentDAO - createPayment: " + paymentId);
		
		String query = "INSERT INTO PAYMENT(PAYMENT_ID, PAYMENT_VALUE, PAYMENT_STATUS) VALUES (?, ?, ?)";
		
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			pst.setString(1, paymentId);
			pst.setBigDecimal(2, paymentValue);
			pst.setString(3, paymentStatus);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			log.error("PaymentDAO - Error createPayment: " + e.getMessage());
			throw new DatabaseException("Error createPayment", e);
		} finally {
			pst.close();
			con.close();
		}
		return true;
	}
	
	/**
	 * changePaymentStatus
	 * @param paymentId
	 * @param paymentStatus
	 * @return
	 * @throws SQLException
	 */
	public boolean changePaymentStatus(String paymentId, String paymentStatus) throws SQLException{

		log.info("PaymentDAO - changePaymentStatus: " + paymentId);
		
		String query = "UPDATE PAYMENT SET PAYMENT_STATUS = ? WHERE PAYMENT_ID = ?";
		
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			pst.setString(1, paymentId);
			pst.setString(2, paymentStatus);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			log.error("PaymentDAO - Error changePaymentStatus: " + e.getMessage());
			throw new DatabaseException("Error changePaymentStatus", e);
		} finally {
			pst.close();
			con.close();
		}
		return true;
	}
	
	/**
	 * Busca todos os Pagamentos
	 * @return List<PaymentItem>
	 * @throws SQLException
	 */
	public List<PaymentItem> getPayments() throws SQLException{

		log.info("PaymentDAO - getPayments");
		
		String query = "SELECT * FROM PAYMENT";
		
		List<PaymentItem> listPaymentItems = new ArrayList<PaymentItem>();
		PaymentItem paymentItem = null;
		
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				
				String paymentId = rs.getString("PAYMENT_ID");
				BigDecimal paymentValue = rs.getBigDecimal("PAYMENT_VALUE");
				String paymentStatus = rs.getString("PAYMENT_STATUS");

				paymentItem = new PaymentItem();

				paymentItem.setPaymentId(paymentId);
				paymentItem.setPaymentValue(paymentValue);
				paymentItem.setPaymentStatus(paymentStatus);

				listPaymentItems.add(paymentItem);
			}
			
		} catch (SQLException e) {
			log.error("PaymentDAO - Error getPayments: " + e.getMessage());
			throw new DatabaseException("Error getPayments:", e);
		} finally {
			pst.close();
			con.close();
			rs.close();
		}
		return listPaymentItems;
	}
	
	/**
	 * Busca o pagamento por ID
	 * @param paymentId
	 * @return PaymentItem
	 * @throws SQLException
	 */
	public PaymentItem getPaymentById(String paymentId) throws SQLException{

		log.info("PaymentDAO - getPaymentById: " + paymentId);
		
		String query = "SELECT * FROM PAYMENT WHERE PAYMENT_ID = ?";
		
		PaymentItem paymentItem = null;
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			pst.setString(1, paymentId);
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				
				String id = rs.getString("PAYMENT_ID");
				BigDecimal paymentValue = rs.getBigDecimal("PAYMENT_VALUE");
				String paymentStatus = rs.getString("PAYMENT_STATUS");

				paymentItem = new PaymentItem();

				paymentItem.setPaymentId(id);
				paymentItem.setPaymentValue(paymentValue);
				paymentItem.setPaymentStatus(paymentStatus);
			}
			
		} catch (SQLException e) {
			log.error("PaymentDAO - Error getPaymentById: " + e.getMessage());
			throw new DatabaseException("Error getPaymentById:", e);
		} finally {
			pst.close();
			con.close();
			rs.close();
		}
		return paymentItem;
	}
	
	/**
	 * dropTablePayment
	 * @return
	 * @throws SQLException
	 */
	public boolean dropTablePayment() throws SQLException{

		log.info("PaymentDAO - dropTablePayment");
		
		String query = "DROP TABLE PAYMENT";
		
		SQLite sQLite = SQLite.getInstance();
		PreparedStatement pst = null;
		Connection con = null;
		
		try {
			con = sQLite.connect();
			pst = con.prepareStatement(query);
			
			pst.executeUpdate();
			System.out.println(" TABLE PAYMENT DELETED ");
			
		} catch (SQLException e) {
			log.error("PaymentDAO - Error dropTablePayment: " + e.getMessage());
			throw new DatabaseException("Error dropTablePayment", e);
		} finally {
			pst.close();
			con.close();
		}
		return true;
	}

	public static void main(String[] args) throws SQLException {
		//new PaymentDAO().dropTablePayment();
		List<PaymentItem> listPaymentItems = new PaymentDAO().getPayments();
		for (PaymentItem paymentItem : listPaymentItems) {
			System.out.println(paymentItem.getPaymentId() + " - " + paymentItem.getPaymentValue() + " - " + paymentItem.getPaymentStatus());
		}
	}
	
}
