package org.mycompany.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.json.simple.JsonObject;
import org.mycompany.model.Entreprise;
import org.mycompany.repo.IEntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntrepriseController {

	@Autowired
	IEntrepriseRepository ier;

	@GetMapping("/getEntreprise/{id}")
	public Entreprise getEntreprise(@PathVariable int id) {
		return ier.findById(id).get();
	}

	@GetMapping("/getEntreprises")
	public List<Entreprise> getEntreprises() {
		return ier.findAll();
	}

	@PostMapping("/saveEntreprise")
	public void saveEntreprise(@RequestBody Entreprise ent) {
		ier.save(ent);
	}

	@DeleteMapping("/deleteEntreprise/{id}")
	public void deleteEntreprise(@PathVariable int id) {
		ier.deleteById(id);
	}

	@PutMapping("/updateEntreprise{id}")
	public Entreprise updateEntreprise(@RequestBody Entreprise newEntreprise, @PathVariable int id) {
		return ier.findById(id).map(Entreprise -> {
			Entreprise.setId(newEntreprise.getId());
			Entreprise.setNom(newEntreprise.getNom());
			Entreprise.setCapital(newEntreprise.getCapital());
			Entreprise.setMoyNotes(newEntreprise.getMoyNotes());
			Entreprise.setListeNotes(newEntreprise.getListeNotes());
			Entreprise.setTaille(newEntreprise.getTaille());
			Entreprise.setListeProjets(newEntreprise.getListeProjets());
			Entreprise.setListeTests(newEntreprise.getListeTests());
			return ier.save(Entreprise);
		}).orElseGet(() -> {
			return ier.save(newEntreprise);
		});
	}

	public String entrepriseToJSONString(Entreprise ent) {
		JsonObject entJSON = new JsonObject();
		entJSON.put("id", ent.getId());
		entJSON.put("nom", ent.getNom());
		entJSON.put("capital", ent.getCapital());
		entJSON.put("moyNotes", ent.getMoyNotes());
		entJSON.put("taille", ent.getTaille());
		entJSON.put("listeNotes", new ArrayList<>());
		entJSON.put("listeProjets", new ArrayList<>());
		entJSON.put("listeTests", new ArrayList<>());
		String output = entJSON.toJson().toString();
		return output;
	}

	public JsonObject entrepriseToJSON(Entreprise ent) {
		JsonObject entJSON = new JsonObject();
		entJSON.put("id", ent.getId());
		entJSON.put("nom", ent.getNom());
		entJSON.put("capital", ent.getCapital());
		entJSON.put("moyNotes", ent.getMoyNotes());
		entJSON.put("listeNotes", new ArrayList<>());
		entJSON.put("taille", ent.getTaille());
		entJSON.put("listeProjets", new ArrayList<>());
		entJSON.put("listeTests", new ArrayList<>());
		return entJSON;
	}

}
