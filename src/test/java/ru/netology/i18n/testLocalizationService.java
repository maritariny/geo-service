package ru.netology.i18n;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class testLocalizationService {

    LocalizationServiceImpl localizationService;

    @BeforeEach
    public void beforeEachMethod() {
        localizationService = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getParams")
    void test_locale(Country country, String expected) {
        // given: arrange

        // when: act
        String result = localizationService.locale(country);

        // then: assert
        assertThat(result, equalTo(expected));
    }

    private static Stream<Arguments> getParams() {
        return Stream.of(
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.RUSSIA, "Добро пожаловать")
        );
    }
}
