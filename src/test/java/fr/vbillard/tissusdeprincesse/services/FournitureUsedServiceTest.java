package fr.vbillard.tissusdeprincesse.services;

import fr.vbillard.tissusdeprincesse.testUtils.TestContexte;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureUsedDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class FournitureUsedServiceTest {

    @Mock
    FournitureUsedDao fournitureUsedDao;

    @Mock
    MapperService mapperService;

    static TestContexte testContexte;

    @BeforeEach
    void initTests(){
        testContexte = new TestContexte();
    }

    @InjectMocks
    FournitureUsedService fournitureUsedService;


    @Test
    void getAllByFournitureTest(){
        when(fournitureUsedDao.getAllByFourniture(testContexte.getFourniture())).thenReturn(List.of(testContexte.getFournitureUsed()));
        List<FournitureUsed> result = fournitureUsedService.getByFourniture(testContexte.getFourniture());
        verify(fournitureUsedDao, times(1)).getAllByFourniture(any());
        assertEquals(1, result.size());
        assertEquals(testContexte.getFournitureUsed(), result.get(0));
    }

    @Test
    void getAllByProjet(){
        when(fournitureUsedDao.getAllByProjet(testContexte.getProjet())).thenReturn(List.of(testContexte.getFournitureUsed()));
        List<FournitureUsed> result = fournitureUsedService.getByProjet(testContexte.getProjet());
        verify(fournitureUsedDao, times(1)).getAllByProjet(any());
        assertEquals(1, result.size());
        assertEquals(testContexte.getFournitureUsed(), result.get(0));

    }

    @Test
    void getFournitureUsedByFournitureRequiseAndProjetTest(){
        // TODO
        List<FournitureUsed> result = fournitureUsedService.getFournitureUsedByFournitureRequiseAndProjet(testContexte.getFournitureRequiseDto(), testContexte.getProjetDto());
    }

}
