package com.piotr.nbp;

import com.google.gson.Gson;
import com.piotr.nbp.enums.CurrencyEnum;
import com.piotr.nbp.request.HttpRequest;
import com.piotr.nbp.request.JsonObject;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiApplication {

	public static void main (String [] args) {

		CurrencyEnum currency = CurrencyEnum.EUR;

		HttpRequest r = new HttpRequest("http://api.nbp.pl/api/exchangerates/rates/c/");		// creating object which is a request to worldtimeapi.org
		Gson gson = new Gson();

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-15");

			String response = r.request(currency, date);
			System.out.println(response);

			JsonObject jsOb = gson.fromJson(response, JsonObject.class);
			System.out.println(jsOb.rates.get(0).ask);
			System.out.println(jsOb.rates.get(0).bid);


		} catch (HTTPException ex) {				// catch any HTTP error code from server
			System.out.println("Server connection error http code: " + ex.getStatusCode());
		} catch (IOException exc) {					// do when an error occurred on the client's side
			System.out.println(exc.getMessage() + " is not available (client error)");
		} catch (ParseException e) {				// catch if wrong data format during parsing
			System.out.println("Wrong format of date");
		}
	}
}