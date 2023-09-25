package fr.vbillard.tissusdeprincesseboot.utils.color;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
/**
 * stock canaux de couleurs entre 0 et 1
 */
public class RGBColor {
    private double red;
    private double green;
    private double blue;
}
