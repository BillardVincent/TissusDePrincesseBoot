package fr.vbillard.tissusdeprincesse.testUtils;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Supplier;

public class TestContexte {

    private Fourniture fourniture;
    private TypeFourniture typeFourniture;
    private ColorEntity color;
    private Quantite quantite1;
    private Quantite quantite2;
    private Rangement rangement;
    private FournitureDto fournitureDto;

    public FournitureDto getFournitureDto(){
        return getOrBuildIfNull(fournitureDto, this::buildFournitureDto);
    }

    public Fourniture getFourniture() {
        return getOrBuildIfNull(fourniture, this::buildFourniture);
    }

    public ColorEntity getColor() {
        return getOrBuildIfNull(color, this::buildColor);
    }

    public TypeFourniture getTypeFourniture() {
        return getOrBuildIfNull(typeFourniture, this::buildTypeFourniture);
    }

    public Rangement getRangement() {
        return getOrBuildIfNull(rangement, this::buildRangement);
    }

    public Quantite getQuantite1() {
        return getOrBuildIfNull(quantite1, () -> quantite1 = buildQuantite());
    }

    public Quantite getQuantite2() {
        return getOrBuildIfNull(quantite2, () -> quantite2 = buildQuantite());
    }

    private FournitureDto buildFournitureDto(){
        fournitureDto = new FournitureDto();
        fournitureDto.setNom(RandomUtils.getRandomString(8));
        fournitureDto.setType(getTypeFourniture());
        fournitureDto.setColor(Color.rgb(RandomUtils.getRandomNumber(255),
                RandomUtils.getRandomNumber(255),RandomUtils.getRandomNumber(255)));
        fournitureDto.setUnite(RandomUtils.getRandomEnum(Unite.class));
        fournitureDto.setUniteSecondaire(RandomUtils.getRandomEnum(Unite.class));
        fournitureDto.setId(RandomUtils.getRandomNumber(100));
        fournitureDto.setArchived(RandomUtils.getRandomBoolean());
        fournitureDto.setQuantite(RandomUtils.getRandomFloat(100));
        fournitureDto.setQuantiteSec(RandomUtils.getRandomFloat(100));
        fournitureDto.setQuantiteDisponible(RandomUtils.getRandomFloat(100));
        fournitureDto.setIntituleDimension(RandomUtils.getRandomString(10));
        fournitureDto.setIntituleSecondaire(RandomUtils.getRandomString(10));
        fournitureDto.setLieuAchat(RandomUtils.getRandomString(10));
        fournitureDto.setReference(RandomUtils.getRandomString(5));
        fournitureDto.setDescription(RandomUtils.getRandomString(100));
        fournitureDto.setRangement(getRangement());
        return fournitureDto;
    }

    private TypeFourniture buildTypeFourniture() {
        typeFourniture = new TypeFourniture();
        typeFourniture.setId(RandomUtils.getRandomNumber(100));
        typeFourniture.setFournitures(List.of(getFourniture()));
        typeFourniture.setDimensionPrincipale(RandomUtils.getRandomEnum(DimensionEnum.class));
        typeFourniture.setIntitulePrincipale(RandomUtils.getRandomString(8));
        typeFourniture.setDimensionSecondaire(RandomUtils.getRandomEnum(DimensionEnum.class));
        typeFourniture.setValue(RandomUtils.getRandomString(12));
        typeFourniture.setIntituleSecondaire(RandomUtils.getRandomString(8));
        typeFourniture.setUnitePrincipaleConseillee(RandomUtils.getRandomEnum(Unite.class));
        typeFourniture.setUniteSecondaireConseillee(RandomUtils.getRandomEnum(Unite.class));

        return typeFourniture;
    }

    private Fourniture buildFourniture() {
        fourniture = new Fourniture();
        fourniture.setId(RandomUtils.getRandomNumber(100));
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

        return fourniture;
    }

    private Rangement buildRangement() {
        rangement = new Rangement();
        rangement.setNom(RandomUtils.getRandomString(8));
        rangement.setId(RandomUtils.getRandomNumber(100));

        return rangement;
    }

    private Quantite buildQuantite() {
        Quantite q = new Quantite();
        q.setId(RandomUtils.getRandomNumber(100));
        q.setQuantite(RandomUtils.getRandomFloat(10));
        q.setUnite(RandomUtils.getRandomEnum(Unite.class));
        return q;
    }

    private ColorEntity buildColor() {
        color = new ColorEntity();
        color.setId(RandomUtils.getRandomNumber(100));
        color.setBlue(RandomUtils.getRandomNumber(255));
        color.setRed(RandomUtils.getRandomNumber(255));
        color.setGreen(RandomUtils.getRandomNumber(255));
        color.setA(RandomUtils.getRandomDouble(1));
        color.setB(RandomUtils.getRandomDouble(1));
        color.setL(RandomUtils.getRandomDouble(1));

        return color;
    }

    private <T> T getOrBuildIfNull(T object, Supplier<T> supplier) {
        if (object == null) {
            return supplier.get();
        }
        return object;
    }
}
