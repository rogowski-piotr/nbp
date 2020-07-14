package com.piotr.nbp;

import com.google.gson.Gson;
import com.piotr.nbp.database.DbAdapter;
import com.piotr.nbp.entities.EurEntity;
import com.piotr.nbp.enums.CurrencyType;
import com.piotr.nbp.request.HttpRequest;
import com.piotr.nbp.request.JsonObject;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiApplication {

	public static void main (String [] args) {

		String dateStr = "2020-07-08";
		CurrencyType currency = CurrencyType.EUR;

		DbAdapter dbAdapter = new DbAdapter();

		HttpRequest r = new HttpRequest("http://api.nbp.pl/api/exchangerates/rates/c/");		// creating object which is a request to worldtimeapi.org

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

			if (!dbAdapter.contain(date)) {
				String response = r.request(currency, date);
				System.out.println(response);

				Gson gson = new Gson();
				JsonObject jsOb = gson.fromJson(response, JsonObject.class);

				EurEntity entity = new EurEntity(date, jsOb.rates.get(0).bid, jsOb.rates.get(0).ask);
				dbAdapter.insert(entity);
			}

		} catch (HTTPException ex) {				// catch any HTTP error code from server
			System.out.println("Server connection error http code: " + ex.getStatusCode());
		} catch (IOException exc) {					// do when an error occurred on the client's side
			System.out.println(exc.getMessage() + " is not available (client error)");
		} catch (ParseException e) {				// catch if wrong data format during parsing
			System.out.println("Wrong format of date");
		}
	}
}