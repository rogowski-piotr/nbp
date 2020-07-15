package com.piotr.nbp;

import com.google.gson.Gson;
import com.piotr.nbp.database.DbAdapter;
import com.piotr.nbp.entities.Currency;
import com.piotr.nbp.entities.EurEntity;
import com.piotr.nbp.entities.RubEntity;
import com.piotr.nbp.entities.UsdEntity;
import com.piotr.nbp.enums.CurrencyType;
import com.piotr.nbp.request.HttpRequest;
import com.piotr.nbp.request.JsonObject;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.persistence.PersistenceException;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiApplication {

	public static void main (String [] args) {

		String dateStr = "2020-07-06";
		CurrencyType currency = CurrencyType.EUR;

		HttpRequest r = new HttpRequest("http://api.nbp.pl/api/exchangerates/rates/c/");		// creating object which is a request to worldtimeapi.org
		DbAdapter dbAdapter = new DbAdapter();


		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

			Currency e = dbAdapter.getEntity(date);
			if (e != null) {
				System.out.println(e);
				System.exit(0);
			}

			String response = r.request(currency, date);
			System.out.println(response);

			Gson gson = new Gson();
			JsonObject jsOb = gson.fromJson(response, JsonObject.class);

			Currency entity = null;
			switch (currency) {
				case EUR:
					entity = new EurEntity(date, jsOb.rates.get(0).bid, jsOb.rates.get(0).ask);
					break;
				case USD:
					entity = new UsdEntity(date, jsOb.rates.get(0).bid, jsOb.rates.get(0).ask);
					break;
				case RUB:
					entity = new RubEntity(date, jsOb.rates.get(0).bid, jsOb.rates.get(0).ask);
					break;
			}

			dbAdapter.insert(entity);

		} catch (HTTPException e1) {				// catch any HTTP error code from server
			System.out.println("Server connection error http code: " + e1.getStatusCode());
		} catch (IOException e2) {					// do when an error occurred on the client's side
			System.out.println(e2.getMessage() + " is not available (client error)");
		} catch (ParseException e3) {				// catch if wrong data format during parsing
			System.out.println("Wrong format of date: " + e3.getMessage());
		}
	}
}