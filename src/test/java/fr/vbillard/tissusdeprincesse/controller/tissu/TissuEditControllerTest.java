package fr.vbillard.tissusdeprincesse.controller.tissu;

import fr.vbillard.tissusdeprincesse.controller.ControllerTest;
import fr.vbillard.tissusdeprincesse.testUtils.TestContexte;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.TissuPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuEditController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class TissuEditControllerTest extends ControllerTest {

    @InjectMocks
    TissuEditController controller;

    @Mock
    TissuService tissuService;
    @Mock
    MatiereService matiereService;
    @Mock
    TissageService tissageService;
    @Mock
    TissuPictureHelper tissuPictureHelper;

    @Test
    void initTestOK(){
        when(matiereService.getAllMatieresValues()).thenReturn(FXCollections.observableArrayList(new ArrayList<>()));

        initJFX_FXML(controller);
        controller.initialize();
        FxData data = new FxData();
        TestContexte context = new TestContexte();
        TissuDto source = context.getTissuDto();
        data.setTissu(source);
        controller.setStageInitializer(stageInitializer, data);

        assertEquals(source.getReference(), controller.referenceField.getText());
        assertEquals(String.valueOf(source.getLongueur()), controller.longueurField.getText());
        assertEquals(String.valueOf(source.getLaize()), controller.laizeField.getText());
        assertEquals(source.getDescription(), controller.descriptionField.getText());
        assertEquals(source.getMatiere(), controller.matiereField.getValue());
        assertEquals(source.getTypeTissu(), controller.typeField.getValue());
        assertEquals(String.valueOf(source.getPoids()), controller.poidsField.getText());
        assertEquals(source.getLieuAchat(), controller.lieuDachatField.getText());
        assertEquals(source.isDecati(), controller.decatiField.isSelected());
        assertEquals(source.getUnitePoids(), controller.unitePoidsField.getValue());
        assertEquals(source.getTissage(), controller.tissageField.getValue());
        assertEquals(source.isChute(), controller.chuteField.isSelected());
        // TODO archive
        // TODO restante?
        // assertEquals(String.valueOf(source.getLongueurRestante()), controller.ancienneValeurLabel.getText());
        assertEquals(source.getColor(), controller.colorComp.getColor());

    }


    @Test
    void editAndSaveOK(){
        when(matiereService.getAllMatieresValues()).thenReturn(FXCollections.observableArrayList(new ArrayList<>()));

        initJFX_FXML(controller);
        controller.initialize();
        FxData data = new FxData();
        TestContexte context = new TestContexte();
        TissuDto source = context.getTissuDto();
        data.setTissu(source);
        controller.setStageInitializer(stageInitializer, data);

        assertEquals(source.getReference(), controller.referenceField.getText());
        assertEquals(String.valueOf(source.getLongueur()), controller.longueurField.getText());
        assertEquals(String.valueOf(source.getLaize()), controller.laizeField.getText());
        assertEquals(source.getDescription(), controller.descriptionField.getText());
        assertEquals(source.getMatiere(), controller.matiereField.getValue());
        assertEquals(source.getTypeTissu(), controller.typeField.getValue());
        assertEquals(String.valueOf(source.getPoids()), controller.poidsField.getText());
        assertEquals(source.getLieuAchat(), controller.lieuDachatField.getText());
        assertEquals(source.isDecati(), controller.decatiField.isSelected());
        assertEquals(source.getUnitePoids(), controller.unitePoidsField.getValue());
        assertEquals(source.getTissage(), controller.tissageField.getValue());
        assertEquals(source.isChute(), controller.chuteField.isSelected());
        // TODO archive
        // TODO restante?
        // assertEquals(String.valueOf(source.getLongueurRestante()), controller.ancienneValeurLabel.getText());
        assertEquals(source.getColor(), controller.colorComp.getColor());

    }


}
