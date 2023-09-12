package fr.vbillard.tissusdeprincesseboot.service.workflow;

import fr.vbillard.tissusdeprincesseboot.model.*;
import fr.vbillard.tissusdeprincesseboot.service.*;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.Articles;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.EntityToString;
import fr.vbillard.tissusdeprincesseboot.utils.model_to_string.ModelUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

  public ErrorWarn verifyLength(Projet projet) {
    ErrorWarn result = new ErrorWarn();
    List<TissuRequis> trList = tissuRequisService.getAllByVersionId(projet.getPatronVersion().getId());

    float marge = userPrefService.getUser().getLongueurMargePercent();

    for (TissuRequis tr : trList) {
      int longueurUtilisee =  tissuUsedService.longueurUsedByRequis(tr, projet);

      for (TissuRequisLaizeOption trlo : tr.getOption()){
        if ((trlo.getLongueur() - marge * trlo.getLongueur() > longueurUtilisee)) {
          result.addWarn("La longueur totale allouée est inférieure à la longueur requise pour " + tr);
          break;
        }
      }
    }

    List<FournitureRequise> frList = fournitureRequiseService.getAllByVersionId(projet.getPatronVersion().getId());
    for (FournitureRequise fr : frList) {
      List<FournitureUsed> fuList = fournitureUsedService.getFournitureUsedByFournitureRequiseAndProjet(fr, projet);
      float quantiteUtilisee = fuList.stream().map(FournitureUsed::getQuantite).reduce(0f, Float::sum);

      if (fr.getQuantite()  > quantiteUtilisee) {
        result.addWarn("La quantité totale allouée est inférieure à la quantité requise pour " + fr);
      }
    }

    return  result;
  }
}
