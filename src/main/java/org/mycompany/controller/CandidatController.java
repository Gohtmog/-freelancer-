package org.mycompany.controller;

import java.util.List;

import org.mycompany.model.Candidat;
import org.mycompany.repo.ICandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidatController {

	@Autowired
	ICandidatRepository icr;

	@GetMapping("/getCandidat/{id}")
	public Candidat getCandidat(@PathVariable int id) {
		return icr.findById(id).get();
	}

	@GetMapping("/getCandidats")
	public List<Candidat> getCandidats() {
		return icr.findAll();
	}

	@PostMapping("/saveCandidat")
	public void saveCandidat(@RequestBody Candidat can) {
		icr.save(can);
	}

	@DeleteMapping("/deleteCandidat/{id}")
	public void deleteCandidat(@PathVariable int id) {
		icr.deleteById(id);
	}

	@PutMapping("/updateCandidat{id}")
	public Candidat updateCandidat(@RequestBody Candidat newCandidat, @PathVariable int id) {
		return icr.findById(id).map(Candidat -> {
			Candidat.setId(newCandidat.getId());
			Candidat.setNom(newCandidat.getNom());
			Candidat.setPrenom(newCandidat.getPrenom());
			Candidat.setMoyNotes(newCandidat.getMoyNotes());
			Candidat.setListeTest(newCandidat.getListeTest());
			Candidat.setListeProjet(newCandidat.getListeProjet());
			Candidat.setListeCV(newCandidat.getListeCV());
			Candidat.setListeNotes(newCandidat.getListeNotes());
			return icr.save(Candidat);
		}).orElseGet(() -> {
			return icr.save(newCandidat);
		});
	}

}
