package fr.vbillard.tissusdeprincesseboot.mappers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TissuUsedMapper {
    /*
    @Lazy
    TissuMapper tissuMapper;
    @Lazy
    ProjetMapper projetMapper;
    @Lazy
    TissuRequisMapper tissuRequisMapper;

    public TissuUsed map(TissuRequisDto tissuRequisSelected, ProjetDto projetSelected, TissuDto tissuSelected,
                         int longueur) {
        TissuUsed tissuUsed = new TissuUsed();
        tissuUsed.setTissu( tissuMapper.map(tissuSelected));
        tissuUsed.setProjet(projetMapper.map(projetSelected));
        tissuUsed.setTissuRequis( tissuRequisMapper.map(tissuRequisSelected));
        tissuUsed.setLongueur(longueur);

        return tissuUsed;

    }

     */
}
