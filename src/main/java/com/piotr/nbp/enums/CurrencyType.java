package com.piotr.nbp.enums;

public enum CurrencyType {

    USD("usd"),
    RUB("rub"),
    EUR("eur");

    public String url;

    CurrencyType(String url) {
        this.url = url;
    }
}