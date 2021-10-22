package fr.vbillard.tissusdeprincesseboot.factory;

import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;

import static fr.vbillard.tissusdeprincesseboot.utils.RandomUtils.FAKER;

public class TypeTissuFactory {

    public static TypeTissu newType() {
        return newType(new FactoryContext());
    }

    public static TypeTissu newType(FactoryContext factoryContext) {
        TypeTissu typeTissu = new TypeTissu();
        factoryContext.setType(typeTissu);
        typeTissu.setId(FAKER.random().nextInt(1, 100));
        typeTissu.setValue(FAKER.beer().name());
        return typeTissu;
    }
}
