package fr.vbillard.tissusdeprincesseboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.vbillard.tissusdeprincesseboot.dao.FournitureRequiseDao;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.common.NumericSearch;
import fr.vbillard.tissusdeprincesseboot.mapper.MapperService;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
public class FournitureRequiseService
    extends AbstractRequisService<FournitureRequise, Fourniture, FournitureRequiseDto> {

  private final FournitureRequiseDao fournitureRequiseDao;
  private final FournitureVariantService tvs;
  private final UserPrefService userPrefService;

  public FournitureRequiseService(MapperService mapper, FournitureRequiseDao fournitureRequiseDao,
      FournitureVariantService tvs, UserPrefService userPrefService, PatronVersionService patronVersionService) {
    super(mapper, patronVersionService);
    this.fournitureRequiseDao = fournitureRequiseDao;
    this.tvs = tvs;
    this.userPrefService = userPrefService;
  }

  public List<FournitureRequise> getAllByVersionId(int id) {
    return fournitureRequiseDao.getAllByVersionId(id);
  }

  public List<FournitureRequiseDto> getAllFournitureRequiseDtoByVersion(int id) {
    return fournitureRequiseDao.getAllByVersionId(id).stream().map(tr -> mapper.map(tr)).collect(Collectors.toList());
  }

  public FournitureRequise findFournitureRequise(int fournitureRequisId) {

    return fournitureRequiseDao.findById(fournitureRequisId).get();
  }

  @Transactional
  public void delete(FournitureRequiseDto fourniture) {
    delete(mapper.map(fourniture));

  }

  @Override
  protected void beforeSaveOrUpdate(FournitureRequise entity) {
    //nothing to do here
  }

  @Transactional
  @Override
  public void delete(FournitureRequise fourniture) {
    PatronVersion pv = fourniture.getVersion();
    pv.getFournituresRequises().remove(fourniture);
    patronVersionService.saveOrUpdate(pv);

    List<FournitureVariant> tvLst = tvs.getVariantByRequis(fourniture);
    for (FournitureVariant tv : tvLst) {
      tvs.delete(tv);
    }
    fournitureRequiseDao.delete(fourniture);

  }

  public ObservableList<FournitureRequise> getAsObservableAllFournitureRequiseByVersion(int id) {
    return FXCollections.observableArrayList(getAllByVersionId(id));
  }

  public FournitureSpecification getFournitureSpecification(FournitureRequiseDto tr) {
    return getFournitureSpecification(convert(tr));
  }

  public FournitureSpecification getFournitureSpecification(FournitureRequise fr) {

    float marge = userPrefService.getUser().getLongueurMargePercent();

    NumericSearch<Float> quantiteSearch = new NumericSearch<>();
    quantiteSearch.setGreaterThanEqual(fr.getQuantite() - fr.getQuantite() * marge);

    List<TypeFourniture> types = new ArrayList<>();
    types.add(fr.getType());

    NumericSearch<Float> quantiteSecondaire = null;

    if (fr.getType().getDimensionSecondaire() != null) {
      quantiteSecondaire = new NumericSearch<>();
      quantiteSecondaire.setGreaterThanEqual(fr.getQuantiteSecMin());
      quantiteSecondaire.setLessThanEqual(fr.getQuantiteSecMax());
    }

    return FournitureSpecification.builder().type(types).quantite(quantiteSearch).quantiteSecondaire(quantiteSecondaire)
        .build();
  }

  @Override
  public FournitureRequise convert(FournitureRequiseDto dto) {
    return mapper.map(dto);
  }

  @Override
  public FournitureRequiseDto convert(FournitureRequise entity) {
    return mapper.map(entity);
  }

  @Override
  protected FournitureRequiseDao getDao() {
    return fournitureRequiseDao;
  }
}
