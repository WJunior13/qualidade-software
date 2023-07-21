package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class ChampionshipServiceTest extends BaseTest {
    @Autowired
    ChampionshipService service;

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveBuscarCampeonatoPorId() {
        var championship = service.findById(2);
        assertNotNull(championship);
        assertEquals(2, championship.getId());
        assertEquals("F1 Race", championship.getDescription());
        assertEquals(2023, championship.getYear());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRetornarCampeonatos() {
        List<Championship> championships = service.listAll();
        championships = championships.stream()
                .sorted(Comparator.comparingInt(Championship::getId))
                .collect(Collectors.toList());
        assertEquals(2, championships.size());
        assertEquals("F1 Race", championships.get(0).getDescription());
        assertEquals("F2 Race", championships.get(1).getDescription());
        assertEquals(2023, championships.get(0).getYear());
        assertEquals(2021, championships.get(1).getYear());
    }

    @Test
    void deveInserirUmCampeonato() {
        Championship championship = new Championship(null, "F1 New Race", 2023);
        championship = service.insert(championship);
        assertEquals(1, championship.getId());
        assertEquals("F1 New Race", championship.getDescription());
        assertEquals(2023, championship.getYear());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveAlterarCampeonato() {
        Championship championship = service.findById(2);
        assertEquals("F1 Race", championship.getDescription());
        Championship championshipUpdate = new Championship(2, "New description", 2023);
        championshipUpdate = service.update(championshipUpdate);
        assertEquals("New description", championshipUpdate.getDescription());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRemoverCampeonato() {
        service.delete(2);
        List<Championship> lista = service.listAll();
        assertEquals(1, lista.size());
        assertEquals(3, lista.get(0).getId());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRetornarUmCampeonatoEntreDatas() {
        List<Championship> championshipList = service.findByYearBetween(2020, 2022);
        assertEquals(1, championshipList.size());
        assertEquals("F2 Race", championshipList.get(0).getDescription());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRetornarCampeonato2023() {
        List<Championship> championships = service.findByYear(2023);
        assertEquals(1, championships.size());
        assertEquals("F1 Race", championships.get(0).getDescription());

    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void naoDeveRetornarCampeonatoDescricao() {
        List<Championship> championships = service.findByDescriptionContainsIgnoreCase("f race");
        assertEquals(0, championships.size());
    }

    @Test
    @Sql({"/sqls/campeonato.sql"})
    void deveRetornarCampeonatoDescricaoEAno() {
        List<Championship> championships = service.findByescriptionContainsIgnoreCaseAndAnoEquals("f1 race",2023);
        assertEquals(1, championships.size());
        assertEquals("F1 Race", championships.get(0).getDescription());
    }
}
