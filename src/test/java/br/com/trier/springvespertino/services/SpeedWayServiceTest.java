package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.models.Speedway;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class SpeedWayServiceTest extends BaseTest {

    @Autowired
    SpeedwayService service;

    @Test
    @Sql({"/sqls/pista.sql"})
    void deveBuscarPistaPorId() {
        Speedway speedway = service.findById(12);
        assertNotNull(speedway);
        assertEquals(12, speedway.getId());
        assertEquals("Interlagos", speedway.getName());
        assertEquals("Brazil", speedway.getCountry().getName());
        assertEquals(1000, speedway.getSize());


    }

    @Test
    @Sql({"/sqls/pista.sql"})
    void deveRetornarPistas() {
        List<Speedway> speedways = service.listAll();
        speedways = speedways.stream()
                .sorted(Comparator.comparingInt(Speedway::getId))
                .collect(Collectors.toList());
        assertEquals(2, speedways.size());
        assertEquals("Interlagos", speedways.get(0).getName());
        assertEquals("Singapura", speedways.get(1).getName());

    }

    @Test
    @Sql({"/sqls/pista.sql"})
    void deveInserirUmaPista() {
        Country country = new Country(3,"Japan");
        Speedway speedway = new Speedway(null, "Suzuca",1500,country);
        speedway = service.insert(speedway);
        assertEquals(2, speedway.getId());
        assertEquals("Suzuca", speedway.getName());
        assertEquals(1500, speedway.getSize());
        assertEquals("Japan", speedway.getCountry().getName());
    }

    @Test
    @Sql({"/sqls/pista.sql"})
    void deveAlterarPista() {
        Speedway speedway = service.findById(12);
        assertEquals(12, speedway.getId());
        Speedway speedwayUpdate = new Speedway(12,speedway.getName(),3500,speedway.getCountry());
        speedway = service.update(speedwayUpdate);
        assertEquals(3500, speedway.getSize());
    }

    @Test
    @Sql({"/sqls/pista.sql"})
    void deveRemoverPista() {
        service.delete(12);
        List<Speedway> lista = service.listAll();
        assertEquals(1, lista.size());
        assertEquals(13, lista.get(0).getId());
    }
    @Test
    @Sql({"/sqls/pista.sql"})
    void deveRetornarPistaPorPais() {
        Country country = new Country(3,"Japan");
        Speedway speedway = new Speedway(null, "Suzuca",1500,country);
        service.insert(speedway);
        List<Speedway> speedways = service.findByCountryOrderBySizeDesc(country);
        assertEquals(2,speedways.size());
        assertEquals(13,speedways.get(0).getId());
        assertEquals(2000,speedways.get(0).getSize());
        assertEquals(1,speedways.get(1).getId());
        assertEquals(1500,speedways.get(1).getSize());

    }
    @Test
    @Sql({"/sqls/pista.sql"})
    void deveRetornarPistaPorNome() {
        List<Speedway> speedways = service.findByNameStartsWithIgnoreCase("inter");
        assertEquals(1,speedways.size());
        assertEquals("Interlagos",speedways.get(0).getName());
    }
    @Test
    @Sql({"/sqls/pista.sql"})
    void deveRetornarPistaPorData() {
        List<Speedway> speedways = service.findBySizeBetween(1500,2500);
        assertEquals(1,speedways.size());
        assertEquals(13,speedways.get(0).getId());
        assertEquals("Singapura",speedways.get(0).getName());
    }
}
