package com.piotr.nbp.request;

import com.piotr.nbp.enums.CurrencyType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.ws.http.HTTPException;

public class HttpRequest {

    String url;

    public HttpRequest(String url) {
        this.url = url;
    }

    public String request(CurrencyType currency, Date date) throws IOException, HTTPException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);

        String urlAddress = this.url + currency.url + "/" + dateString + "/?format=json";

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