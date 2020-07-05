package com.piotr.nbp.request;

import com.piotr.nbp.enums.CurrencyEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.ws.http.HTTPException;

public class HttpRequest implements NbpRequest {

    String url;

    public HttpRequest(String url) {
        this.url = url;
    }

    public String request(CurrencyEnum currency, Date date) throws IOException, HTTPException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);

        // http://api.nbp.pl/api/exchangerates/rates/c/usd/2016-04-04/?format=json
        String urlAddress = this.url + currency.url + dateString + "/?format=json";

        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String r = in.readLine();
            in.close();
            return r;
        } else throw new HTTPException(connection.getResponseCode());
    }

}