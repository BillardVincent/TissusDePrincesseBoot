package fr.vbillard.tissusdeprincesseboot.utils;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.Data;

@Data
@Component
public class FxData  {

    private PathEnum path;
    private TissuDto tissu;
    private PatronDto patron;
    private ProjetDto projet;
    private TissuUsed tissuUsed;
    private TissuRequisDto tissuRequis;
    private TissuVariantDto tissuVariant;
    private int longueurRequise;

    public FxData getCopy() {
        try {
            return (FxData) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("Echec du clonage : " + this.toString());
        }
    }




}
