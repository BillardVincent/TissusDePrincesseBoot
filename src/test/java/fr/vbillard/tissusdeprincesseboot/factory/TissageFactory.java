package fr.vbillard.tissusdeprincesseboot.factory;

import fr.vbillard.tissusdeprincesseboot.model.Tissage;

import static fr.vbillard.tissusdeprincesseboot.utils.RandomUtils.FAKER;

public class TissageFactory {

    public static Tissage newTissage(){
        return newTissage(new FactoryContext());
    }
    public static Tissage newTissage(FactoryContext factoryContext) {
        Tissage tissage =new Tissage();
        factoryContext.setTissage(tissage);
        tissage.setId(FAKER.random().nextInt(1,100));
        tissage.setValue(FAKER.animal().name());

        return tissage;
    }
}
