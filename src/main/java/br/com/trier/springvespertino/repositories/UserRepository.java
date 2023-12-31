package br.com.trier.springvespertino.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findByNameStartingWithIgnoreCase(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);

}
