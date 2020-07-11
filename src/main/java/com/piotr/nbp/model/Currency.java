package com.piotr.nbp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Currency {

    Date datetime;

    Double bid;

    Double ask;

    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return "datetime: " + format.format(this.datetime) + ", bid: " + bid + ", ask: " + ask;
    }

}
