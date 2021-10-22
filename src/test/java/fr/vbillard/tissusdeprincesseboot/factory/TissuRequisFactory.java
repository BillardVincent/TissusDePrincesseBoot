package fr.vbillard.tissusdeprincesseboot.factory;

import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.enums.GammePoids;
import fr.vbillard.tissusdeprincesseboot.utils.RandomUtils;

import static fr.vbillard.tissusdeprincesseboot.utils.RandomUtils.FAKER;

public class TissuRequisFactory {
    public static TissuRequis newTissuRequis() {
        return newTissuRequis(new FactoryContext());
    }

    public static TissuRequis newTissuRequis(FactoryContext factoryContext) {
        TissuRequis tissuRequis = new TissuRequis();
        factoryContext.setTissuRequis(tissuRequis);

        tissuRequis.setId(FAKER.random().nextInt(1,100));
        tissuRequis.setPatron(factoryContext.getPatron());
        tissuRequis.setGammePoids(RandomUtils.randomEnum(GammePoids.class));
        tissuRequis.setLaize(FAKER.random().nextInt(50,1000));
        tissuRequis.setLongueur(FAKER.random().nextInt(50,1000));

        return tissuRequis;
    }
}
