package fr.vbillard.tissusdeprincesse.testUtils;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Supplier;

import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomBoolean;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomDouble;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomEnum;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomFloat;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomList;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomNumber;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomString;

public class TestContexte {

    private Fourniture fourniture;
    private TypeFourniture typeFourniture;
    private ColorEntity colorEntity;
    private Quantite quantite1;
    private Quantite quantite2;
    private Rangement rangement;
    private FournitureDto fournitureDto;
    private FournitureRequise fournitureRequise;
    private PatronVersion patronVersion;
    private Patron patron;
    private TissuDto tissuDto;
    private Color color;
    public FournitureDto getFournitureDto(){
        return getOrBuildIfNull(fournitureDto, this::buildFournitureDto);
    }

    public Fourniture getFourniture() {
        return getOrBuildIfNull(fourniture, this::buildFourniture);
    }

    public ColorEntity getColorEntity() {
        return getOrBuildIfNull(colorEntity, this::buildColorEntity);
    }

    public TypeFourniture getTypeFourniture() {
        return getOrBuildIfNull(typeFourniture, this::buildTypeFourniture);
    }

    public Rangement getRangement() {
        return getOrBuildIfNull(rangement, this::buildRangement);
    }

    public Quantite getQuantite1() {
        return getOrBuildIfNull(quantite1, () -> {
            quantite1 = buildQuantite();
            DimensionEnum d = getTypeFourniture().getDimensionPrincipale();
            quantite1.setUnite(getRandomList(d.getUnites()));
            return quantite1;
        });
    }

    public Quantite getQuantite2() {
        return getOrBuildIfNull(quantite2, () -> {
            quantite2 = buildQuantite();
            quantite2.setUnite((getRandomList(getTypeFourniture().getDimensionSecondaire().getUnites())));
            return quantite2;
        });
    }

    private FournitureDto buildFournitureDto(){
        fournitureDto = new FournitureDto();
        fournitureDto.setNom(getRandomString(8));
        fournitureDto.setType(getTypeFourniture());
        fournitureDto.setColor(Color.rgb(getRandomNumber(255),
                getRandomNumber(255), getRandomNumber(255)));
        fournitureDto.setUnite(getRandomList(getTypeFourniture().getDimensionPrincipale().getUnites()));
        fournitureDto.setUniteSecondaire(getRandomList(getTypeFourniture().getDimensionSecondaire().getUnites()));
        fournitureDto.setId(getRandomNumber(100));
        fournitureDto.setArchived(getRandomBoolean());
        fournitureDto.setQuantite(getRandomFloat(100));
        fournitureDto.setQuantiteSec(getRandomFloat(100));
        fournitureDto.setQuantiteDisponible(getRandomFloat(100));
        fournitureDto.setIntituleDimension(getRandomString(10));
        fournitureDto.setIntituleSecondaire(getRandomString(10));
        fournitureDto.setLieuAchat(getRandomString(10));
        fournitureDto.setReference(getRandomString(5));
        fournitureDto.setDescription(getRandomString(100));
        fournitureDto.setRangement(getRangement());
        return fournitureDto;
    }

    private TypeFourniture buildTypeFourniture() {
        typeFourniture = new TypeFourniture();
        typeFourniture.setId(getRandomNumber(100));
        typeFourniture.setDimensionPrincipale(getRandomEnum(DimensionEnum.class));
        typeFourniture.setIntitulePrincipale(getRandomString(8));
        typeFourniture.setDimensionSecondaire(getRandomEnum(DimensionEnum.class));
        typeFourniture.setValue(getRandomString(12));
        typeFourniture.setIntituleSecondaire(getRandomString(8));
        typeFourniture.setUnitePrincipaleConseillee(getRandomEnum(Unite.class));
        typeFourniture.setUniteSecondaireConseillee(getRandomEnum(Unite.class));

        return typeFourniture;
    }

    private Fourniture buildFourniture() {
        fourniture = new Fourniture();
        fourniture.setId(getRandomNumber(100));
        fourniture.setNom(getRandomString(8));
        fourniture.setColor(getColorEntity());
        fourniture.setType(getTypeFourniture());
        fourniture.setNom(getRandomString(12));
        fourniture.setDescription(getRandomString(100));
        fourniture.setArchived(getRandomBoolean());
        fourniture.setQuantitePrincipale(getQuantite1());
        fourniture.setQuantiteDisponible(getRandomFloat(4));
        fourniture.setQuantiteSecondaire(getQuantite2());
        fourniture.setLieuAchat(getRandomString(10));
        fourniture.setReference(getRandomString(5));
        fourniture.setRangement(getRangement());

        return fourniture;
    }

    private Rangement buildRangement() {
        rangement = new Rangement();
        rangement.setNom(getRandomString(8));
        rangement.setId(getRandomNumber(100));

        return rangement;
    }

    private Quantite buildQuantite() {
        Quantite q = new Quantite();
        q.setId(getRandomNumber(100));
        q.setQuantite(getRandomFloat(10));
        return q;
    }

    private ColorEntity buildColorEntity() {
        colorEntity = new ColorEntity();
        colorEntity.setId(getRandomNumber(100));
        colorEntity.setBlue(getRandomNumber(255));
        colorEntity.setRed(getRandomNumber(255));
        colorEntity.setGreen(getRandomNumber(255));
        colorEntity.setA(getRandomDouble(1));
        colorEntity.setB(getRandomDouble(1));
        colorEntity.setL(getRandomDouble(1));

        return colorEntity;
    }

    private <T> T getOrBuildIfNull(T object, Supplier<T> supplier) {
        if (object == null) {
            return supplier.get();
        }
        return object;
    }

    public FournitureRequise getFournitureRequise() {
        return getOrBuildIfNull(fournitureRequise, this::buildFournitureRequise);
    }

    private FournitureRequise buildFournitureRequise() {
        fournitureRequise = new FournitureRequise();
        fournitureRequise.setType(getTypeFourniture());
        fournitureRequise.setUnite(getRandomList(getTypeFourniture().getDimensionPrincipale().getUnites()));
        fournitureRequise.setDetails(getRandomString(50));
        fournitureRequise.setId(getRandomNumber(10));
        fournitureRequise.setQuantite(getRandomFloat(50));
        fournitureRequise.setUniteSecondaire(getRandomList(getTypeFourniture().getDimensionPrincipale().getUnites()));
        fournitureRequise.setVersion(getPatronVersion());
        fournitureRequise.setQuantiteSecMin(getRandomFloat(50));
        fournitureRequise.setQuantiteSecMax(getRandomFloat(50));
        return fournitureRequise;
    }

    private PatronVersion getPatronVersion() {
        return getOrBuildIfNull(patronVersion, this::buildPatronVersion);

    }

    private PatronVersion buildPatronVersion() {
        patronVersion = new PatronVersion();
        // TODO

        return patronVersion;
    }

    public Patron getPatron() {
        return getOrBuildIfNull(patron, this::buildPatron);
    }

    private Patron buildPatron() {
        patron = new Patron();
        patron.setId(getRandomNumber(50));
        patron.setRangement(getRangement());
        patron.setDescription(getRandomString(10));
        patron.setArchived(getRandomBoolean());
        patron.setMarque(getRandomString(10));
        patron.setModele(getRandomString(10));
        patron.setSupportType(getRandomEnum(SupportTypeEnum.class));
        patron.setReference(getRandomString(5));
        patron.setVersions(List.of(getPatronVersion()));

        return patron;
    }

    public TissuDto getTissuDto() {
        return getOrBuildIfNull(tissuDto, this::buildTissuDto);
    }

    private TissuDto buildTissuDto() {
        TissuDto dto = new TissuDto();
        dto.setId(getRandomNumber(50));
        dto.setDescription(getRandomString(50));
        dto.setColor(getColor());
        dto.setTypeTissu(getRandomString(10));
        dto.setArchived(getRandomBoolean());
        dto.setLongueurRestante(getRandomNumber(50));
        dto.setChute(getRandomBoolean());
        dto.setTissage(getRandomString(50));
        dto.setMatiere(getRandomString(50));
        dto.setReference(getRandomString(50));
        dto.setDecati(getRandomBoolean());
        dto.setUnitePoids(getRandomEnum(UnitePoids.class));

        return dto;
    }

    private Color getColor() {
        return getOrBuildIfNull(color, this::buildColor);
    }

    private Color buildColor() {
        color = new Color(getRandomDouble(1), getRandomDouble(1),
                getRandomDouble(1), getRandomNumber(1));
        return color;
    }

}
