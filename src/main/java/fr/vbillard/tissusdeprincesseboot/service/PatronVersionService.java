package fr.vbillard.tissusdeprincesseboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.PatronVersionDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronVersionDto;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatronVersionService extends AbstractDtoService<PatronVersion, PatronVersionDto> {
  private PatronVersionDao dao;
  private MapperService mapper;

  @Override
  protected PatronVersionDao getDao() {
    return dao;
  }

  @Override
  protected void beforeSaveOrUpdate(PatronVersion patronVersion){
    if(patronVersion.getId() != 0) {
      dao.findById(patronVersion.getId()).ifPresent(pv -> patronVersion.setPatron(pv.getPatron()));
    }
  }

  @Override
  public PatronVersion convert(PatronVersionDto dto) {
    return mapper.map(dto);
  }

  @Override
  public PatronVersionDto convert(PatronVersion entity) {
    return mapper.map(entity);
  }

  public List<PatronVersion> getByPatronId(int id) {
    return dao.getByPatronId(id);
  }
}
