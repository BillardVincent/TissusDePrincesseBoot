package fr.vbillard.tissusdeprincesse.services;

import fr.vbillard.tissusdeprincesse.testUtils.TestContexte;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissuDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.TissuSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class TissuServiceTest {

    @Mock
    TissuDao tissuDao;

    @Mock
    MapperService mapperService;

    @Mock
    ImageService imageService;

    static TestContexte testContexte;

    @BeforeEach
    void initTests(){
        testContexte = new TestContexte();
    }

    @InjectMocks
    TissuService tissuService;

    @Test
    void existsByReference(){
        when(tissuDao.existsByReference(anyString())).thenReturn(true);
        boolean result = tissuService.existByReference("value");
        assertTrue(result);
    }

     @Test
    void delete(){
        when(tissuDao.findById(1)).thenReturn(Optional.of(new Tissu()));
        tissuService.delete(1);
         verify(tissuDao, times(1)).findById(1);
         verify(tissuDao, times(1)).delete(any(Tissu.class));
     }


     @Test
     void testDelete(){
         TissuDto dto = testContexte.getTissuDto();
         when(tissuDao.findById(dto.getId())).thenReturn(Optional.of(testContexte.getTissu()));
         when(mapperService.map(dto)).thenReturn(testContexte.getTissu());
         tissuService.delete(dto);
         verify(tissuDao, times(1)).delete(argThat(e -> e.getId() == testContexte.getTissu().getId()));
     }

     @Test
    void  testGetObservablePage(){
        when(tissuDao.findAllByArchived(PageRequest.of(1, 1),false)).thenReturn(new PageImpl<>(List.of(testContexte.getTissu())));
        ObservableList<TissuDto> result = tissuService.getObservablePage(1, 1);
        assertEquals(1, result.size());
     }

    @Test
    void  testGetObservablePageSpec(){
        TissuSpecification spec = new TissuSpecification();
        when(tissuDao.findAll(spec, PageRequest.of(1, 1))).thenReturn(new PageImpl<>(List.of(testContexte.getTissu())));
        List<TissuDto> result = tissuService.getObservablePage( 1, 1, spec);
        assertEquals(1, result.size());
    }

     @Test
    void testGetQuantiteUtiliseeOK(){
         when(tissuDao.longueurUtilisee(1)).thenReturn(10);
         int result = tissuService.getLongueurUtilisee(1);
         assertEquals(10, result);
     }

    @Test
    void testGetQuantiteUtiliseeNull(){
        when(tissuDao.longueurUtilisee(1)).thenReturn(null);
        int result = tissuService.getLongueurUtilisee(1);
        assertEquals(0, result);
    }

    @Test
    void testCountByRangementId() {
        when(tissuDao.countByRangementIdAndArchived(1, false)).thenReturn(20);
        assertEquals(20, tissuService.countByRangementId(1));
    }

    @Test
    void  testAddRangement(){
        when(tissuDao.findById(1)).thenReturn(Optional.of(testContexte.getTissu()));
        when(tissuDao.save(any())).thenReturn(testContexte.getTissu());
        tissuService.addRangement(1, testContexte.getRangement());
        verify(tissuDao, times(1)).save(argThat(f -> f.getRangement().equals(testContexte.getRangement())));
    }

    @Test
    void testSaveOrUpdateByDto(){
        TissuDto dto = testContexte.getTissuDto();
        Tissu entity = testContexte.getTissu();
        when(mapperService.map(any(TissuDto.class))).thenReturn(entity);
        when(mapperService.map(any(Tissu.class))).thenReturn(dto);
        when(tissuDao.save(entity)).thenReturn(entity);
        TissuDto result = tissuService.saveOrUpdate(dto);
        verify(tissuDao, times(1)).save(testContexte.getTissu());
        assertEquals(dto, result);
    }

    @Test
    void beforeSaveOrUpdateNoConversion(){
        Tissu entity = testContexte.getTissu();
        entity.setUnitePoids(UnitePoids.GRAMME_M_CARRE);
        ArgumentCaptor<Tissu> captor = ArgumentCaptor.forClass(Tissu.class);
        when(tissuDao.save(captor.capture())).thenReturn(entity);
        tissuService.saveOrUpdate(entity);

        Tissu result = captor.getValue();
        assertEquals(entity.getUnitePoids(),result.getUnitePoids());
        assertEquals(entity.getPoids(),result.getPoids());
    }

    @Test
    void beforeSaveOrUpdateWithConversion(){
        Tissu entity = testContexte.getTissu();
        entity.setUnitePoids(UnitePoids.GRAMME_M);
        entity.setLaize(50);
        entity.setPoids(10);
        ArgumentCaptor<Tissu> captor = ArgumentCaptor.forClass(Tissu.class);
        when(tissuDao.save(captor.capture())).thenReturn(entity);
        tissuService.saveOrUpdate(entity);

        Tissu result = captor.getValue();
        assertEquals(UnitePoids.GRAMME_M_CARRE,result.getUnitePoids());
        assertEquals(20,result.getPoids());
    }

}
