package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Race;
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
public class RaceServiceTest extends BaseTest {
    @Autowired
    RaceService service;

    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveBuscarCorridaPorId() {
        Race race = service.findById(15);
        assertNotNull(race);
        assertEquals(15, race.getId());
        assertEquals("F1 Race", race.getChampionship().getDescription());
        assertEquals("Interlagos", race.getSpeedway().getName());

    }

    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveRetornarCorridas() {
        List<Race> races = service.listAll();
        races = races.stream()
                .sorted(Comparator.comparingInt(Race::getId))
                .collect(Collectors.toList());
        assertEquals(2, races.size());
        assertEquals("F1 Race", races.get(0).getChampionship().getDescription());
        assertEquals("F2 Race", races.get(1).getChampionship().getDescription());

    }

    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveInserirUmaCorrida() {
        Country country = new Country(2,"Brazil");
        Speedway speedway = new Speedway(12,"Interlagos",1000,country);
        Championship championship = new Championship(2,"F1 Race",2023);
        Race race = new Race(null, ZonedDateTime.now(),speedway,championship);
        race = service.insert(race);
        assertEquals(1, race.getId());
        assertEquals("F1 Race", race.getChampionship().getDescription());
        assertEquals("Interlagos", race.getSpeedway().getName());
    }

    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveAlterarCorrida() {
        Race race = service.findById(15);
        assertEquals(15, race.getId());

        Championship championship = new Championship(3,"F2 Race",2020);
        Race raceUpdate = new Race(15,ZonedDateTime.parse("2020-07-20T12:34:56Z"),race.getSpeedway(),championship);
        race = service.update(raceUpdate);
        assertEquals("F2 Race", race.getChampionship().getDescription());
    }

    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveRemoverCorrida() {
        service.delete(15);
        List<Race> lista = service.listAll();
        assertEquals(1, lista.size());
        assertEquals(16, lista.get(0).getId());
    }
    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveRetornarCorridaPorCampeonato() {
        Championship championship = new Championship(3,"F2 Race",2020);
        List<Race> races = service.findByChampionship(championship);
        assertEquals(1,races.size());
        assertEquals(16,races.get(0).getId());
        assertEquals("F2 Race",races.get(0).getChampionship().getDescription());
    }
    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveRetornarCorridaPorPista() {
        Country country = new Country(2,"Brazil");
        Speedway speedway = new Speedway(12,"Interlagos",1000,country);
        List<Race> races = service.findBySpeedway(speedway);
        assertEquals(1,races.size());
        assertEquals("Interlagos",races.get(0).getSpeedway().getName());
    }
    @Test
    @Sql({"/sqls/corrida.sql"})
    void deveRetornarCorridaPorData() {
        List<Race> races = service.findByDate(ZonedDateTime.parse("2023-07-20T12:34:56Z"));
        assertEquals(1,races.size());
        assertEquals(15,races.get(0).getId());
        assertEquals("Interlagos",races.get(0).getSpeedway().getName());
    }
}
