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
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @BeforeEach
    void initMapper(){
        new FournitureRequiseToDto().configure(modelMapper.typeMap(FournitureRequise.class, FournitureRequiseDto.class));
        new PatronToDto().configure(modelMapper.typeMap(Patron.class, PatronDto.class));
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
        f.getQuantitePrincipale().setUnite(RandomUtils.getRandomList(f.getType().getDimensionPrincipale().getUnites()));
        f.getQuantiteSecondaire().setUnite(RandomUtils.getRandomList(f.getType().getDimensionSecondaire().getUnites()));

        FournitureDto dto = modelMapper.map(f, FournitureDto.class);

        assertNotNull(dto);

        assertEquals(f.getNom(), dto.getNom());
        assertEquals(f.getId(), dto.getId());
        assertEquals(f.getRangement(), dto.getRangement());
        assertEquals(f.getColor().getBlue(), Math.round(dto.getColor().getBlue()*255));

        // TODO
    }
}
