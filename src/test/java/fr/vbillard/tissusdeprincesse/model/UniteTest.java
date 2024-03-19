package fr.vbillard.tissusdeprincesse.model;

import fr.vbillard.tissusdeprincesseboot.exception.IllegalData;
import fr.vbillard.tissusdeprincesseboot.model.enums.Unite;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
 class UniteTest {

    @ParameterizedTest
    @MethodSource("parameters")
    void convertir(float inputValue, Unite inputUnit, Unite outputUnit, float expectedResult){
       float result =  Unite.convertir(inputValue, inputUnit, outputUnit);
       assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("parametersTrows")
    void convertirTrows(float inputValue, Unite inputUnit, Unite outputUnit){
       assertThrows(IllegalData.class, () -> Unite.convertir(inputValue, inputUnit, outputUnit));
    }

   private static Stream<Arguments> parameters() {
      return Stream.of(
              Arguments.of(1f, Unite.CM, Unite.M, 0.01f),
              Arguments.of(1.23f , Unite.M, Unite.CM, 123f),
              Arguments.of(1.23f , Unite.CM, Unite.CM, 1.23f),
              Arguments.of(1.23f , Unite.M, Unite.NON_RENSEIGNE, 1.23f),
              Arguments.of(1.23f , Unite.NON_RENSEIGNE, Unite.CM, 1.23f)
      );
   }

   private static Stream<Arguments> parametersTrows() {
      return Stream.of(
              Arguments.of(1, Unite.L, Unite.M)
      );
   }

}
