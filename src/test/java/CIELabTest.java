import fr.vbillard.tissusdeprincesseboot.service.CIELab;
import javafx.scene.paint.Color;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CIELabTest {


    @ParameterizedTest
    @MethodSource("parameters")
    public void testCielAb(Color colorA, Color colorB, boolean expected){
        CIELab cieLab = CIELab.getInstance();
        double distance = cieLab.distance(colorA, colorB);
        System.out.println(distance);
        assertEquals(expected, distance < 50);
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(Color.RED, Color.GREEN, false),
                Arguments.of(Color.RED, Color.DARKRED, true),
                Arguments.of(Color.RED, Color.ORANGE, false),
                Arguments.of(Color.RED, Color.ORANGERED, true),
                Arguments.of(Color.RED, Color.BLUE, false)
        );
    }
}
