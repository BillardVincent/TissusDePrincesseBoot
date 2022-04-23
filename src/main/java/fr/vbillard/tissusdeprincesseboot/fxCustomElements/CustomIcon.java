package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.sun.javafx.webkit.Accessor;

import fr.vbillard.tissusdeprincesseboot.config.PathIconsProperties;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import fr.vbillard.tissusdeprincesseboot.model.enums.TypeTissuEnum;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.web.WebView;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomIcon {

	PathIconsProperties pathProperties;

	public void washingMachinIcon(WebView view, double size) {
		Resource path = pathProperties.getWashingMachine();
		loadSVG(view, path, size);
	}

	public void noWashingMachinIcon(WebView view, double size) {
		Resource path = pathProperties.getNoWashingMachine();
		loadSVG(view, path, size);
	}

	public WebView typeTissu(WebView view, TypeTissuEnum typeTissu) {
		Resource path = null;
		switch (typeTissu) {
		case CHAINE_ET_TRAME:
			path = pathProperties.getChaineEtTrame();
			break;
		case MAILLE:
			path = pathProperties.getMaille();
			break;
		case MIXILIGNE:
			path = pathProperties.getMultiligne();
			break;
		case NON_TISSE:
			path = pathProperties.getNonTisse();
			break;
		case NON_RENSEIGNE:
			return new WebView();
		}

		loadSVG(view, path, 40);
		return view;
	}

	private void loadSVG(WebView view, Resource path, double size) {
		Accessor.getPageFor(view.getEngine()).setBackgroundColor(0);

		view.setMinSize(size, size);
		view.setPrefSize(size, size);
		try {
			view.getEngine().load(path.getURL().toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistanceException(path.getFilename());
		}
		for (Node content : view.getChildrenUnmodifiable()) {
			if (content instanceof ScrollBar) {

				double currentHeight = ((ScrollBar) content).getHeight();

				double scale = size / currentHeight;

				view.setZoom(scale);

			}
		}
	}

}
