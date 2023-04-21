package fr.vbillard.testintegrationtdp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.Matiere;
import fr.vbillard.tissusdeprincesseboot.model.Quantite;
import fr.vbillard.tissusdeprincesseboot.model.Tissage;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.model.enums.UnitePoids;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.MatiereService;
import fr.vbillard.tissusdeprincesseboot.service.TissageService;
import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Factory {

  MatiereService matiereService;
  TissageService tissageService;
  TissuService tissuService;
  TypeFournitureService typeFournitureService;
  FournitureService fournitureService;

  public void setBdd(){
    for (int i = 0; i < 10; i++) {
      matiereService.saveOrUpdate(matiere(i));
    }

    for (int i = 0; i < 4; i++) {
      tissageService.saveOrUpdate(tissage(i));
    }

    for (int i = 0; i < 24; i++) {
      tissuService.saveOrUpdate(tissu(i));
    }

    for (int i = 0; i < 3; i++) {
      typeFournitureService.saveOrUpdate(typeFourniture(i));
    }

    for (int i = 0; i < 12; i++) {
      fournitureService.saveOrUpdate(fourniture(i));
    }
  }

  public Tissage tissage (int i){
    return new Tissage("Tissage-"+i);
  }

  public  Matiere matiere (int i){
    return new Matiere("Matiere-"+i);
  }

  public TypeFourniture typeFourniture(int i){
    TypeFourniture typeFourniture = new TypeFourniture();
    typeFourniture.setIntitulePrincipale("intitulé-"+i);
    typeFourniture.setIntituleSecondaire("intitulé-2ndR-"+i);
    typeFourniture.setDimensionPrincipale(TDPRandomUtils.randomEnum(DimensionEnum.class));
    typeFourniture.setDimensionSecondaire(TDPRandomUtils.randomEnum(DimensionEnum.class));
    return typeFourniture;
  }

  public Fourniture fourniture(int i){
    Fourniture fourniture = new Fourniture();
    fourniture.setNom("Nom-"+i);
    fourniture.setReference("ref-F-"+i);
    fourniture.setType(typeFournitureService.getAll().get(i%3));
    Quantite q = new Quantite();
    q.setQuantite((float) i);
    q.setUnite(TDPRandomUtils.randomEnum(Unite.class));
    fourniture.setQuantitePrincipale(q);
    Quantite q2 = new Quantite();
    q2.setQuantite(i +0.5f);
    q2.setUnite(TDPRandomUtils.randomEnum(Unite.class));
    fourniture.setQuantiteSecondaire(q2);
    fourniture.setLieuAchat("LieuAchat-"+i);
    return fourniture;
  }

  public Tissu tissu(int i){
    Tissu tissu = new Tissu();
    tissu.setReference("ref-T-"+i);
    tissu.setPoids((i+1)*50);
    tissu.setUnitePoids(TDPRandomUtils.randomEnum(UnitePoids.class));
    tissu.setLongueur((i+1)*10);
    tissu.setLaize((i+1)*50);
    tissu.setArchived(i%5==0);
    tissu.setMatiere(matiereService.getAll().get(i%4));
    tissu.setTissage(tissageService.getAll().get(i%3));
    tissu.setTypeTissu(TDPRandomUtils.randomEnum(TypeTissuEnum.class));
    return tissu;
  }


}
