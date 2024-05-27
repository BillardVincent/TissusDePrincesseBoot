package fr.vbillard.tissusdeprincesseboot.controller.components.style;

import javafx.scene.paint.Color;

public record StyleColor(
        Color color,
        Color contrast,
        StyleColorEnum type) {
}
