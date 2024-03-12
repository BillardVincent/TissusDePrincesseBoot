package fr.vbillard.tissusdeprincesseboot.mapper.from_dto;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DtoToRequis extends TypeMapConfigurer<TissuRequisDto, TissuRequis> {

  private PatronVersionDao versionDao;
  private MatiereService matiereService;
  private TissageService tissageService;

  @Override
  public void configure(TypeMap<TissuRequisDto, TissuRequis> typeMap) {

    typeMap.addMappings(mapper -> mapper.using(new IdConverter()).map(src -> src, TissuRequis::setId));
    typeMap.setPostConverter(context -> {

      TissuRequis dest = context.getDestination();
      TissuRequisDto src = context.getSource();
      dest.setVersion(versionDao.getById(src.getVersionId()));
      dest.setMatieres(matiereService.findMatiere(src.getMatiere()));
      dest.setTissages(tissageService.tissageValuesSelected(src.getTissage()));

      return context.getDestination();
    });
  }

  private static class IdConverter extends AbstractConverter<TissuRequisDto, Integer> {
    @Override
    protected Integer convert(TissuRequisDto dto) {
      return dto.getIdProperty() == null ? 0 : dto.getId();
    }
  }

}
