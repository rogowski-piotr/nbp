package com.piotr.nbp.request;


import java.util.List;

/**
 * @JsonObiect represents an object of the server's response
 */

public class JsonObject {

    public class Rates {
        public String no;
        public String effectiveDate;
        public double bid;
        public double ask;

        public Rates(String no, String effectiveDate, double bid, double ask) {
            this.no = no;
            this.effectiveDate = effectiveDate;
            this.bid = bid;
            this.ask = ask;
        }
    }

    public final String table;
    public final String currency;
    public final String code;
    public final List<Rates> rates;

    JsonObject(String table, String currency, String code, List rates) {
        this.table = table;
        this.currency = currency;
        this.code = code;
        this.rates = rates;
    }
}