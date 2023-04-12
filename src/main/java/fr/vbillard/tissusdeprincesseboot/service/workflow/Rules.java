package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;
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

  private static void notReservedAnymore(ErrorWarn result, EntityToString entity) {
    result.addWarn(
        ModelUtils.generateString(entity, Articles.DEFINI, true, true) + "ne seront plus réservés pour ce projet");
  }

  public ErrorWarn verifyLenght(Projet projet) {
    ErrorWarn result = new ErrorWarn();
    List<TissuRequis> trList = tissuRequisService.getAllRequisByPatron(projet.getPatron().getId());
    for (TissuRequis tr : trList) {
      int longueurUtilisee =  tissuUsedService.longueurUsedByRequis(tr, projet);
      float marge = userPrefService.getUser().getLongueurMargePercent();

      if (tr.getLongueur() - marge * tr.getLongueur() > longueurUtilisee) {
        result.addWarn("La longueur totale alloué est inferieure à la longueur requise pour " + tr);
      }

    }

    List<FournitureRequise> frList = fournitureRequiseService.getAllRequisByPatron(projet.getPatron().getId());
    for (FournitureRequise fr : frList) {
      List<FournitureUsed> fuList = fournitureUsedService.getFournitureUsedByFournitureRequiseAndProjet(fr, projet);
      float quantiteUtilisee = fuList.stream().map(FournitureUsed::getQuantite).reduce(0f, Float::sum);

      if (fr.getQuantite()  > quantiteUtilisee) {
        result.addWarn("La quantité totale alloué est inferieure à la quantité requise pour " + fr);
      }

    }

    return  result;
  }
}
