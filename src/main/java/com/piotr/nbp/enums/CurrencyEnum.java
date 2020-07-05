package com.piotr.nbp.enums;

public enum CurrencyEnum {

    USD("usd/"),
    RUB("rub/"),
    EUR("eur/");

    public String url;

    private CurrencyEnum(String url) {
        this.url = url;
    }

}