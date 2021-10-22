package fr.vbillard.tissusdeprincesseboot.factory;

import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Patron;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TypeTissu;
import lombok.Setter;

@Setter
public class FactoryContext {
    private TissuVariantDto variantDto;

    public TissuVariantDto getTissuVariantDto(){
        return variantDto == null ? TissuVariantDtoFactory.newDto(this) : variantDto;
    }

    private Matiere matiere;

    public Matiere getMatiere() {
        return matiere == null ? MatiereFactory.newMatiere(this): matiere;
    }

    private TypeTissu type;

    public TypeTissu getTypeTissu() {
        return type == null ? TypeTissuFactory.newType(this):type;
    }

    private Tissage tissage;

    public Tissage getTissage() {
        return tissage == null? TissageFactory.newTissage(this):tissage;
    }

    private TissuRequis tissuRequis;
    public TissuRequis getTissuRequis() {
        return tissuRequis == null ? TissuRequisFactory.newTissuRequis(this):tissuRequis;
    }

    private Patron patron;
    public Patron getPatron() {
        return patron == null ? PatronFactory.newPatron(this):patron;

    }
}
