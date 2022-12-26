package org.mycompany.controller;

import java.util.List;

import org.mycompany.model.Projet;
import org.mycompany.repo.IProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjetController {

	@Autowired
	IProjetRepository ier;

	@GetMapping("/getProjet/{id}")
	public Projet getProjet(@PathVariable int id) {
		return ier.findById(id).get();
	}

	@GetMapping("/getProjets")
	public List<Projet> getProjets() {
		return ier.findAll();
	}

	@PostMapping("/saveProjet")
	public void saveProjet(@RequestBody Projet pro) {
		ier.save(pro);
	}

	@DeleteMapping("/deleteProjet/{id}")
	public void deleteProjet(@PathVariable int id) {
		ier.deleteById(id);
	}

	@PutMapping("/updateProjet{id}")
	public Projet updateProjet(@RequestBody Projet newProjet, @PathVariable int id) {
		return ier.findById(id).map(Projet -> {
			Projet.setId(newProjet.getId());
			Projet.setIntitule(newProjet.getIntitule());
			Projet.setSalaire(newProjet.getSalaire());
			Projet.setDuree(newProjet.getDuree());
			Projet.setTailleEquipe(newProjet.getTailleEquipe());
			Projet.setEntreprise(newProjet.getEntreprise());
			Projet.setListeCandidats(newProjet.getListeCandidats());
			return ier.save(Projet);
		}).orElseGet(() -> {
			return ier.save(newProjet);
		});
	}

}
