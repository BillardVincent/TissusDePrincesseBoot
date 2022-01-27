package fr.vbillard.tissusdeprincesseboot.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtosFx.ListElement;
import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.Getter;

@Getter
@Component
public class FxData  {

    private PathEnum path;
    private TissuDto tissu;
    private PatronDto patron;
    private ProjetDto projet;
    private TissuUsed tissuUsed;
    private TissuRequisDto tissuRequis;
    private TissuVariantDto tissuVariant;

    public FxData getCopy() {
        try {
            return (FxData) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("Echec du clonage : " + this.toString());
        }
    }

    public void setPath(PathEnum path) {
        this.path = path;
    }

    public void setTissu(TissuDto tissu) {
        this.tissu = tissu;

    }

    public void setPatron(PatronDto patron) {
        this.patron = patron;

    }

    public void setTissuUsed(TissuUsed tissuUsed) {
        this.tissuUsed = tissuUsed;

    }
    
    public void setProjet(ProjetDto projet) {
        this.projet = projet;

    }
    
    public void setTissuRequis(TissuRequisDto tissuRequis) {
        this.tissuRequis = tissuRequis;

    }
    
    public void setTissuVariant(TissuVariantDto tissuVariant) {
        this.tissuVariant = tissuVariant;
    }


}
