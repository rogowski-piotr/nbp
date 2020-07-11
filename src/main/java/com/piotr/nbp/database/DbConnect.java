package com.piotr.nbp.database;

import com.piotr.nbp.enums.CurrencyType;
import com.piotr.nbp.model.Currency;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DbConnect {

    String url = "jdbc:derby://localhost:1527/db_nbp";
    Connection connection;
	Statement statement;

	public DbConnect() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
		} catch (SQLException e) {
            System.out.println("Can not connect with data base, reason: " + e.getSQLState());
		}
    }

    public Currency contains(Date date, CurrencyType table) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);

		try {
			ResultSet resultSet = statement.executeQuery("select * from " + table);
			while (resultSet.next()) {
				if (resultSet.getString("datetime").equals(dateStr)) {
					return new Currency(date, resultSet.getDouble("bid"), resultSet.getDouble("ask"));
				}
			}

		} catch (SQLException e) {
			System.out.println("Can not select data: " + e.getSQLState());
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

    public boolean insert(Currency values, CurrencyType table) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(values.getDatetime());
			statement.executeUpdate("insert into " + table +
										"(datetime, bid, ask) " +
										"values ('" + date + "', " + values.getBid() + ", " + values.getAsk() + ")");
		} catch (SQLException e) {
			System.out.println("Can not insert data (db error): " + e.getSQLState());
			return false;
		} catch (NullPointerException e1) {
			System.out.println("Can not insert data: " + e1.getMessage());
			return false;
		}
		return true;
	}

}
