package fr.vbillard.tissusdeprincesseboot.factory;

import fr.vbillard.tissusdeprincesseboot.model.Matiere;

import static fr.vbillard.tissusdeprincesseboot.utils.RandomUtils.FAKER;

public class MatiereFactory {

    public static Matiere newMatiere(){
        return newMatiere(new FactoryContext());
    }
    public static Matiere newMatiere(FactoryContext factoryContext) {
        Matiere matiere = new Matiere();
        factoryContext.setMatiere(matiere);
        matiere.setId(FAKER.random().nextInt(1, 100));
        matiere.setValue(FAKER.commerce().material());
        return matiere;
    }
}
