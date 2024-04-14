package fr.vbillard.tissusdeprincesse.testUtils;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.model.ColorEntity;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import fr.vbillard.tissusdeprincesseboot.model.enums.SupportTypeEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import javafx.scene.paint.Color;

import java.util.HashMap;
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
    private Tissu tissu;
    private Matiere matiere;
    private Tissage tissage;
    private TypeFourniture typeFourniture;
    private ColorEntity colorEntity;
    private Quantite quantite1;
    private Quantite quantite2;
    private Rangement rangement;
    private FournitureDto fournitureDto;
    private FournitureRequise fournitureRequise;
    private FournitureRequiseDto fournitureRequiseDto;
    private PatronVersion patronVersion;
    private Patron patron;
    private TissuDto tissuDto;
    private Color color;
    private UserPref userPref;
    private FournitureUsed fournitureUsed;
    private Projet projet;
    private ProjetDto projetDto;
    private PatronVersionDto patronVersionDto;

    public FournitureRequiseDto getFournitureRequiseDto() {
        return getOrBuildIfNull(fournitureRequiseDto, this::buildFournitureRequiseDto);
    }

    private FournitureRequiseDto buildFournitureRequiseDto() {
        fournitureRequiseDto = new FournitureRequiseDto();
        fournitureRequiseDto.setId(getRandomNumber(10));
        fournitureRequiseDto.setUnite(getRandomList(getTypeFourniture().getDimensionPrincipale().getUnites()));
        fournitureRequiseDto.setUniteSecondaire(
                getRandomList(getTypeFourniture().getDimensionSecondaire().getUnites()));
        fournitureRequiseDto.setQuantiteSecondaireMax(getRandomFloat(10));
        fournitureRequiseDto.setQuantite(getRandomFloat(10));
        fournitureRequiseDto.setQuantiteSecondaireMin(getRandomFloat(10));
        fournitureRequiseDto.setQuantiteDisponible(getRandomFloat(10));
        return fournitureRequiseDto;
    }

    public Matiere getMatiere() {
        return getOrBuildIfNull(matiere, this::buildMatiere);
    }

    public Tissage getTissage() {
        return getOrBuildIfNull(tissage, this::buildTissage);
    }

    public Tissu getTissu() {
        return getOrBuildIfNull(tissu, this::buildTissu);
    }

    public FournitureDto getFournitureDto() {
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

    private FournitureDto buildFournitureDto() {
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

    private Tissu buildTissu() {
        tissu = new Tissu();
        tissu.setRangement(getRangement());
        tissu.setTypeTissu(getRandomEnum(TypeTissuEnum.class));
        tissu.setPoids(getRandomNumber(10));
        tissu.setColor(getColorEntity());
        tissu.setMatiere(getMatiere());
        tissu.setTissage(getTissage());
        tissu.setChute(getRandomBoolean());
        tissu.setDecati(getRandomBoolean());
        tissu.setReference(getRandomString(5));
        tissu.setArchived(getRandomBoolean());
        tissu.setPoids(getRandomNumber(100));
        tissu.setLongueurDisponible(getRandomNumber(100));
        tissu.setLongueur(getRandomNumber(100));
        tissu.setLaize(getRandomNumber(100));
        tissu.setLieuAchat(getRandomString(20));
        tissu.setUnitePoids(getRandomEnum(UnitePoids.class));
        tissu.setDescription(getRandomString(50));
        tissu.setId(10);
        return tissu;
    }

    private Matiere buildMatiere() {
        matiere = new Matiere();
        matiere.setId(getRandomNumber(10));
        matiere.setValue(getRandomString(10));
        return matiere;
    }

    private Tissage buildTissage() {
        tissage = new Tissage();
        tissage.setId(getRandomNumber(10));
        tissage.setValue(getRandomString(10));
        return tissage;
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
        fournitureRequise.setId(getRandomNumber(1000));
        fournitureRequise.setQuantite(getRandomFloat(50));
        fournitureRequise.setUniteSecondaire(getRandomList(getTypeFourniture().getDimensionPrincipale().getUnites()));
        fournitureRequise.setVersion(getPatronVersion());
        fournitureRequise.setQuantiteSecMin(getRandomFloat(50));
        fournitureRequise.setQuantiteSecMax(getRandomFloat(50));
        return fournitureRequise;
    }

    public PatronVersion getPatronVersion() {
        return getOrBuildIfNull(patronVersion, this::buildPatronVersion);

    }

    private PatronVersion buildPatronVersion() {
        patronVersion = new PatronVersion();
        patronVersion.setPatron(getPatron());
        patronVersion.setId(getRandomNumber(10));
        patronVersion.setNom(getRandomString(10));

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
        tissuDto = new TissuDto();
        tissuDto.setId(getRandomNumber(50));
        tissuDto.setDescription(getRandomString(50));
        tissuDto.setColor(getColor());
        tissuDto.setTypeTissu(getRandomString(10));
        tissuDto.setArchived(getRandomBoolean());
        tissuDto.setLongueurRestante(getRandomNumber(50));
        tissuDto.setChute(getRandomBoolean());
        tissuDto.setTissage(getRandomString(50));
        tissuDto.setMatiere(getRandomString(50));
        tissuDto.setReference(getRandomString(50));
        tissuDto.setDecati(getRandomBoolean());
        tissuDto.setUnitePoids(getRandomEnum(UnitePoids.class));

        return tissuDto;
    }

    private Color getColor() {
        return getOrBuildIfNull(color, this::buildColor);
    }

    private Color buildColor() {
        color = new Color(getRandomDouble(1), getRandomDouble(1),
                getRandomDouble(1), getRandomNumber(1));
        return color;
    }

    public UserPref getUser() {
        return getOrBuildIfNull(userPref, this::buildUserPref);
    }

    private UserPref buildUserPref() {
        userPref = new UserPref();
        userPref.setId(getRandomNumber(10));
        userPref.setMinPoidsMoyen(getRandomNumber(50));
        userPref.setMaxPoidsMoyen(userPref.getMinPoidsMoyen() * 2);
        userPref.setLongueurMargePercent(getRandomFloat(1));
        userPref.setPoidsMargePercent(getRandomFloat(1));
        return userPref;
    }

    public FournitureUsed getFournitureUsed() {
        return getOrBuildIfNull(fournitureUsed, this::buildFournitureUsed);
    }

    private FournitureUsed buildFournitureUsed() {
        fournitureUsed = new FournitureUsed();
        fournitureUsed.setId(getRandomNumber(10));
        fournitureUsed.setFourniture(getFourniture());
        fournitureUsed.setQuantite(getRandomFloat(10));
        fournitureUsed.setProjet(getProjet());
        fournitureUsed.setRequis(getFournitureRequise());
        return fournitureUsed;
    }

    public Projet getProjet() {
        return getOrBuildIfNull(projet, this::buildProjet);
    }

    private Projet buildProjet() {
        projet = new Projet();
        projet.setId(getRandomNumber(20));
        projet.setDescription(getRandomString(50));
        projet.setStatus(getRandomEnum(ProjectStatus.class));
        projet.setPatronVersion(getPatronVersion());
        return projet;
    }

    public ProjetDto getProjetDto() {
        return getOrBuildIfNull(projetDto, this::buildProjetDto);
    }

    private ProjetDto buildProjetDto() {
        projetDto = new ProjetDto();
        projetDto.setId(getRandomNumber(20));
        projetDto.setDescription(getRandomString(50));
        projetDto.setPatronVersion(getPatronVersionDto());
        projetDto.setProjectStatus(getRandomEnum(ProjectStatus.class).label);
        projetDto.setTissuUsed(new HashMap<>());
        projetDto.setFournitureUsed(new HashMap<>());
        return projetDto;
    }

    private PatronVersionDto getPatronVersionDto() {
        return getOrBuildIfNull(patronVersionDto, this::buildPatronVersionDto);
    }

    private PatronVersionDto buildPatronVersionDto() {
        patronVersionDto = new PatronVersionDto();
        patronVersionDto.setId(getRandomNumber(20));
        patronVersionDto.setNom(getRandomString(20));
        return patronVersionDto;
    }
}
