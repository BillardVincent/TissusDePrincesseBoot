package fr.vbillard.tissusdeprincesseboot.mapper.to_dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dao.FournitureUsedDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;

import org.modelmapper.spi.DestinationSetter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.TissuUsedDao;
import fr.vbillard.tissusdeprincesseboot.dao.TissusRequisDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise_;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class ProjetToDto extends TypeMapConfigurer<Projet, ProjetDto> {

    @Lazy
    TissusRequisDao trs ;
    @Lazy
    TissuUsedDao tus;
    @Lazy
    FournitureRequiseDao frs ;
    @Lazy
    FournitureUsedDao fus;

    @Override
    public void configure(TypeMap<Projet, ProjetDto> typeMap) {
        typeMap.addMapping(Projet::getStatus, (DestinationSetter<ProjetDto, ProjectStatus>) ProjetDto::setProjectStatus);
        typeMap.setPostConverter(context -> {
            Map<TissuRequisDto, List<Integer>> tuMap = new HashMap<TissuRequisDto, List<Integer>>();
            if (context.getSource().getId() != 0){
                List<TissuRequis> trLst = trs.getAllByPatronId(context.getSource().getPatron().getId());
                for (TissuRequis tr : trLst) {
                    List<TissuUsed> tu = tus.getAllByRequisAndProjet(tr, context.getSource());
                    tuMap.put(new ModelMapper().map(tr, TissuRequisDto.class), tu == null ? new ArrayList<Integer>() : tu.stream().map(TissuUsed::getId).collect(Collectors.toList()));
                }
            }
            context.getDestination().setTissuUsed(tuMap);

            Map<FournitureRequiseDto, List<Integer>> fuMap = new HashMap<FournitureRequiseDto, List<Integer>>();
            if (context.getSource().getId() != 0){
                List<FournitureRequise> frLst = frs.getAllByPatronId(context.getSource().getPatron().getId());
                for (FournitureRequise fr : frLst) {
                    List<FournitureUsed> fu = fus.getAllByRequisAndProjet(fr, context.getSource());
                    fuMap.put(new ModelMapper().map(fr, FournitureRequiseDto.class), fu == null ? new ArrayList<Integer>()
                        : fu.stream().map(FournitureUsed::getId).collect(Collectors.toList()));
                }
            }
            context.getDestination().setFournitureUsed(fuMap);

            return context.getDestination();
        });
    }

}
