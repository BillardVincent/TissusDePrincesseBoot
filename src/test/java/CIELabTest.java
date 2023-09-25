import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import fr.vbillard.tissusdeprincesseboot.utils.color.LabColor;
import javafx.scene.paint.Color;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CIELabTest {


    @ParameterizedTest
    @MethodSource("parameters")
    public void testCielAb(Color colorA, Color colorB, boolean expectedClose, boolean expectedSomewhat){
        double distance = ColorUtils.distance(colorA, colorB);
        System.out.println(colorA + " -> " + colorB +" = " + distance);
        assertEquals(expectedClose, distance < 50);
        assertEquals(expectedSomewhat, distance < 100);
    }

    // 80 ?

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(Color.RED, Color.GREEN, false, false),
                Arguments.of(Color.RED, Color.DARKRED, true, true),
                Arguments.of(Color.RED, Color.ORANGE, false, true),
                Arguments.of(Color.RED, Color.ORANGERED, true, true),
                Arguments.of(Color.RED, Color.BLUE, false, false),
                Arguments.of(Color.RED, Color.web("FF1010"), true, true),
                Arguments.of(Color.RED, Color.web("EE1010"), true, true),
                Arguments.of(Color.RED, Color.web("C72727"), true, true),
                Arguments.of(Color.RED, Color.web("AFE1AF"), false, false),

                Arguments.of(Color.web("d20724"), Color.web("96c896"), false, false));
    }

    @ParameterizedTest
    @MethodSource("parameters2")
    public void compareCielabMethods(Color c){
        LabColor lab1 = ColorUtils.rgbToLab(c);
        //LabColor lab2 = ColorUtils.rgb2lab(c);
        LabColor lab3 = ColorUtils.adobeToLab(c);

        System.out.println(lab1 +" - "+ lab3);


    }

    private static Stream<Arguments> parameters2() {
        return Stream.of(
                Arguments.of(Color.RED),
                Arguments.of(Color.GREEN),
                Arguments.of(Color.DARKRED),
                Arguments.of(Color.ORANGE),
                Arguments.of(Color.ORANGERED),
                Arguments.of(Color.BLUE),
                Arguments.of(Color.web("FF1010")),
                Arguments.of(Color.web("EE1010")),
                Arguments.of(Color.web("C72727")),
                Arguments.of(Color.web("AFE1AF")),

                Arguments.of(Color.web("d20724")),
                Arguments.of(Color.web("96c896")));
    }

}
