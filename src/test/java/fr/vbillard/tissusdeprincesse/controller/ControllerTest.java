package fr.vbillard.tissusdeprincesse.controller;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.tissu.TissuEditController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static fr.vbillard.tissusdeprincesse.testUtils.JavaFxTestUtil.initJavaFx;

public abstract class ControllerTest {

    @Mock
    protected StageInitializer stageInitializer;

    protected void initJFX_FXML(TissuEditController controller) {
        initJavaFx();
        for (Field f : controller.getClass().getDeclaredFields()){
            if (f.isAnnotationPresent(FXML.class)){
                try {
                    Object fxElement = f.getType().getDeclaredConstructor().newInstance();
                    f.set(controller, fxElement);
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
