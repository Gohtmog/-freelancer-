package org.mycompany.controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.json.simple.JsonObject;
import org.mycompany.model.Candidat;
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
	private int count = 0;
	private static String url = "tcp://194.206.91.85:61616";

	@Autowired
	IEntrepriseRepository ier;
	
	@Autowired
	ProducerTemplate producerTemplate;

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
	
	@GetMapping("/lancerRouteEntreprise")
	public void lanceRoute() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.createConnection("admin", "adaming2022");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.start();
		producerTemplate.sendBody("direct:startEntreprise", null);
		context.stop();
	}
	
	@GetMapping("/EntrepriseToJSON")
	public void EntrepriseToJSONFile(@RequestBody Entreprise ent) {

		JsonObject EntJSON = entrepriseToJSON(ent);
		String adresse = "inputEntreprise/envoiEntreprise" + count + ".json";

		try (FileWriter file = new FileWriter(adresse)) {
			String output = EntJSON.toJson().toString();
			file.write(output);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
