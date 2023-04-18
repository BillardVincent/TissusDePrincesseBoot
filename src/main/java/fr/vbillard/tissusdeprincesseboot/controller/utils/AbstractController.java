package fr.vbillard.tissusdeprincesseboot.controller.utils;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;

@Component
public class AbstractController {
    FxData data;
    StageInitializer initializer;

    AbstractController(StageInitializer initializer){
        this.data =initializer.getData();
        this.initializer = initializer;
    }
}
