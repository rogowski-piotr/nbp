package com.piotr.nbp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "rub")
public class RubEntity {

    @Id
    @Column(name = "datetime")
    Date datetime;

    @Column(name = "bid")
    Double bid;

    @Column(name = "ask")
    Double ask;
}
