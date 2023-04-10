package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.TissuRequisService;
import fr.vbillard.tissusdeprincesseboot.service.TissuUsedService;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Rules {

  TissuUsedService tissuUsedService;
  FournitureUsedService fournitureUsedService;
  TissuRequisService tissuRequisService;
  FournitureRequiseService fournitureRequiseService;
  UserPrefService userPrefService;

  public static ErrorWarn definitiveAction() {
    ErrorWarn result = new ErrorWarn();
    result.addWarn("Cette action est définitive");
    return result;
  }


  public ErrorWarn notReservedAnymore(Projet projet) {
    ErrorWarn result = new ErrorWarn();
    if (!tissuUsedService.getByProjet(projet).isEmpty()) {
      notReservedAnymore(result, EntityToString.TISSU);
    }
    if (!fournitureUsedService.getByProjet(projet).isEmpty()) {
      notReservedAnymore(result, EntityToString.FOURNITURE);
    }
    return result;
  }

  private static ErrorWarn notReservedAnymore(ErrorWarn result, EntityToString entity) {
    result.addWarn(
        ModelUtils.generateString(entity, Articles.DEFINI, true, true) + "ne seront plus réservés pour ce projet");
    return result;
  }

  public ErrorWarn verifyLenght(Projet projet) {
    ErrorWarn result = new ErrorWarn();
    List<TissuRequis> trList = tissuRequisService.getAllRequisByPatron(projet.getPatron().getId());
    for (TissuRequis tr : trList) {
      List<TissuUsed> tuList = tissuUsedService.getTissuUsedByTissuRequisAndProjet(tr, projet);
      int longueurUtilisee = tuList.stream().map(TissuUsed::getLongueur).reduce(0, Integer::sum);
      float marge = userPrefService.getUser().getPoidsMargePercent();

      if (tr.getLongueur() - marge * tr.getLongueur() > 0) {
        //TODO cf warning on card
      }

    }

    List<FournitureRequise> frList = fournitureRequiseService.getAllRequisByPatron(projet.getPatron().getId());
    for (FournitureRequise fr : frList) {
      List<FournitureUsed> fuList = fournitureUsedService.getFournitureUsedByFournitureRequiseAndProjet(fr, projet);
      float longueurUtilisee = fuList.stream().map(FournitureUsed::getQuantite).reduce(0f, Float::sum);
      float marge = userPrefService.getUser().getPoidsMargePercent();

      if (fr.getQuantite() - marge * fr.getQuantite() > 0) {
        //TODO
      }

    }

    return  result;
  }
}
