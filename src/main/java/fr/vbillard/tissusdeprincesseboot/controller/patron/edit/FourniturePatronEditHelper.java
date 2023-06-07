package fr.vbillard.tissusdeprincesseboot.controller.patron.edit;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildComboBox;
import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.buildSpinner;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureRequiseDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureVariantDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.FournitureRequise;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.FournitureVariant;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import fr.vbillard.tissusdeprincesseboot.service.FournitureRequiseService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureUsedService;
import fr.vbillard.tissusdeprincesseboot.service.FournitureVariantService;
import fr.vbillard.tissusdeprincesseboot.service.PatronService;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils;
import fr.vbillard.tissusdeprincesseboot.utils.Utils;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

@Service
public class FourniturePatronEditHelper extends
    PatronEditHelper<Fourniture, FournitureVariant, FournitureRequise, FournitureUsed, FournitureDto, FournitureVariantDto, FournitureRequiseDto> {

  private final TypeFournitureService typeFournitureService;

  public FourniturePatronEditHelper(PatronService patronService, TypeFournitureService typeFournitureService,
      FournitureRequiseService requisService,
      FournitureVariantService variantService, FournitureUsedService usedService) {
    this.variantService = variantService;
    this.usedService = usedService;
    this.requisService = requisService;
    this.typeFournitureService = typeFournitureService;
    this.patronService = patronService;
  }

  @Override
  protected void completeTopGrid(GridPane topGrid, FournitureRequiseDto dto, JFXButton validateBtn) {
    boolean dtoIsNotNew = dto.getId() != 0;

    topGrid.add(new Label("Type"), 0, 0);

    Label intitulePrincipal = new Label();
    JFXTextField dimensionPrincipaleSpinner = buildSpinner(0f);
    dimensionPrincipaleSpinner.setVisible(dtoIsNotNew);
    JFXComboBox<String> uniteChBx = new JFXComboBox<>();
    uniteChBx.setVisible(dtoIsNotNew);

    topGrid.add(intitulePrincipal, 0, 1);
    topGrid.add(dimensionPrincipaleSpinner, 1, 1);
    topGrid.add(uniteChBx, 2, 1);

    Label intituleSecondaire = new Label();
    Label entre = new Label();
    JFXTextField dimensionMinSpinner = buildSpinner(0f);
    dimensionMinSpinner.setVisible(dtoIsNotNew);
    Label et = new Label();
    JFXTextField dimensionMaxSpinner = buildSpinner(0f);
    dimensionMaxSpinner.setVisible(dtoIsNotNew);
    JFXComboBox<String> uniteSecChBx = new JFXComboBox<>();
    uniteSecChBx.setVisible(dtoIsNotNew);

    topGrid.add(intituleSecondaire, 0, 2);
    topGrid.add(entre, 1, 2);
    topGrid.add(dimensionMinSpinner, 1, 3);
    topGrid.add(et, 2, 2);
    topGrid.add(dimensionMaxSpinner, 2, 3);
    topGrid.add(uniteSecChBx, 3, 3);

    JFXComboBox<String> typeChBx =
        buildComboBox(typeFournitureService.getAllValues(), dto.getTypeNameProperty());

    if (dtoIsNotNew) {
      dimensionPrincipaleSpinner.setText(Float.toString(dto.getQuantite()));

      if (dto.getType() != null) {
        intitulePrincipal.setText(dto.getType().getIntitulePrincipale());
        buildComboBox(Unite.getValuesByDimension(dto.getType().getDimensionPrincipale()),
            dto.getUnite().getLabel(),
            dto.getType().getDimensionPrincipale().getDefault().getLabel(), uniteChBx);
        dimensionPrincipaleSpinner.setVisible(true);
        uniteChBx.setVisible(true);

        if (dto.getType().getDimensionSecondaire() != null) {

          dimensionMinSpinner.setText(Float.toString(dto.getQuantiteSecondaireMin()));
          dimensionMaxSpinner.setText(Float.toString(dto.getQuantiteSecondaireMax()));

          intituleSecondaire.setText(dto.getType().getIntituleSecondaire());
          buildComboBox(Unite.getValuesByDimension(dto.getType().getDimensionSecondaire()),
              dto.getType().getUniteSecondaireConseillee().getLabel(),
              dto.getType().getDimensionSecondaire().getDefault().getLabel(), uniteSecChBx);

          dimensionMinSpinner.setVisible(true);
          dimensionMaxSpinner.setVisible(true);
          uniteSecChBx.setVisible(true);

          entre.setText("entre");
          et.setText("et");
        }
      }
    }

    typeChBx.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

      TypeFourniture typeFourniture = typeFournitureService.findTypeFourniture(newValue);
      intitulePrincipal.setText(typeFourniture.getIntitulePrincipale());

      buildComboBox(Unite.getValuesByDimension(typeFourniture.getDimensionPrincipale()),
          typeFourniture.getUnitePrincipaleConseillee().getLabel(),
          typeFourniture.getDimensionPrincipale().getDefault().getLabel(), uniteChBx);
      dimensionPrincipaleSpinner.setVisible(true);
      uniteChBx.setVisible(true);

      if (typeFourniture.getDimensionSecondaire() != null) {

        intituleSecondaire.setText(typeFourniture.getIntituleSecondaire());

        buildComboBox(Unite.getValuesByDimension(typeFourniture.getDimensionSecondaire()),
            typeFourniture.getUniteSecondaireConseillee().getLabel(),
            typeFourniture.getDimensionSecondaire().getDefault().getLabel(), uniteSecChBx);

        dimensionMinSpinner.setVisible(true);
        dimensionMaxSpinner.setVisible(true);
        uniteSecChBx.setVisible(true);

        entre.setText("entre");
        et.setText("et");
      }
    });

    validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

      dto.setType(typeFournitureService.findTypeFourniture(typeChBx.getValue()));

      dto.setUnite(Unite.getEnum(uniteChBx.getValue()));
      dto.setQuantite(Float.parseFloat(dimensionPrincipaleSpinner.getText()));

      dto.setUniteSecondaire(Unite.getEnum(uniteSecChBx.getValue()));
      dto.setQuantiteSecondaireMin(Float.parseFloat(dimensionMinSpinner.getText()));
      dto.setQuantiteSecondaireMax(Float.parseFloat(dimensionMaxSpinner.getText()));

      saveRequis(dto, patron);
    });

    topGrid.add(typeChBx, 1, 0);

  }

  @Override
  protected void addToPatron(FournitureRequiseDto requis, PatronDto patron) {
    patron.setFournituresRequises(requisService.convertToDto(requisService.getAllRequisByPatron(patron.getId())));
  }

  @Override
  protected HBox completeLoadBottomRightVBox(JFXButton addTvBtn, FournitureRequiseDto requis) {
    return null;
  }

  @Override
  protected void setRequisToPatron() {

    patron.setFournituresRequises(requisService.convertToDto(requisService.getAllRequisByPatron(patron.getId())));
  }

  @Override
  protected void displayVariant(GridPane bottomGrid, FournitureVariantDto tv, int index) {
    bottomGrid.add(new Label(
        Utils.safeString(tv.getTypeName()) + " - " + tv.getIntituleSecondaire() + ":  " + FxUtils.safePropertyToString(
            tv.getQuantiteSecondaireMinProperty()) + "/" + FxUtils.safePropertyToString(
            tv.getQuantiteSecondaireMaxProperty()) + Utils.safeString(tv.getUniteSecondaire())), 0, index * 2);
  }

  @Override
  protected List<FournitureRequiseDto> getListRequisFromPatron() {
    if (patron.getFournituresRequisesProperty() != null && patron.getFournituresRequises() != null) {
      return patron.getFournituresRequises();
    } else
      return Collections.emptyList();
  }

  @Override
  protected boolean hasVariant() {
    return false;
  }

  @Override
  protected Class<Fourniture> getEntityClass() {
    return Fourniture.class;
  }
}
