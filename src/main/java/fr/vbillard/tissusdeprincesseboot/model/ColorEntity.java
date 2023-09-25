package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ColorEntity extends AbstractEntity {

    private int red;
    private int green;
    private int blue;

    private double l;
    private double a;
    private double b;
}
