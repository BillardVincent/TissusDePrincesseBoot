package fr.vbillard.tissusdeprincesseboot.fxCustomElements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import fr.vbillard.tissusdeprincesseboot.config.PathIconsProperties;
import fr.vbillard.tissusdeprincesseboot.exception.PersistanceException;
import fr.vbillard.tissusdeprincesseboot.utils.svg.BufferedImageTranscoder;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomIcon {

	PathIconsProperties pathProperties;

	private ImageView setIcone(ImageView view, String path, Paint color, double size) {
		BufferedImageTranscoder trans = new BufferedImageTranscoder();
		trans.createImage(25, 25);

		try (InputStream file = new ClassPathResource(path).getInputStream()) {

			TranscoderInput transIn = new TranscoderInput(file);
			trans.transcode(transIn, null);

		} catch (IOException | TranscoderException e) {
			throw new PersistanceException(path);
		}
		Image img = SwingFXUtils.toFXImage(trans.getBufferedImage(), null);

		view = recolor(img, view, color);
		return view;
	}

	public ImageView washingMachinIcon(ImageView view, double size, Paint color) {
		String path = pathProperties.getWashingMachine().toString();
		return setIcone(view, path, color, size);
	}

	public ImageView recolor(Image source, ImageView view, Paint color) {
		ColorAdjust monochrome = new ColorAdjust();
		monochrome.setSaturation(-1.0);
		Blend blush = new Blend(BlendMode.SRC_ATOP, monochrome,
				new ColorInput(0, 0, source.getWidth(), source.getHeight(), color));
		view.setImage(source);
		view.setEffect(blush);
		return view;
	}

	public WebView loadSVG() {
		WebView view = new WebView();
		view.setMinSize(50, 50);
		view.setPrefSize(50, 50);
		try {
			view.getEngine().load(pathProperties.getWashingMachine().getURL().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return view;
	}

}
