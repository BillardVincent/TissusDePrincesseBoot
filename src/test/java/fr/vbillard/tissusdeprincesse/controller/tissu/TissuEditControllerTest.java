package fr.vbillard.tissusdeprincesse.controller.tissu;

import fr.vbillard.tissusdeprincesse.controller.ControllerTest;
import fr.vbillard.tissusdeprincesse.testUtils.TestContexte;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.picture_helper.TissuPictureHelper;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuEditController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.stream.Stream;

import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomBoolean;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomNumber;
import static fr.vbillard.tissusdeprincesse.testUtils.RandomUtils.getRandomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class TissuEditControllerTest extends ControllerTest {

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

    @Mock
    StageInitializer initializer;

    @Mock
    RootController rootController;

    private TissuDto generateSource(){
        TestContexte context = new TestContexte();
        return context.getTissuDto();
    }
    @Test
    void initTestOK(){
        when(matiereService.getAllMatieresValues()).thenReturn(FXCollections.observableArrayList(new ArrayList<>()));

        TissuDto source = generateSource();
        initController(source);

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
        assertEquals(source.getColor(), controller.colorComp.getColor());

    }

    private void initController(TissuDto source) {
        initJFX_FXML(controller);
        controller.initialize();
        FxData data = new FxData();
        data.setTissu(source);
        controller.setStageInitializer(stageInitializer, data);
    }

    @Test
    void editAndSaveOK(){
        TissuDto source = generateSource();

        when(matiereService.getAllMatieresValues()).thenReturn(FXCollections.observableArrayList(new ArrayList<>()));
        when(tissuService.saveOrUpdate(any(TissuDto.class))).thenReturn(source);

        initController(source);

        String fakeString = getRandomString(10);
        String fakeIntValue = String.valueOf(getRandomNumber(50));
        boolean fakeBoolean = getRandomBoolean();

        controller.referenceField.setText(fakeString);
        controller.longueurField.setText(fakeIntValue);
        controller.laizeField.setText(fakeIntValue);
        controller.descriptionField.setText(fakeString);
        controller.matiereField.setValue(fakeString);
        controller.typeField.setValue(fakeString);
        controller.poidsField.setText(fakeIntValue);
        controller.lieuDachatField.setText(fakeString);
        controller.decatiField.setSelected(fakeBoolean);
        controller.unitePoidsField.setValue(fakeString);
        controller.tissageField.setValue(fakeString);
        controller.chuteField.setSelected(fakeBoolean);

        controller.handleOk();
        verify(tissuService, times(1)).saveOrUpdate(any(TissuDto.class));

        assertEquals(fakeString, source.getReference());
        assertEquals(fakeIntValue, String.valueOf(source.getLongueur()));
        assertEquals(fakeIntValue,  String.valueOf(source.getLaize()));
        assertEquals(fakeString, source.getDescription());
        assertEquals(fakeString, source.getMatiere());
        assertEquals(fakeString, source.getTypeTissu());
        assertEquals(fakeIntValue, String.valueOf(source.getPoids()));
        assertEquals(fakeString, source.getLieuAchat());
        assertEquals(fakeBoolean, source.isDecati());
        assertEquals(fakeString, source.getUnitePoids());
        assertEquals(fakeString, source.getTissage());
        assertEquals(fakeBoolean, source.isChute());

    }

    @ParameterizedTest
    @MethodSource("handleReferenceParameters")
    void handleReference(String type, String matiere, String tissage, boolean chute, Boolean[] existsAfterFirst, String expected){

        if (existsAfterFirst == null) {
            when(tissuService.existByReference(any())).thenReturn(false);
        } else {
            when(tissuService.existByReference(any())).thenReturn(true, existsAfterFirst);
        }

        initController(generateSource());

        controller.typeField.setValue(type);
        controller.matiereField.setValue(matiere);
        controller.tissageField.setValue(tissage);
        controller.chuteField.setSelected(chute);

        controller.handleGenerateReference();

        assertEquals(expected, controller.referenceField.getText());

    }

    private static Stream<Arguments> handleReferenceParameters() {
        return Stream.of(
                Arguments.of("aze", "qsd", "wxc", false, null, "AQW-1"),
                Arguments.of("aze", "qsd", "wxc", true, new Boolean[]{true, false}, "AQW-cp-3"));

    }

    @Test
    void handleCancel(){
        TissuDto dto = generateSource();
        initController(dto);
        when(tissuService.getDtoById(dto.getId())).thenReturn(dto);
        controller.handleCancel();
        verify(rootController, times(1)).displayTissusDetails(dto);
    }

    @Test
    void handleCancelIdNull(){
        TissuDto dto = generateSource();
        dto.setId(0);
        initController(dto);
        when(tissuService.getDtoById(dto.getId())).thenReturn(dto);
        controller.handleCancel();
        verify(rootController, times(1)).displayTissus();
    }

    @Test
    void addPicture(){
        TissuDto dto = generateSource();
        when(tissuService.saveOrUpdate(dto)).thenReturn(dto);
        initController(dto);
        controller.initialize();
        controller.addPicture();
        verify(tissuService, times(1)).saveOrUpdate(dto);
        verify(tissuPictureHelper, times(1)).addPictureLocal(dto);
    }

    @Test
    void addPictureWeb(){
        TissuDto dto = generateSource();
        when(tissuService.saveOrUpdate(dto)).thenReturn(dto);
        initController(dto);
        controller.initialize();
        controller.addPictureWeb();
        verify(tissuService, times(1)).saveOrUpdate(dto);
        verify(tissuPictureHelper, times(1)).addPictureWeb(dto);

    }

    @Test
    void addPictureFromClipboard(){
        TissuDto dto = generateSource();
        when(tissuService.saveOrUpdate(dto)).thenReturn(dto);
        initController(dto);
        controller.initialize();
        controller.addPictureFromClipboard();
        verify(tissuService, times(1)).saveOrUpdate(dto);
        verify(tissuPictureHelper, times(1)).addPictureClipBoard(dto);

    }


}
