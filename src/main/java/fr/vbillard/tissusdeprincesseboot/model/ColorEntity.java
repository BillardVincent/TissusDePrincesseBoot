package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.service.CIELab;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
public class ColorEntity extends AbstractEntity {

    private float red;
    private float green;
    private float blue;

    private float l;
    private float a;
    private float b;

    @PrePersist
    @PreUpdate
    public void setCIELab(){
        CIELab cieLab = CIELab.getInstance();
        float[] lab = cieLab.fromRGB(new float[]{red, green, blue});
        l = lab[0];
        a = lab[1];
        b = lab[2];
    }

    public void setComparable(float[] comparableRgb){
        CIELab cieLab = CIELab.getInstance();
        float[] lab = cieLab.fromRGB(comparableRgb);
        lCompare = lab[0];
        aCompare = lab[1];
        bCompare = lab[2];
    }

    @Transient
    private float lCompare;
    @Transient
    private float aCompare;
    @Transient
    private float bCompare;

    @Formula("Math.sqrt((L * L) + (A * A) + (B * B))")
    float distance;
}
