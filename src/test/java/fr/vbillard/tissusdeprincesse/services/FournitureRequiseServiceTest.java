package fr.vbillard.tissusdeprincesse.services;

import fr.vbillard.tissusdeprincesse.testUtils.TestContexte;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class FournitureRequiseServiceTest {

    @Mock
    FournitureRequiseDao fournitureRequiseDao;

    @Mock
    MapperService mapperService;

    @Mock
    PatronVersionDao patronVersionDao;
    @Mock
    UserPrefService userPrefService;

    static TestContexte testContexte;

    @BeforeEach
    void initTests(){
        testContexte = new TestContexte();
    }

    @InjectMocks
    FournitureRequiseService fournitureRequiseService;


     @Test
     void testDelete(){
         FournitureRequiseDto dto = testContexte.getFournitureRequiseDto();
         when(mapperService.map(dto)).thenReturn(testContexte.getFournitureRequise());
         when(fournitureRequiseDao.findById(dto.getId())).thenReturn(Optional.of(testContexte.getFournitureRequise()));
         fournitureRequiseService.delete(dto);
         verify(fournitureRequiseDao, times(1))
                 .delete(argThat(e -> e.equals(testContexte.getFournitureRequise())));
     }

     @Test
    void getAllFournitureRequiseDtoByVersionTest(){
         when(fournitureRequiseDao.getAllByVersionId(1)).thenReturn(List.of(testContexte.getFournitureRequise()));
         when(mapperService.map(testContexte.getFournitureRequise())).thenReturn(testContexte.getFournitureRequiseDto());
         List<FournitureRequiseDto> result = fournitureRequiseService.getAllFournitureRequiseDtoByVersion(1);
         verify(fournitureRequiseDao, times(1)).getAllByVersionId(1);
         assertEquals(1, result.size());
         assertEquals(testContexte.getFournitureRequiseDto(), result.get(0));
     }

     @Test
     void createNewForPatronTest(){
         when(patronVersionDao.findById(anyInt())).thenReturn(Optional.of(testContexte.getPatronVersion()));
         when(fournitureRequiseDao.findById(anyInt())).thenReturn(
                 Optional.ofNullable(testContexte.getFournitureRequise()));
         when(fournitureRequiseDao.save(any())).thenReturn(testContexte.getFournitureRequise());
         fournitureRequiseService.createNewForPatron(1);

         verify(fournitureRequiseDao, times(1))
                 .save(argThat(e -> e.getVersion().equals(testContexte.getPatronVersion())));
     }

     @Test
    void testDuplicate(){
         ArgumentCaptor<FournitureRequise> captor = ArgumentCaptor.forClass(FournitureRequise.class);

         when(fournitureRequiseDao.findById(1)).thenReturn(Optional.of(testContexte.getFournitureRequise()));
         when(fournitureRequiseDao.save(captor.capture())).thenReturn(testContexte.getFournitureRequise());
         FournitureRequise result = fournitureRequiseService.duplicate(1, testContexte.getPatronVersion());
         FournitureRequise copy = captor.getValue();

         assertEquals(0, copy.getId());
         assertNotEquals(copy.getId(), testContexte.getFournitureRequise().getId());
         assertEquals(testContexte.getFournitureRequise().getDetails(), copy.getDetails());

     }

     @Test
    void getFournitureSpecificationTest(){
         when(mapperService.map(any(FournitureRequiseDto.class))).thenReturn(testContexte.getFournitureRequise());
         when(userPrefService.getUser()).thenReturn(testContexte.getUser());

         FournitureSpecification specification = fournitureRequiseService.getFournitureSpecification(testContexte.getFournitureRequiseDto());

         assertNotNull(specification);
         assertEquals(testContexte.getFournitureRequise().getType(), specification.getType().get(0));
     }
}
