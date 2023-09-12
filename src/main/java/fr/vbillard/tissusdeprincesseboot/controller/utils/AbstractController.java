package fr.vbillard.tissusdeprincesseboot.controller.utils;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import org.springframework.stereotype.Component;

@Component
public class AbstractController {
    FxData data;
    StageInitializer initializer;

    AbstractController(StageInitializer initializer){
        this.data =initializer.getData();
        this.initializer = initializer;
    }
}
