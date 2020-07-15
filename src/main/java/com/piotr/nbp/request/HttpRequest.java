package com.piotr.nbp.request;

import com.google.gson.Gson;
import com.piotr.nbp.entities.*;
import com.piotr.nbp.enums.CurrencyType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.ws.http.HTTPException;


public abstract class HttpRequest {

    private static final String URL = "http://api.nbp.pl/api/exchangerates/rates/c/";

    public static Currency request(CurrencyType currency, Date date) throws IOException, HTTPException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);

        String urlAddress = URL + currency.url + "/" + dateString + "/?format=json";

        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            in.close();
            return convertToEntity(response, currency, date);

        } else throw new HTTPException(connection.getResponseCode());
    }

    private static Currency convertToEntity(String response, CurrencyType currency, Date date) {
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
        return entity;
    }

}