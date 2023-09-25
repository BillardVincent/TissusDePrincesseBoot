package fr.vbillard.tissusdeprincesseboot.model;

import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import fr.vbillard.tissusdeprincesseboot.utils.color.LabColor;
import fr.vbillard.tissusdeprincesseboot.utils.color.RGBColor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Getter
@Setter
@Entity
public class ColorEntity extends AbstractEntity {

    private int red;
    private int green;
    private int blue;

    private float l;
    private float a;
    private float b;

    /*
    @PrePersist
    @PreUpdate
    public void setCIELab(){
         LabColor lab = ColorUtils.rgbToLab(new RGBColor(red, green, blue));
        l = lab.getL();
        a = lab.getA();
        b = lab.getB();
    }

     */
}
