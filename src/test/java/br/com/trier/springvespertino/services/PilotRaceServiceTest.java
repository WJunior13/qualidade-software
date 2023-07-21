package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.*;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class PilotRaceServiceTest extends BaseTest {

    @Autowired
    PilotRaceService service;

    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveBuscarPilotoCorridaPorId() {
        PilotRace pilotRace = service.findById(20);
        assertNotNull(pilotRace);
        Race race = pilotRace.getRace();
        Championship championship = race.getChampionship();
        Speedway speedway = race.getSpeedway();
        assertEquals(20, pilotRace.getId());
        assertEquals("Felipe Massa", pilotRace.getPilot().getName());
        assertEquals("F1 Race", championship.getDescription());
        assertEquals("Interlagos", speedway.getName());

    }

    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveRetornarTodasCorridasPilotos() {
        List<PilotRace> pilotRaces = service.listAll();
        assertEquals(2, pilotRaces.size());

    }

    @Test
    void deveInserirUmPilotoCorrida() {
        Pilot pilot = createPilot(null,"Vettel", createCountry(), createTeam());
        Race race = createRace(null);
        PilotRace pilotRace = new PilotRace(null,1,pilot, race);
        pilotRace = service.insert(pilotRace);
        assertEquals(1, pilotRace.getId());
        assertEquals("Vettel", pilotRace.getPilot().getName());
    }

    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveAlterarPosicaoPilotoCorrida() {
        PilotRace pilotRace = service.findById(20);
        assertEquals(1, pilotRace.getPlacement());
        Pilot pilot = pilotRace.getPilot();
        Race race = pilotRace.getRace();
        PilotRace pilotRaceUpdate = new PilotRace(20, 2,pilot,race);
        pilotRace = service.update(pilotRaceUpdate);
        assertEquals(2, pilotRace.getPlacement());
    }

    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveRemoverPilotoCorrida() {
        service.delete(20);
        List<PilotRace> lista = service.listAll();
        assertEquals(1, lista.size());

    }

    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveRetornarPilotoCorridaPorPiloto() {
        PilotRace pilotRace = service.findById(20);
      Pilot pilot = pilotRace.getPilot();
        List<PilotRace> byPilot = service.findByPilot(pilot);
        assertEquals(1,byPilot.size());
        assertEquals("Felipe Massa",byPilot.get(0).getPilot().getName());
    }
    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveRetornarPilotoCorridaPorPosicao() {
        List<PilotRace> byPilot = service.findByPlacement(1);
        assertEquals(1,byPilot.size());
        assertEquals("Felipe Massa",byPilot.get(0).getPilot().getName());
    }


    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveRetornarPilotoCorridaPorIntervaloPosicaoECorrida() {
        List<PilotRace> byPilot = service.findByPlacementBetweenAndRace(1,2,createRace(15));
        assertEquals(1,byPilot.size());
        assertEquals("Felipe Massa",byPilot.get(0).getPilot().getName());
    }

    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveRetornarPilotoCorridaPorPosicaoECorrida() {
        Pilot pilot = createPilot(10,"Felipe Massa", createCountry(), createTeam());
        PilotRace byPilotAndRace = service.findByPilotAndRace(pilot,createRace(15));
        assertEquals("Felipe Massa",byPilotAndRace.getPilot().getName());
    }
    @Test
    @Sql({"/sqls/piloto_corrida.sql"})
    void deveRetornarPilotoCorridaPorPosicaoNaCorrida() {
        List<PilotRace> byPilot = service.findByRaceOrderByPlacementAsc(createRace(15));
        assertEquals(2,byPilot.size());
        assertEquals("Felipe Massa",byPilot.get(0).getPilot().getName());
        assertEquals("Barrichelo",byPilot.get(1).getPilot().getName());
    }

    private Country createCountry() {
        return new Country(2, "Brazil");
    }

    private Race createRace(Integer id) {
        Championship championship = new Championship(2, "F1 Race", 2023);
        Speedway speedway = new Speedway(12, "Interlagos", 1000, createCountry());
        return new Race(id, ZonedDateTime.now(), speedway, championship);
    }

    private Team createTeam() {
        return new Team(11, "Ferrari");
    }

    private Pilot createPilot(Integer id,String name, Country country, Team team) {
        return new Pilot(id, name, country, team);
    }
}
