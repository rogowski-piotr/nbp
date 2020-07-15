package com.piotr.nbp.enums;

import com.piotr.nbp.entities.*;

public enum CurrencyType {

    USD("usd", UsdEntity.class),
    RUB("rub", RubEntity.class),
    EUR("eur", EurEntity.class);

    public String url;
    public Class type;

    CurrencyType(String url, Class type) {
        this.url = url;
        this.type = type;
    }
}