package com.piotr.nbp;

import com.google.gson.Gson;
import com.piotr.nbp.database.DbConnect;
import com.piotr.nbp.enums.CurrencyType;
import com.piotr.nbp.model.Currency;
import com.piotr.nbp.request.HttpRequest;
import com.piotr.nbp.request.JsonObject;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiApplication {

	public static void main (String [] args) {

		DbConnect db = new DbConnect();
		CurrencyType currencyType = CurrencyType.USD;
		HttpRequest r = new HttpRequest("http://api.nbp.pl/api/exchangerates/rates/c/");		// creating object which is a request to worldtimeapi.org
		Currency entity = null;
		Date date = null;
		String dateStr = "2020-06-09";


		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			Gson gson = new Gson();

			String response = r.request(currencyType, date);
			//System.out.println(response);

			JsonObject jsOb = gson.fromJson(response, JsonObject.class);

			entity = new Currency(date, jsOb.rates.get(0).bid, jsOb.rates.get(0).ask);

		} catch (HTTPException ex) {				// catch any HTTP error code from server
			System.out.println("Server connection error http code: " + ex.getStatusCode());
		} catch (IOException exc) {					// do when an error occurred on the client's side
			System.out.println(exc.getMessage() + " is not available (client error)");
		} catch (ParseException e) {				// catch if wrong data format during parsing
			System.out.println("Wrong date format " + e.getMessage());
		}

		if (db.contains(date, currencyType) == null) {
			if (db.insert(entity, currencyType)) {
				System.out.println("[database] Inserted: " + entity);
			}
		}

		if (date != null && entity != null) {
			System.out.println(entity);
		}



	}
}