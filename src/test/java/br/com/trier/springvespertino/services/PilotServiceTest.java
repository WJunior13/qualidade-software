package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.Team;
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
public class PilotServiceTest extends BaseTest {
    @Autowired
    PilotService service;

    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveBuscarPilotoPorId() {
        Pilot pilot = service.findById(11);
        assertNotNull(pilot);
        assertEquals(11, pilot.getId());
        assertEquals("Barrichelo", pilot.getName());

    }

    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveRetornarPilotos() {
        List<Pilot> pilots = service.listAll();
        pilots = pilots.stream()
                .sorted(Comparator.comparingInt(Pilot::getId))
                .collect(Collectors.toList());
        assertEquals(2, pilots.size());
        assertEquals("Felipe Massa", pilots.get(0).getName());
        assertEquals("Barrichelo", pilots.get(1).getName());

    }

    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveInserirUmPiloto() {
        Pilot pilot = service.findById(11);
        Country country = pilot.getCountry();
        Team team = pilot.getTeam();

        Pilot newPilot = new Pilot(null, "Caca Bueno",country,team);
        newPilot = service.insert(newPilot);
        assertEquals(1, newPilot.getId());
        assertEquals("Caca Bueno", newPilot.getName());
        assertEquals("Red Bull", newPilot.getTeam().getName());
    }

    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveAlterarPiloto() {
        Pilot pilot = service.findById(10);
        Team newTeam = new Team(20,"Red Bull");
        assertEquals("Felipe Massa", pilot.getName());
        Pilot pilotUpdate = new Pilot(10, "Felipe Massa",pilot.getCountry(),newTeam);
        pilot = service.update(pilotUpdate);
        assertEquals("Red Bull", pilot.getTeam().getName());
    }

    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveRemoverPiloto() {
        service.delete(10);
        List<Pilot> lista = service.listAll();
        assertEquals(1, lista.size());
        assertEquals(11, lista.get(0).getId());
    }
    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveRetornarPilotosPorPais() {
        Country country = new Country(2,"Brazil");
        List<Pilot> pilots = service.findByCountry(country);
        assertEquals(2,pilots.size());
    }
    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveRetornarPilotoParteNomeIgnoreCase() {
        List<Pilot> pilots = service.findByNameStartsWithIgnoreCase("felipe");
        assertEquals(1,pilots.size());
        assertEquals("Felipe Massa",pilots.get(0).getName());
    }
    @Test
    @Sql({"/sqls/piloto.sql"})
    void deveRetornarPilotoPorEquipe() {
        Team team = new Team(20,"Red Bull");
        List<Pilot> pilots = service.findByTeam(team);
        assertEquals(1,pilots.size());
        assertEquals("Barrichelo",pilots.get(0).getName());
    }

}
