package com.piotr.nbp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "eur")
public class EurEntity {

    @Id
    @Column(name = "datetime")
    Date datetime;

    @Column(name = "bid")
    Double bid;

    @Column(name = "ask")
    Double ask;
}
