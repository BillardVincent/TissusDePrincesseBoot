package fr.vbillard.tissusdeprincesseboot.controller.projet;

import fr.vbillard.tissusdeprincesseboot.controller.StageInitializer;
import fr.vbillard.tissusdeprincesseboot.controller.components.cards.Card;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import fr.vbillard.tissusdeprincesseboot.controller.utils.IController;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.FournitureDto;
import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.FournitureUsed;
import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.service.FournitureService;
import fr.vbillard.tissusdeprincesseboot.service.ImageService;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static fr.vbillard.tissusdeprincesseboot.controller.utils.FxUtils.setTextFromQuantity;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class FournitureUsedCardComponent extends Card implements IController {

    StageInitializer initializer;

    FournitureUsed fournitureUsed;
    ProjetEditListElementController parent;
    FournitureDto fournitureDto;
    FournitureService fournitureService;

    ImageService imageService;

    public FournitureUsedCardComponent(FournitureService fournitureService, ImageService imageService){
        this.imageService = imageService;
        this.fournitureService = fournitureService;
    }


    @Override
    public void setStageInitializer(StageInitializer initializer, FxData data) {
        this.initializer = initializer;
        if (data == null || data.getFournitureUsed() == null) {
            throw new IllegalData();
        }
        fournitureUsed = data.getFournitureUsed();
        parent = (ProjetEditListElementController) data.getParentController();

        //fournitureDto = fournitureService.convert(fournitureUsed.getFourniture());
        //setCardContent();

        setMinWidth(170);
        //setWidth(170);
        setTitre(fournitureUsed.getFourniture().getNom());
        Optional<Photo> picture = imageService.getImage(fournitureUsed.getFourniture());
        getImage().setImage(imageService.imageOrDefault(picture));
        getImage().setFitWidth(169.0);
        Label label = new Label(setTextFromQuantity(fournitureUsed.getQuantite(), fournitureUsed.getFourniture().getQuantitePrincipale().getUnite()));
        label.setAlignment(Pos.CENTER);
        HBox p = new HBox(label);
        p.setAlignment(Pos.CENTER);
        addContent(p);
        if (fournitureUsed.getFourniture().getQuantiteSecondaire() != null){
            Label label2 = new Label(setTextFromQuantity(fournitureUsed.getFourniture().getQuantiteSecondaire()));
            label2.setAlignment(Pos.CENTER);
            HBox p2 = new HBox(label2);
            p.setAlignment(Pos.CENTER);
            addContent(p2);

        }
    }

}
