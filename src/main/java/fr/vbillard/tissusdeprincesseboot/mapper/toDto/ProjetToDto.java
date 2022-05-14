package fr.vbillard.tissusdeprincesseboot.mapper.toDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.TissuUsedDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@Component
@AllArgsConstructor
public class ProjetToDto extends TypeMapConfigurer<Projet, ProjetDto> {

    @Lazy
    TissusRequisDao trs ;
    @Lazy
    TissuUsedDao tus;

    @Override
    public void configure(TypeMap<Projet, ProjetDto> typeMap) {
        typeMap.addMapping(Projet::getStatus, (ProjetDto dest, ProjectStatus v) -> dest.setProjectStatus(v));
        typeMap.setPostConverter(context -> {
            Map<TissuRequisDto, List<Integer>> tuMap = new HashMap<TissuRequisDto, List<Integer>>();
            if (context.getSource().getId() != 0){
                List<TissuRequis> trLst = trs.getAllByPatronId(context.getSource().getPatron().getId());
                for (TissuRequis tr : trLst) {
                    List<TissuUsed> tu = tus.getAllByTissuRequisAndProjet(tr, context.getSource());
                    tuMap.put(new ModelMapper().map(tr, TissuRequisDto.class), tu == null ? new ArrayList<Integer>() : tu.stream().map(AbstractEntity::getId).collect(Collectors.toList()));
                }
            }
            context.getDestination().setTissuUsed(tuMap);

            return context.getDestination();
        });
    }

}
