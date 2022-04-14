package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestGeoService {

    GeoService geoService;

    @BeforeEach
    public void beforeEachMethod() {
        geoService = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getParams")
    void test_getLocationByIp(String ip, Location expected) {
        // given: arrange

        // when: act
        Location location = geoService.byIp(ip);

        // then: assert
        assertThat(expected.getCountry(), equalTo(location.getCountry()));
    }

    @Test
    void test_getLocationByCoordinates() {
        // given: arrange
        double latitude = 10.1, longitude = 25.16;
        Class<RuntimeException> expected = RuntimeException.class;

        // when: act
        // then: assert
        Assertions.assertThrowsExactly(expected, () -> geoService.byCoordinates(latitude, longitude));
    }

    private static Stream<Arguments> getParams() {
        return Stream.of(
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.10.11.12", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.4.111.147", new Location("New York", Country.USA, null,  0))
        );
    }
}
