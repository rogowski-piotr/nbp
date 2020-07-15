package com.piotr.nbp.entities;

import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "eur")
public class EurEntity implements Currency {

    @Id
    @Column(name = "datetime")
    Date datetime;

    @Column(name = "bid")
    Double bid;

    @Column(name = "ask")
    Double ask;

    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate= format.format(datetime);
        return stringDate + "\t\task: " + ask + "\t\tbid: " + bid;
    }
}
