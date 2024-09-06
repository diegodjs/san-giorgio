package br.com.desafio.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class SQLite {

	private static SQLite sQLite;

	private static Connection connection;

	public static synchronized SQLite getInstance() {
		if (sQLite == null)
			sQLite = new SQLite();
		return sQLite;
	}

	public Connection connect() throws SQLException {

		if (connection == null || connection.isClosed()) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:banco.db");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return connection;
	}

}