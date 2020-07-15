package com.piotr.nbp;

import com.piotr.nbp.database.DbAdapter;
import com.piotr.nbp.entities.*;
import com.piotr.nbp.enums.CurrencyType;
import com.piotr.nbp.request.HttpRequest;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApiApplication {

	public static void main (String [] args) {

		String dateFromStr = "2020-06-01";
		String dateToStr = "2020-07-15";
		CurrencyType currency = CurrencyType.EUR;

		DbAdapter dbAdapter = new DbAdapter();


		try {
			Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(dateFromStr);
			Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(dateToStr);

			List<Date> missingDateElements = dbAdapter.getMissingElements(dateFrom, dateTo, currency);

			for (Date date : missingDateElements) {
				try {
					Currency entity = HttpRequest.request(currency, date);
					dbAdapter.insert(entity);
				} catch (HTTPException e) {
						if (!(e.getStatusCode() == 404))
							throw new HTTPException(e.getStatusCode());
				}
			}

			Currency bestAsk = null;
			Currency bestBid = null;

			Date date = dateFrom;
			while (date.before(dateTo) || date.equals(dateTo)) {
				Currency entity = dbAdapter.getEntity(date, currency);
				if (entity != null) {
					System.out.println(entity);
					if (bestAsk == null) {
						bestAsk = entity;
						bestBid = entity;
					}
					if (entity.getAsk() < bestAsk.getAsk()) {
						bestAsk = entity;
					}
					if (entity.getBid() > bestBid.getBid()) {
						bestBid = entity;
					}
				}

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				date = calendar.getTime();
			}

			System.out.println("\nBEST BID:");
			System.out.println(bestBid);
			System.out.println("BEST ASK");
			System.out.println(bestAsk);


		} catch (HTTPException e1) {				// catch any HTTP error code from server
			System.out.println("Server connection error http code: " + e1.getStatusCode());
		} catch (IOException e2) {					// do when an error occurred on the client's side
			System.out.println(e2.getMessage() + " is not available (client error)");
		} catch (ParseException e3) {				// catch if wrong data format during parsing
			System.out.println("Wrong date format: " + e3.getMessage());
		}
	}
}