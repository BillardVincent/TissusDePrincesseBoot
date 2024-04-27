package fr.vbillard.tissusdeprincesseboot.controller.common;

import de.jensd.fx.glyphs.GlyphIcon;
import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.misc.RootController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ClassCssUtils;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.controller.utils.ShowAlert;
import fr.vbillard.tissusdeprincesseboot.controller.utils.fx_custom_element.GlyphIconUtil;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.enums.DimensionEnum;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(Utils.PROTOTYPE)
public class FournitureRequiseCardController implements IController {

    @FXML
    private Label uniteLabel;
    @FXML
    private Label quantiteLabel;
    @FXML
    private Label uniteSecLabel;
    @FXML
    private Label quantiteSecLabel;
    @FXML
    private Label typeLabel;

    private StageInitializer initializer;
    private FournitureRequiseDto fournitureRequise;
    private FournitureUsedService fournitureUsedService;

    private RootController rootController;
    private ProjetDto projetDto;

    private FxData fxData;

    public FournitureRequiseCardController(FournitureUsedService fournitureUsedService, RootController rootController) {
        this.fournitureUsedService = fournitureUsedService;
        this.rootController = rootController;
    }

    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;
        if (data == null || data.getFournitureRequise() == null || data.getProjet() == null) {
            throw new IllegalData();
        }
        projetDto = data.getProjet();
        fournitureRequise = data.getFournitureRequise();
        if (fournitureRequise != null) {
            String unite = fournitureRequise.getUnite() != null ? fournitureRequise.getUnite().getAbbreviation() :
                    Strings.EMPTY;
            quantiteLabel.setText(fournitureRequise.getQuantite() + unite);
            typeLabel.setText(fournitureRequise.getTypeName());
            if (fournitureRequise.getType() != null) {
                uniteLabel.setText(fournitureRequise.getType().getIntitulePrincipale());

                uniteSecLabel.setText(fournitureRequise.getType().getIntituleSecondaire());
                String uniteSecondaire = fournitureRequise.getUniteSecondaire() != null ?
                        fournitureRequise.getUniteSecondaire().getAbbreviation() : Strings.EMPTY;
                quantiteSecLabel.setText(fournitureRequise.getQuantiteSecondaireMin() + "-" +
                        fournitureRequise.getQuantiteSecondaireMax() + uniteSecondaire);
            }
        } else {
            quantiteLabel.setText("?");
            typeLabel.setText("?");
            uniteLabel.setText("?");
        }
        setIcones();
    }

    private void setIcones() {
        GlyphIcon<?> iconStatus;

        List<FournitureUsed> fournitureUseds = fournitureUsedService.getFournitureUsedByFournitureRequiseAndProjet(fournitureRequise, projetDto);
        if(fournitureRequise.getType() != null && fournitureRequise.getType().getDimensionSecondaire() != null){
            List<FournitureUsed> uniteSecondaireIncompatibles = fournitureUseds.stream().filter(this::assertUniteSecondaireCompatibles).toList();
            List<FournitureUsed> dimensionSecOutOfRange = fournitureUseds.stream().filter(f -> !uniteSecondaireIncompatibles.contains(f)).toList();
        }

        if (!fournitureUseds.stream().allMatch(this::assertUniteSecondaireCompatibles)){
            iconStatus = GlyphIconUtil.warning();
            StringBuilder header = new StringBuilder();
            StringBuilder content = new StringBuilder();
/*
            getMessageForLongueur(longueurUtilisee, longueurMin, header, content);

            getMessageForLaize(tissuUsedTooShort, header, content);

            getMesageForDecati(tissuUsedNotDecati, header, content);


 */
            iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ShowAlert
                    .information(initializer.getPrimaryStage(), "Attention", header.toString(), content.toString()));
            iconStatus.setStyleClass(ClassCssUtils.CLICKABLE);

        } else {
            iconStatus = GlyphIconUtil.ok();
        }

        /*
        int longueurMin = trloService.getLongueurMinByRequis(tissuRequis.getId());
        int longueurUtilisee = tissuUsedService.longueurUsedByRequis(tissuRequis, projet);

        List<TissuUsed> tissuUsedTooShort = tissuUsedService.getTissuVariantLaizeTooShort(tissuRequis, projet);
        List<TissuUsed> tissuUsedNotDecati = tissuUsedService.getTissuUsedNotDecati(tissuRequis, projet);

        if (longueurUtilisee < longueurMin - longueurMin * preferenceService.getUser().getLongueurMargePercent()) {
            iconStatus = GlyphIconUtil.notOk();
            iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    e -> ShowAlert.information(initializer.getPrimaryStage(), "Attention", "Pas assez de tissu",
                            "La longueur de tissu alloué est inférieure à la longueur de tissu "
                                    + "requise. Ajoutez d'autres tissus"));
            iconStatus.setStyleClass(ClassCssUtils.CLICKABLE);

        } else if (longueurUtilisee < longueurMin || !CollectionUtils.isEmpty(tissuUsedTooShort)
                || !CollectionUtils.isEmpty(tissuUsedNotDecati)) {
            iconStatus = GlyphIconUtil.warning();
            StringBuilder header = new StringBuilder();
            StringBuilder content = new StringBuilder();

            getMessageForLongueur(longueurUtilisee, longueurMin, header, content);

            getMessageForLaize(tissuUsedTooShort, header, content);

            getMesageForDecati(tissuUsedNotDecati, header, content);

            iconStatus.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ShowAlert
                    .information(initializer.getPrimaryStage(), "Attention", header.toString(), content.toString()));
            iconStatus.setStyleClass(ClassCssUtils.CLICKABLE);

        } else {
            iconStatus = GlyphIconUtil.ok();
        }


         */
        //doublurHbx.getChildren().add(iconStatus);
    }

    private boolean assertUniteSecondaireCompatibles(FournitureUsed f) {
        boolean usedQteSecondaireIsNotNull = f.getFourniture().getQuantiteSecondaire() != null;
        boolean qteSecondaireIsNull = fournitureRequise.getUniteSecondaire() == null;
        boolean usedUniteSecondaireIsNotNull = usedQteSecondaireIsNotNull && f.getFourniture().getQuantiteSecondaire().getUnite() != null;

        return ( qteSecondaireIsNull ||
                (usedQteSecondaireIsNotNull && usedUniteSecondaireIsNotNull &&
                        DimensionEnum.unitsAreInSameDimension(fournitureRequise.getUniteSecondaire(),
                                f.getFourniture().getQuantiteSecondaire().getUnite())));
    }

}
