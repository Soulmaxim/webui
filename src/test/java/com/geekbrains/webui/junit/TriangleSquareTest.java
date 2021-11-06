package com.geekbrains.webui.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TriangleSquareTest {
    private static Logger logger = LoggerFactory.getLogger(TriangleSquareTest.class);

    @ParameterizedTest
    @MethodSource("correctTriangleDataProvider")
    @DisplayName("Нахождение площади корректных треугольников")
    void getSquarePositiveTest(Triangle triangle, double square) throws NotTriangleException {
        logger.debug("Текущий треугольник: " + triangle);
        assertThat(triangle.getSquare()).isEqualTo(square);
    }

    private static Stream<Arguments> correctTriangleDataProvider() {
        return Stream.of(
                Arguments.arguments(new Triangle(3.0, 4.0, 5.0), 6.0),
                Arguments.arguments(new Triangle(6.5, 6.5, 5.0), 15.0));
    }

    @Test
    @DisplayName("Нахождение площади корректного треугольника с иррациональной площадью")
    void getIrrationalSquareTest() throws NotTriangleException {
        Triangle triangle = new Triangle(3.5, 3.5, 3.5);
        logger.debug("Текущий треугольник: " + triangle);
        assertThat(triangle.getSquare()).isGreaterThan(5.30440).isLessThan(5.30441);
    }

    @ParameterizedTest
    @MethodSource("unCorrectTriangleDataProvider")
    @DisplayName("Нахождение площади некорректных треугольников")
    void exceptionWhenNotTriangleTest(Triangle triangle) {
        Assertions.assertAll(
                () -> Assertions.assertThrows(NotTriangleException.class, () -> triangle.getSquare()),
                () -> assertThatExceptionOfType(NotTriangleException.class).isThrownBy(() -> triangle.getSquare())
        );
    }

    private static Stream<Triangle> unCorrectTriangleDataProvider() {
        return Stream.of(
                new Triangle(-3.0, 4.0, 5.0),
                new Triangle(3.5, 6.1, 15.2),
                new Triangle(4.523, 0.0, 1.1));
    }
}
