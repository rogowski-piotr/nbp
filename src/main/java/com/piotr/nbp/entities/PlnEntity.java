package com.piotr.nbp.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "pln")
public class PlnEntity {

    @Id
    @Column(name = "datetime")
    Date datetime;

    @Column(name = "bid")
    Double bid;

    @Column(name = "ask")
    Double ask;
}