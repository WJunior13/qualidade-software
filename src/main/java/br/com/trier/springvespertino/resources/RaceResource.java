package br.com.trier.springvespertino.resources;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.SpeedwayService;

@RestController
@RequestMapping("/racers")
public class RaceResource {
	
	@Autowired
	private RaceService service;

	@Autowired
	private SpeedwayService speedwayService;
	
	@Autowired
	private ChampionshipService championshipService;

	@GetMapping("/{id}")
	public ResponseEntity<Race> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	ResponseEntity<Race> insert(@RequestBody Race race) {
		speedwayService.findById(race.getSpeedway().getId());
		championshipService.findById(race.getChampionship().getId());
		return ResponseEntity.ok(service.insert(race));
	}

	@GetMapping
	ResponseEntity<List<Race>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@PutMapping("/{id}")
	ResponseEntity<Race> update(@PathVariable Integer id, @RequestBody Race race) {
		speedwayService.findById(race.getSpeedway().getId());
		championshipService.findById(race.getChampionship().getId());
		race.setId(id);
		return ResponseEntity.ok(service.update(race));
	}

	@DeleteMapping
	ResponseEntity<Void> delete(Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/date/{date}")
	ResponseEntity<List<Race>> findByDate(@PathVariable String date) {
		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, formatter);
		return ResponseEntity.ok(service.findByDate(zonedDateTime));
	}

	@GetMapping("/speedway/{idSpeedway}")
	ResponseEntity<List<Race>> findBySpeedway(@PathVariable Integer idSpeedway) {
		return ResponseEntity.ok(service.findBySpeedway(speedwayService.findById(idSpeedway)));
	}
	
	@GetMapping("/championship/{idchampionship}")
	ResponseEntity<List<Race>> findByChampionship(@PathVariable Integer idchampionship) {
		return ResponseEntity.ok(service.findByChampionship(championshipService.findById(idchampionship)));
	}

}
