package fr.vbillard.tissusdeprincesse.controller;

import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
class FxUtilsTest {

    @Test
    void quantiteAsString(){
        Quantite q = new Quantite();
        q.setQuantite(123.123f);
        q.setUnite(Unite.CM);
        String result = FxUtils.setTextFromQuantity(q);
        assertEquals("123,12 cm", result);
    }

    @Test
    void quantiteEndingWith0AsString(){
        Quantite q = new Quantite();
        q.setQuantite(123.100f);
        q.setUnite(Unite.CM);
        String result = FxUtils.setTextFromQuantity(q);
        assertEquals("123,1 cm", result);
    }

    @Test
    void quantiteEndingWithDot0AsString(){
        Quantite q = new Quantite();
        q.setQuantite(123.00f);
        q.setUnite(Unite.CM);
        String result = FxUtils.setTextFromQuantity(q);
        assertEquals("123 cm", result);
    }
}
