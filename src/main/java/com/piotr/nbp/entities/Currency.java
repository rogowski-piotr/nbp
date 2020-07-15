package com.piotr.nbp.entities;

import java.util.Date;

public interface Currency {
    Date getDatetime();
    Double getBid();
    Double getAsk();
}
