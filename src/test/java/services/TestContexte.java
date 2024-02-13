package services;

import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.test.context.TestContext;

import java.util.List;

public class TestContexte {
    
    private Fourniture fourniture;
    private TypeFourniture typeFourniture;
    private ColorEntity color;
    private Quantite quantite1;
    private Quantite quantite2;
    private Rangement rangement;


    public Fourniture getFourniture() {
        if (fourniture == null){
            buildFourniture();
        }
        return fourniture;
    }

    public ColorEntity getColor(){
        if (color == null){
            buildColor();
        }
        return color;
    }

    public TypeFourniture getTypeFourniture(){
        if (typeFourniture == null){
            buildTypeFourniture();
        }
        return typeFourniture;
    }

    private void buildTypeFourniture() {
        typeFourniture = new TypeFourniture();
        typeFourniture.setId(RandomUtils.getRandomNumber(4));
        typeFourniture.setFournitures(List.of(getFourniture()));
        typeFourniture.setDimensionPrincipale(RandomUtils.getRandomEnum(DimensionEnum.class));
        typeFourniture.setIntitulePrincipale(RandomUtils.getRandomString(8));
        typeFourniture.setDimensionSecondaire(RandomUtils.getRandomEnum(DimensionEnum.class));
        typeFourniture.setValue(RandomUtils.getRandomString(12));
        typeFourniture.setIntituleSecondaire(RandomUtils.getRandomString(8));
        typeFourniture.setUnitePrincipaleConseillee(RandomUtils.getRandomEnum(Unite.class));
        typeFourniture.setUniteSecondaireConseillee(RandomUtils.getRandomEnum(Unite.class));
    }

    private void buildFourniture(){
        fourniture = new Fourniture();
        fourniture.setId(RandomUtils.getRandomNumber(4));
        fourniture.setNom(RandomUtils.getRandomString(8));
        fourniture.setColor(getColor());
        fourniture.setType(getTypeFourniture());
        fourniture.setNom(RandomUtils.getRandomString(12));
        fourniture.setDescription(RandomUtils.getRandomString(100));
        fourniture.setArchived(RandomUtils.getRandomBoolean());
        fourniture.setQuantitePrincipale(getQuantite1());
        fourniture.setQuantiteDisponible(RandomUtils.getRandomFloat(4));
        fourniture.setQuantiteSecondaire(getQuantite2());
        fourniture.setLieuAchat(RandomUtils.getRandomString(10));
        fourniture.setReference(RandomUtils.getRandomString(5));
        fourniture.setRangement(getRangement());
    }

    public Rangement getRangement(){
        if (rangement == null){
            buildRangement();
        }
        return rangement;
    }

    public Quantite getQuantite1() {
        if (quantite1 == null){
            quantite1 = buildQuantite();
        }
        return quantite1;
    }

    public Quantite getQuantite2() {
        if (quantite2 == null){
            quantite2 = buildQuantite();
        }
        return quantite2;
    }

    private void buildRangement(){
        rangement = new Rangement();
        rangement.setNom(RandomUtils.getRandomString(8));
        rangement.setId(RandomUtils.getRandomNumber(4));
    }

    private Quantite buildQuantite() {
        Quantite q = new Quantite();
        q.setId(RandomUtils.getRandomNumber(4));
        q.setQuantite(RandomUtils.getRandomFloat(10));
        q.setUnite(RandomUtils.getRandomEnum(Unite.class));
        return q;
    }

    private void buildColor(){
        color = new ColorEntity();
        color.setId(RandomUtils.getRandomNumber(4));
        color.setBlue(RandomUtils.getRandomNumber(2));
        color.setRed(RandomUtils.getRandomNumber(2));
        color.setGreen(RandomUtils.getRandomNumber(2));
        color.setA(RandomUtils.getRandomDouble(2));
        color.setB(RandomUtils.getRandomDouble(2));
        color.setL(RandomUtils.getRandomDouble(2));
    }
}
