import fr.vbillard.tissusdeprincesseboot.utils.color.ColorUtils;
import javafx.scene.paint.Color;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CIELabTest {

    @ParameterizedTest
    @MethodSource("parameters")
    void testCielAb(Color colorA, Color colorB, boolean expectedClose, boolean expectedSomewhat){
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
}
