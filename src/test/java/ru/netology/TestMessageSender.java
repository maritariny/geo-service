package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TestMessageSender {

    @ParameterizedTest
    @MethodSource("getParams")
    void test_get_text(String ip, Location location) {

        Country country = location.getCountry();
        String expected = (country.equals(Country.RUSSIA) ? "Добро пожаловать" : "Welcome");

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country)).thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);

        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> getParams() {
        List<Arguments> listArguments = new ArrayList<>();
        int max = 200;
        Location locationRussia = new Location("Moscow", Country.RUSSIA, null, 0);
        Location locationUsa = new Location("New York", Country.USA, null,  0);

        for (int i = 0; i < 10; i++) {
            String ip = "96." + i + "." + (int) (Math.random() *  ++max) + "." + (int) (Math.random() *  ++max);
            listArguments.add(Arguments.of(ip, locationUsa));
        }
        for (int i = 0; i < 10; i++) {
            String ip = "172." + i + "." + (int) (Math.random() *  ++max) + "." + (int) (Math.random() *  ++max);
            listArguments.add(Arguments.of(ip, locationRussia));
        }
        listArguments.add(Arguments.of("127.11.0.1", new Location("Brazil", Country.BRAZIL, null,  0)));

        return listArguments.stream();
    }
}
