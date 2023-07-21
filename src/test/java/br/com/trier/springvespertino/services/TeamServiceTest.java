package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
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
public class TeamServiceTest extends BaseTest {
    @Autowired
   TeamService service;

    @Test
    @Sql({"/sqls/equipe.sql"})
    void deveBuscarEquipePorId() {
        Team team = service.findById(2);
        assertNotNull(team);
        assertEquals(2, team.getId());
        assertEquals("Ferrari", team.getName());

    }

    @Test
    @Sql({"/sqls/equipe.sql"})
    void deveRetornarEquipes() {
        List<Team> teams = service.listAll();
        teams = teams.stream()
                .sorted(Comparator.comparingInt(Team::getId))
                .collect(Collectors.toList());
        assertEquals(2, teams.size());
        assertEquals("Ferrari", teams.get(0).getName());
        assertEquals("Red Bull", teams.get(1).getName());

    }

    @Test
    void deveInserirUmaEquipe() {
        Team team = new Team(null, "Mercedes");
        team = service.salvar(team);
        assertEquals(1, team.getId());
        assertEquals("Mercedes", team.getName());
    }

    @Test
    @Sql({"/sqls/equipe.sql"})
    void deveAlterarEquipe() {
        Team team = service.findById(2);
        assertEquals("Ferrari", team.getName());
        Team teamUpdate = new Team(2, "Mclaren");
        team = service.update(teamUpdate);
        assertEquals("Mclaren", team.getName());
    }

    @Test
    @Sql({"/sqls/equipe.sql"})
    void deveRemoverEquipe() {
        service.delete(2);
        List<Team> lista = service.listAll();
        assertEquals(1, lista.size());
        assertEquals(3, lista.get(0).getId());
    }
    @Test
    @Sql({"/sqls/equipe.sql"})
    void deveRetornarEquipeParteNome() {
        List<Team> teams = service.findByNameContains("Red");
        assertEquals(1,teams.size());
        assertEquals("Red Bull",teams.get(0).getName());
    }
    @Test
    @Sql({"/sqls/equipe.sql"})
    void deveRetornarEquipeNomeIgnoreCase() {
        List<Team> teams = service.findByNameIgnoreCase("ferrari");
        assertEquals(1,teams.size());
        assertEquals("Ferrari",teams.get(0).getName());
    }
}
