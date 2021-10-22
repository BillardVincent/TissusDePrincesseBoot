package fr.vbillard.tissusdeprincesseboot.factory;

import fr.vbillard.tissusdeprincesseboot.model.Patron;

import static fr.vbillard.tissusdeprincesseboot.utils.RandomUtils.FAKER;

public class PatronFactory {

    public static Patron newPatron() {
        return newPatron(new FactoryContext());
    }
    public static Patron newPatron(FactoryContext factoryContext) {
        Patron patron = new Patron();
        patron.setId(FAKER.random().nextInt(1,100));
        patron.setDescription(FAKER.gameOfThrones().quote());
        patron.setMarque(FAKER.gameOfThrones().house());
        patron.setModele(FAKER.gameOfThrones().character());
        patron.setReference(FAKER.gameOfThrones().dragon());
        patron.setTypeVetement(FAKER.gameOfThrones().city());
        return patron;
    }
}
