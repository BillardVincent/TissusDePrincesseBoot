package fr.vbillard.tissusdeprincesseboot.controller;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXTextField;

import static fr.vbillard.tissusdeprincesseboot.fx_custom_element.IntegerSpinner.setSpinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.vbillard.tissusdeprincesseboot.model.UserPref;
import fr.vbillard.tissusdeprincesseboot.service.UserPrefService;
import fr.vbillard.tissusdeprincesseboot.utils.FxData;
import javafx.fxml.FXML;
import javafx.stage.Stage;

@Component
public class PreferenceController implements IModalController {

	@FXML
	public JFXTextField poidsMoyenMin;
	@FXML
	public JFXTextField poidsMoyenMax;
	@FXML
	public JFXTextField margePoids;
	@FXML
	public JFXTextField margeLongueur;

	private UserPrefService userPrefService;
	private UserPref pref;
	private Stage dialogStage;

	public PreferenceController(UserPrefService userPrefService) {
		this.userPrefService = userPrefService;
	}

	@Override
	public FxData result() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStage(Stage dialogStage, FxData data) {
		NumberFormat formatter = new DecimalFormat("0");
		this.dialogStage = dialogStage;
		setSpinner(poidsMoyenMin);
		setSpinner(poidsMoyenMax);
		setSpinner(margePoids);
		setSpinner(margeLongueur);

		pref = userPrefService.getUser();
		poidsMoyenMax.setText(String.valueOf(pref.getMaxPoidsMoyen()));
		poidsMoyenMin.setText(String.valueOf(pref.getMinPoidsMoyen()));
		margePoids.setText(formatter.format(pref.getPoidsMargePercent() * 100));
		margeLongueur.setText(formatter.format(pref.getLongueurMargePercent() * 100));
	}

	@FXML
	public void handleOk() {
		pref.setMaxPoidsMoyen(Integer.parseInt(poidsMoyenMax.getText()));
		pref.setMinPoidsMoyen(Integer.parseInt(poidsMoyenMin.getText()));
		pref.setPoidsMargePercent(Float.parseFloat(margePoids.getText()) / 100);
		pref.setLongueurMargePercent(Float.parseFloat(margeLongueur.getText()) / 100);

		userPrefService.saveOrUpdate(pref);

		dialogStage.close();

	}

}
