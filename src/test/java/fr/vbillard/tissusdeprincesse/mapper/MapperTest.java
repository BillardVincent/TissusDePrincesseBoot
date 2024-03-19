package fr.vbillard.tissusdeprincesse.mapper;

import fr.vbillard.tissusdeprincesse.testUtils.RandomUtils;
import fr.vbillard.tissusdeprincesse.testUtils.TestContexte;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dao.MatiereDao;
import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissageDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.mapper.from_dto.DtoToFournitureRequise;
import fr.vbillard.tissusdeprincesseboot.mapper.from_dto.DtoToPatron;
import fr.vbillard.tissusdeprincesseboot.mapper.from_dto.DtoToProjet;
import fr.vbillard.tissusdeprincesseboot.mapper.from_dto.DtoToRequis;
import fr.vbillard.tissusdeprincesseboot.mapper.from_dto.DtoToTissu;
import fr.vbillard.tissusdeprincesseboot.mapper.to_dto.FournitureRequiseToDto;
import fr.vbillard.tissusdeprincesseboot.mapper.to_dto.FournitureToDto;
import fr.vbillard.tissusdeprincesseboot.mapper.to_dto.PatronToDto;
import fr.vbillard.tissusdeprincesseboot.mapper.to_dto.ProjetToDto;
import fr.vbillard.tissusdeprincesseboot.mapper.to_dto.RequisToDto;
import fr.vbillard.tissusdeprincesseboot.mapper.to_dto.TissuToDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class MapperTest {

    @InjectMocks
    ModelMapper modelMapper;
    @Mock
    FournitureDao fournitureDao;
    @Mock
    TissuDao tissuDao;
    @Mock
    PatronVersionDao versionDao;
    @Mock
    MatiereService matiereService;
    @Mock
    TissageService tissageService;
    @Mock
    MatiereDao matiereDao;
    @Mock
    TissageDao tissageDao;

    static TestContexte contexte = new TestContexte();

    void initMapper(){
        new ProjetToDto().configure(modelMapper.typeMap(Projet.class, ProjetDto.class));
        new RequisToDto().configure(modelMapper.typeMap(TissuRequis.class, TissuRequisDto.class));
        new TissuToDto(tissuDao).configure(modelMapper.typeMap(Tissu.class, TissuDto.class));
        new DtoToFournitureRequise().configure(modelMapper.typeMap(FournitureRequiseDto.class, FournitureRequise.class));
        new DtoToPatron().configure(modelMapper.typeMap(PatronDto.class, Patron.class));
        new DtoToProjet().configure(modelMapper.typeMap(ProjetDto.class, Projet.class));
        new DtoToRequis(versionDao, matiereService, tissageService)
                .configure(modelMapper.typeMap(TissuRequisDto.class, TissuRequis.class));
        new DtoToTissu(matiereDao, tissageDao).configure(modelMapper.typeMap(TissuDto.class, Tissu.class));

    }

    @Test
    void fournitureToDtoMapperTest() {
        new FournitureToDto(fournitureDao).configure(modelMapper.typeMap(Fourniture.class, FournitureDto.class));

        Fourniture f = contexte.getFourniture();

        when(fournitureDao.quantiteUtilisee(f.getId())).thenReturn(f.getQuantitePrincipale().getQuantite() - f.getQuantiteDisponible());

        FournitureDto dto = modelMapper.map(f, FournitureDto.class);

        assertNotNull(dto);

        assertEquals(f.getId(), dto.getId());
        assertEquals(f.getReference(), dto.getReference());
        DecimalFormat df = new DecimalFormat("#.####");
        assertEquals(df.format(f.getQuantiteDisponible()), df.format(dto.getQuantiteDisponible()));
        assertEquals(f.getDescription(), dto.getDescription());
        assertEquals(f.getNom(), dto.getNom());
        assertEquals(f.getLieuAchat(), dto.getLieuAchat());
        assertEquals(f.isArchived(), dto.isArchived());
        assertEquals(f.getType().getValue(), dto.getTypeName());
        assertEquals(f.getType(), dto.getType());

        assertEquals(f.getQuantiteSecondaire().getUnite().getLabel(), dto.getUniteSecondaire());

        assertNotNull(dto.getUniteSecondaire());

        assertTrue(unitsAreFromSameDimension(f.getQuantitePrincipale().getUnite(), Unite.getEnum(dto.getUnite())));
        assertTrue(unitsAreFromSameDimension(f.getQuantiteSecondaire().getUnite(), Unite.getEnum(dto.getUniteSecondaire())));

        assertNotNull(dto.getUnite());
        assertEquals(dto.getUniteShort(), Unite.getEnum(dto.getUnite()).getAbbreviation());
        assertEquals(f.getColor().getBlue(), Math.round(dto.getColor().getBlue()*255));

        assertEquals(f.getRangement(), dto.getRangement());

    }

    @Test
    void quantiteFournitureToDtoMapperTest() {

        new FournitureToDto(fournitureDao).configure(modelMapper.typeMap(Fourniture.class, FournitureDto.class));
        Fourniture f = contexte.getFourniture();
        f.getType().setDimensionPrincipale(DimensionEnum.LONGUEUR);
        f.getQuantitePrincipale().setUnite(Unite.CM);
        f.getQuantiteSecondaire().setUnite(RandomUtils.getRandomList(f.getType().getDimensionSecondaire().getUnites()));

        f.getQuantitePrincipale().setQuantite(100f);
        when(fournitureDao.quantiteUtilisee(f.getId())).thenReturn(10f);

        FournitureDto dto = modelMapper.map(f, FournitureDto.class);

        assertEquals(90f, dto.getQuantiteDisponible());

    }

    @Test
    void fournitureRequiseToDto(){
        new FournitureRequiseToDto().configure(modelMapper.typeMap(FournitureRequise.class, FournitureRequiseDto.class));
        FournitureRequise entity = contexte.getFournitureRequise();

        FournitureRequiseDto dto = modelMapper.map(entity, FournitureRequiseDto.class);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getUnite(), dto.getUnite());
        assertEquals(entity.getQuantiteSecMax(), dto.getQuantiteSecondaireMax());
        assertEquals(entity.getQuantiteSecMin(), dto.getQuantiteSecondaireMin());
        assertEquals(entity.getQuantite(), dto.getQuantite());
        assertEquals(entity.getType(), dto.getType());
    }

    @Test
    void patronToPatronDto() {
        new PatronToDto().configure(modelMapper.typeMap(Patron.class, PatronDto.class));

        Patron entity = contexte.getPatron();
        PatronDto dto = modelMapper.map(entity, PatronDto.class);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getSupportType().label, dto.getTypeSupport());

    }


    private boolean unitsAreFromSameDimension(Unite unite1, Unite unite2){
        if (unite1 == Unite.NON_RENSEIGNE || unite2 == Unite.NON_RENSEIGNE){
            return true;
        }

        for (DimensionEnum dim : DimensionEnum.values()){
            if (dim.getUnites().contains(unite1) && dim.getUnites().contains(unite2)){
                return true;
            }
        }

        return false;
    }
}
