package services;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils.entityToColor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
         FournitureDto dto = new FournitureDto();
         dto.setNom("Nom");

         TypeFourniture typeFourniture = new TypeFourniture();
         typeFourniture.setDimensionPrincipale(DimensionEnum.LONGUEUR);
         typeFourniture.setIntitulePrincipale("intitulé1");
         typeFourniture.setDimensionPrincipale(DimensionEnum.NOMBRE);
         typeFourniture.setIntitulePrincipale("intitulé2");
         dto.setType(typeFourniture);
         dto.setColor(Color.AQUAMARINE);

         Fourniture result = fournitureService.convert(dto);

         assertEquals(dto.getNom(), result.getNom());
         assertEquals(dto.getType(), result.getType());
         assertEquals(dto.getColor().getBlue(), entityToColor(result.getColor()).getBlue());
     }

}
