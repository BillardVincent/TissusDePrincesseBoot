package fr.vbillard.tissusdeprincesseboot.controller.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaizeLongueurOptionCell extends HBox {

  private int value;
  private IntegerSpinner laizeSpinner;

  public LaizeLongueurOptionCell(int value){
    this.value = value;
    setSpacing(5);
    setAlignment(Pos.CENTER);
    laizeSpinner = new IntegerSpinner();
    laizeSpinner.setPrefWidth(100);
    laizeSpinner.setText(String.valueOf(value));

    validate();
  }

  public void edit(){
    getChildren().clear();
    Label laizeLbl = new Label("cm");
    getChildren().addAll(laizeSpinner, laizeLbl);
  }

  public int validate(){
    getChildren().clear();
    value = Integer.parseInt(laizeSpinner.getText());
    Label laizeLbl = new Label(laizeSpinner.getText() + " cm");
    getChildren().add(laizeLbl);
    return value;
  }

  public void cancel(){
    laizeSpinner.setText(String.valueOf(value));
    validate();
  }
}
