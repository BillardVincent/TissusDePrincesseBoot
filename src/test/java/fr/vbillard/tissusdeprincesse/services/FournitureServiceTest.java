package fr.vbillard.tissusdeprincesse.services;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import javafx.collections.ObservableList;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import fr.vbillard.tissusdeprincesse.testUtils.TestContexte;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class FournitureServiceTest {

    @Mock
    FournitureDao fournitureDao;

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
    FournitureService fournitureService;

    @Test
    void existsByReference(){
        when(fournitureDao.existsByReference(anyString())).thenReturn(true);
        boolean result = fournitureService.existByReference("value");
        assertTrue(result);
    }

     @Test
    void delete(){
        when(fournitureDao.findById(1)).thenReturn(Optional.of(new Fourniture()));
        fournitureService.delete(1);
         verify(fournitureDao, times(1)).findById(1);
         verify(fournitureDao, times(1)).delete(any(Fourniture.class));
     }
     @Test
    void convertToEntityTest(){
        FournitureDto dto = testContexte.getFournitureDto();
         dto.setId(0);
         dto.setUnite(dto.getType().getDimensionPrincipale().getDefault());
         dto.setUniteSecondaire(dto.getType().getDimensionPrincipale().getDefault());
         Fourniture result = fournitureService.convert(dto);

         assertEquals(dto.getNom(), result.getNom());
         assertEquals(dto.getQuantite(), result.getQuantitePrincipale().getQuantite());
         assertEquals(dto.getUnite(), result.getQuantitePrincipale().getUnite().getLabel());
         assertEquals(dto.getUniteShort(), result.getQuantitePrincipale().getUnite().getAbbreviation());
         assertEquals(dto.getQuantiteSec(), result.getQuantiteSecondaire().getQuantite());
         assertEquals(dto.getUniteSecondaire(), result.getQuantiteSecondaire().getUnite().getLabel());
         assertEquals(dto.getUniteSecondaireShort(), result.getQuantitePrincipale().getUnite().getAbbreviation());
         assertEquals(dto.getLieuAchat(), result.getLieuAchat());
         assertEquals(dto.getReference(), result.getReference());
         assertEquals(dto.getDescription(), result.getDescription());
         assertEquals(dto.isArchived(), result.isArchived());
         assertEquals(dto.getType(), result.getType());
         assertEquals(dto.getRangement(), result.getRangement());
         assertEquals(dto.colorId, result.getColor().getId());
     }

     @Test
     void testDelete(){
         FournitureDto dto = testContexte.getFournitureDto();
         when(fournitureDao.findById(dto.getId())).thenReturn(Optional.of(testContexte.getFourniture()));
         fournitureService.delete(dto);
         verify(fournitureDao, times(1)).delete(argThat(e -> e.getId() == dto.getId()));
     }

     @Test
    void  testGetObservablePage(){
        when(fournitureDao.findAllByArchived(PageRequest.of(1, 1),false)).thenReturn(new PageImpl<>(List.of(testContexte.getFourniture())));
        ObservableList<FournitureDto> result = fournitureService.getObservablePage(1, 1);
        assertEquals(1, result.size());
     }

    @Test
    void  testGetObservablePageSpec(){
        FournitureSpecification spec = new FournitureSpecification();
        when(fournitureDao.findAll(spec, PageRequest.of(1, 1))).thenReturn(new PageImpl<>(List.of(testContexte.getFourniture())));
        List<FournitureDto> result = fournitureService.getObservablePage( 1, 1, spec);
        assertEquals(1, result.size());
    }

     @Test
    void testGetQuantiteUtiliseeOK(){
         when(fournitureDao.quantiteUtilisee(1)).thenReturn(10f);
         Float result = fournitureService.getQuantiteUtilisee(1);
         assertEquals(10f, result);
     }

    @Test
    void testGetQuantiteUtiliseeNull(){
        when(fournitureDao.quantiteUtilisee(1)).thenReturn(null);
        Float result = fournitureService.getQuantiteUtilisee(1);
        assertEquals(0f, result);
    }

    @Test
    void testCountByRangementId() {
        when(fournitureDao.countByRangementIdAndArchived(1, false)).thenReturn(20);
        assertEquals(20, fournitureService.countByRangementId(1));
    }

    @Test
    void  testAddRangement(){
        when(fournitureDao.findById(1)).thenReturn(Optional.of(testContexte.getFourniture()));
        when(fournitureDao.save(any())).thenReturn(testContexte.getFourniture());
        fournitureService.addRangement(1, testContexte.getRangement());
        verify(fournitureDao, times(1)).save(argThat(f -> f.getRangement().equals(testContexte.getRangement())));
    }

}
