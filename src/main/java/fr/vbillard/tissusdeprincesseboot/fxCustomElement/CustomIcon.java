package fr.vbillard.tissusdeprincesseboot.fxCustomElement;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import com.sun.javafx.webkit.Accessor;

import fr.vbillard.tissusdeprincesseboot.config.PathIconsProperties;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import javafx.scene.web.WebView;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomIcon {

	PathIconsProperties pathProperties;

	public void washingMachinIcon(WebView view, double size) {
		Resource path = pathProperties.getWashingMachine();
		double originalSize = pathProperties.getWashingMachineSize();
		loadSVG(view, path, size, originalSize);
	}

	public void noWashingMachinIcon(WebView view, double size) {
		Resource path = pathProperties.getNoWashingMachine();
		double originalSize = pathProperties.getNoWashingMachineSize();
		loadSVG(view, path, size, originalSize);
	}

	public WebView typeTissu(WebView view, TypeTissuEnum typeTissu) {
		if (typeTissu == null) {
			return new WebView();
		}
		Resource path;
		double originalSize;
		switch (typeTissu) {
		case CHAINE_ET_TRAME:
			path = pathProperties.getChaineEtTrame();
			originalSize = pathProperties.getChaineEtTrameSize();
			break;
		case MAILLE:
			path = pathProperties.getMaille();
			originalSize = pathProperties.getMailleSize();
			break;
		case MIXILIGNE:
			path = pathProperties.getMultiligne();
			originalSize = pathProperties.getMultiligneSize();
			break;
		case NON_TISSE:
			path = pathProperties.getNonTisse();
			originalSize = pathProperties.getNonTisseSize();
			break;
		default:
			return new WebView();
		}

		loadSVG(view, path, 40, originalSize);
		return view;
	}

	private void loadSVG(WebView view, Resource path, double size, double originalSize) {
		Accessor.getPageFor(view.getEngine()).setBackgroundColor(0);

		view.setMinSize(size, size);
		view.setPrefSize(size, size);
		try {
			view.getEngine().load(path.getURL().toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistanceException(path.getFilename());
		}

		double scale = size / originalSize;

		view.setZoom(scale);
	}

}
