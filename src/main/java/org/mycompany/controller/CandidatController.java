package org.mycompany.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.json.simple.JsonObject;
import org.mycompany.model.Candidat;
import org.mycompany.model.Projet;
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

	public String candidatToJSONString(Candidat can) {
		JsonObject candidatJSON = new JsonObject();
		candidatJSON.put("id", can.getId());
		candidatJSON.put("nom", can.getNom());
		candidatJSON.put("prenom", can.getPrenom());
		candidatJSON.put("moyNotes", can.getMoyNotes());
		candidatJSON.put("listeCV", "[]");
		candidatJSON.put("listeProjets", "[]");
		candidatJSON.put("listeTest", "[]");
		candidatJSON.put("listeNotes", "[]");
		String output = candidatJSON.toJson().toString();
		return output;
	}

	public JsonObject candidatToJSON(Candidat can) {
		JsonObject candidatJSON = new JsonObject();
		candidatJSON.put("id", can.getId());
		candidatJSON.put("nom", can.getNom());
		candidatJSON.put("prenom", can.getPrenom());
		candidatJSON.put("moyNotes", can.getMoyNotes());
		candidatJSON.put("listeCV", new ArrayList<>());
		candidatJSON.put("listeProjet", new ArrayList<>());
		candidatJSON.put("listeTest", new ArrayList<>());
		candidatJSON.put("listeNotes", new ArrayList<>());
//		String output = candidatJSON.toJson().toString();
//		System.out.println(candidatJSON);
		return candidatJSON;
	}

}
