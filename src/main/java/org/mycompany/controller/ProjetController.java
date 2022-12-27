package org.mycompany.controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.json.simple.JsonObject;
import org.mycompany.model.Candidat;
import org.mycompany.model.Entreprise;
import org.mycompany.model.Notes;
import org.mycompany.model.Projet;
import org.mycompany.repo.IEntrepriseRepository;
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
	Scanner scan = new Scanner(System.in);
	private int count = 0;
	private static String url = "tcp://194.206.91.85:61616";

	@Autowired
	IProjetRepository ier;

	@Autowired
	IEntrepriseRepository ienr;

	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	EntrepriseController eco;

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

	@GetMapping("/lancerRouteProjet")
	public void lanceRoute() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.createConnection("admin", "adaming2022");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.start();
		producerTemplate.sendBody("direct:startProjet", null);
		context.stop();
	}

	@GetMapping("/ProjetToJSON")
	public void ProjetToJSONFile(@RequestBody Projet pro) {

		JsonObject ProJSON = projetToJSON(pro);
		String adresse = "inputProjet/envoiProjet" + count + ".json";

		try (FileWriter file = new FileWriter(adresse)) {
			String output = ProJSON.toJson().toString();
			file.write(output);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String projetToJSONString(Projet pro) {
		JsonObject proJSON = new JsonObject();
		proJSON.put("id", pro.getId());
		proJSON.put("intitule", pro.getIntitule());
		proJSON.put("salaire", pro.getSalaire());
		proJSON.put("duree", pro.getDuree());
		proJSON.put("tailleEquipe", pro.getTailleEquipe());
		proJSON.put("entreprise", eco.entrepriseToJSON(pro.getEntreprise()));
		proJSON.put("listeCandidats", new ArrayList<>());
		String output = proJSON.toJson().toString();
		return output;
	}

	public JsonObject projetToJSON(Projet pro) {
		JsonObject proJSON = new JsonObject();
		proJSON.put("id", pro.getId());
		proJSON.put("intitule", pro.getIntitule());
		proJSON.put("salaire", pro.getSalaire());
		proJSON.put("duree", pro.getDuree());
		proJSON.put("tailleEquipe", pro.getTailleEquipe());
		proJSON.put("entreprise", eco.entrepriseToJSON(pro.getEntreprise()));
		proJSON.put("listeCandidats", new ArrayList<>());
		return proJSON;
	}

	public Projet promptProjet() {
		List<Projet> listeProjets = this.getProjets();
		int nouvelID = listeProjets.size() + 1;

		System.out.println("Rentrez l'intitulé du projet svp : ");
		String intitule = scan.nextLine();

		System.out.println("Quel sera le salaire pour les collaborateurs ?");
		double salaire = scan.nextDouble();

		System.out.println("Quelle sera la durée du projet en semaines ?");
		double duree = scan.nextInt();

		System.out.println("Combien de collaborateurs sont requis ? ");
		int tailleEquipe = scan.nextInt();

		System.out.println("Quel est l'identifiant de l'entreprise menant le projet ?");
		int idE = scan.nextInt();
		Entreprise ent = ienr.findById(idE).get();

		List<Candidat> lC = new ArrayList<>();

		Projet pro = new Projet(idE, intitule, salaire, duree, tailleEquipe, ent, lC);

		return pro;
	}

}
