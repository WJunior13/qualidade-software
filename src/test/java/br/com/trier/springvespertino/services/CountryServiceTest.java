package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Country;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class CountryServiceTest extends BaseTest {

    @Autowired
    CountryService service;

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveBuscarPaisPorId() {
        Country country = service.findById(2);
        assertNotNull(country);
        assertEquals(2, country.getId());
        assertEquals("Brazil", country.getName());

    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRetornarPaises() {
        List<Country> countries = service.listAll();
        countries = countries.stream()
                .sorted(Comparator.comparingInt(Country::getId))
                .collect(Collectors.toList());
        assertEquals(2, countries.size());
        assertEquals("Brazil", countries.get(0).getName());
        assertEquals("France", countries.get(1).getName());

    }

    @Test
    void deveInserirUmPais() {
        Country country = new Country(null, "Japan");
        country = service.salvar(country);
        assertEquals(1, country.getId());
        assertEquals("Japan", country.getName());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveAlterarPais() {
        Country country = service.findById(2);
        assertEquals("Brazil", country.getName());
        Country countryUpdate = new Country(2, "Germany");
        country = service.update(countryUpdate);
        assertEquals("Germany", country.getName());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRemoverPais() {
        service.delete(2);
        List<Country> lista = service.listAll();
        assertEquals(1, lista.size());
        assertEquals(3, lista.get(0).getId());
    }
    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRetornarPaisNome() {
        List<Country> countries = service.findByNomeEqualsIgnoreCase("brazil");
        assertEquals(1,countries.size());
    }
}
