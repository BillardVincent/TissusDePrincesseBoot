package fr.vbillard.tissusdeprincesseboot.controlers_v2;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;

@Component
public class AbstractController {
    FxData data;
    StageInitializer initializer;

    AbstractController(StageInitializer initializer){
        this.data =initializer.getData();
        this.initializer = initializer;
    }
}
