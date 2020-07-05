package com.piotr.nbp.request;

import com.piotr.nbp.enums.CurrencyEnum;

import java.io.IOException;
import java.util.Date;

public interface NbpRequest {

    String request(CurrencyEnum currency, Date date) throws IOException;
    
}
