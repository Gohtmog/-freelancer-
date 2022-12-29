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
import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
import org.mycompany.model.Notes;
import org.mycompany.model.Projet;
import org.mycompany.model.Test;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ICandidatRepository;
import org.mycompany.repo.IEntrepriseRepository;
import org.mycompany.repo.INotesRepository;
import org.mycompany.repo.IProjetRepository;
import org.mycompany.repo.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bazaarvoice.jolt.modifier.function.Math.intSum;

@RestController
public class CandidatController {
	Scanner scan = new Scanner(System.in);
	private int count = 0;
	private static String url = "tcp://194.206.91.85:61616";

	@Autowired
	ProjetController pc;

	@Autowired
	TestController tc;

	@Autowired
	CVController cc;

	@Autowired
	NotesController nc;

	@Autowired
	ICandidatRepository icr;

	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	IProjetRepository ipr;

	@Autowired
	ICVRepository icvr;

	@Autowired
	IEntrepriseRepository ier;

	@Autowired
	INotesRepository inr;

	@Autowired
	ITestRepository itr;

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
//			Candidat.setListeTest(newCandidat.getListeTest());
			Candidat.setListeProjet(newCandidat.getListeProjet());
			Candidat.setListeCV(newCandidat.getListeCV());
			Candidat.setListeNotes(newCandidat.getListeNotes());
			return icr.save(Candidat);
		}).orElseGet(() -> {
			return icr.save(newCandidat);
		});
	}

	@GetMapping("/lancerRouteCandidat")
	public void lanceRoute() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.createConnection("admin", "adaming2022");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.start();
		producerTemplate.sendBody("direct:startCandidat", null);
		context.stop();
	}

	@GetMapping("/CandidatToJSON")
	public void CandidatToJSONFile(@RequestBody Candidat can) {

		JsonObject CanJSON = candidatToJSON(can);
		String adresse = "inputCandidat/envoiCandidat" + count + ".json";

		try (FileWriter file = new FileWriter(adresse)) {
			String output = CanJSON.toJson().toString();
			file.write(output);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String candidatToJSONString(Candidat can) {
		JsonObject candidatJSON = new JsonObject();
		candidatJSON.put("id", can.getId());
		candidatJSON.put("nom", can.getNom());
		candidatJSON.put("prenom", can.getPrenom());
		candidatJSON.put("moyNotes", can.getMoyNotes());
		candidatJSON.put("listeCV", can.getListeCV());
		candidatJSON.put("listeProjets", can.getListeProjet());
//		candidatJSON.put("listeTest", can.getListeTest());
		candidatJSON.put("listeNotes", can.getListeNotes());
		String output = candidatJSON.toJson().toString();
		return output;
	}

	public JsonObject candidatToJSON(Candidat can) {
		JsonObject candidatJSON = new JsonObject();
		candidatJSON.put("id", can.getId());
		candidatJSON.put("nom", can.getNom());
		candidatJSON.put("prenom", can.getPrenom());
		candidatJSON.put("moyNotes", can.getMoyNotes());

		List<CV> listeCV = can.getListeCV();
		List<JsonObject> listeCVJSON = new ArrayList<>();
		for (CV cv : listeCV) {

			System.out.println("Passage dans la liste de CVs.");
			JsonObject cvJSON = cc.CVToJSON(cv);
			listeCVJSON.add(cvJSON);
		}

		List<Projet> listeProjet = can.getListeProjet();
		List<JsonObject> listeProjetJSON = new ArrayList<>();
		for (Projet pro : listeProjet) {

			System.out.println("Passage dans la liste de Projets.");
			System.out.println(pro.toString());
			JsonObject projetJSON = pc.projetToJSON(pro);
			listeProjetJSON.add(projetJSON);
		}

//		List<Test> listeTest = can.getListeTest();
//		List<JsonObject> listeTestJSON = new ArrayList<>();
//		for (Test test : listeTest) {
//			JsonObject testJSON = tc.testToJson(test);
//			listeTestJSON.add(testJSON);
//		}

		List<Notes> listeNotes = can.getListeNotes();
		List<JsonObject> listeNotesJSON = new ArrayList<>();
		for (Notes notes : listeNotes) {
			System.out.println("Passage dans la liste de notes.");
			JsonObject notesJSON = nc.notesToJSON(notes);
			listeNotesJSON.add(notesJSON);
		}

		candidatJSON.put("listeCV", listeCVJSON);
		candidatJSON.put("listeProjet", listeProjetJSON);
//		candidatJSON.put("listeTest", listeTestJSON);
		candidatJSON.put("listeNotes", listeNotesJSON);
//		String output = candidatJSON.toJson().toString();
//		System.out.println(candidatJSON);
		return candidatJSON;
	}

	public Candidat promptCandidat() {
		List<Candidat> listeCA = this.getCandidats();
		int nouvelID = listeCA.size() + 1;

		System.out.println("Rentrez le nom de votre Candidat svp");
		String nom = scan.nextLine();
		System.out.println("Rentrez le prenom de votre Candidat svp");
		String pren = scan.nextLine();

		List<CV> listeCV = new ArrayList<>();
//		System.out.println("Souhaitez-vous ajouter un CV à ce candidat ? 1 pour oui, autre pour non.");
//		int selCV = scan.nextInt();
//		if (selCV == 1) {
//			do {
//				System.out.println("Quel est l'identifiant du CV en question ?");
//				int idCV = scan.nextInt();
//				CV cv = icvr.findById(idCV).get();
//				listeCV.add(cv);
//
//				System.out.println("Souhaitez-vous ajouter un autre CV à ce candidat ? 1 pour oui, autre pour non.");
//				selCV = scan.nextInt();
//			} while (selCV == 1);
//		}

		List<Projet> listeProjets = new ArrayList<>();
//		System.out.println("Souhaitez-vous ajouter un projet à ce candidat ? 1 pour oui, autre pour non.");
//		int selPro = scan.nextInt();
//		if (selPro == 1) {
//			do {
//				System.out.println("Quel est l'identifiant du projet en question ?");
//				int idP = scan.nextInt();
//				Projet pro = ipr.findById(idP).get();
//				listeProjets.add(pro);
//
//				System.out
//						.println("Souhaitez-vous ajouter un autre projet à ce candidat ? 1 pour oui, autre pour non.");
//				selPro = scan.nextInt();
//			} while (selPro == 1);
//		}

//		List<Test> listeTests = new ArrayList<>();
//		System.out.println("Souhaitez-vous ajouter un test à ce candidat ? 1 pour oui, autre pour non.");
//		int selTest = scan.nextInt();
//		do {
//			System.out.println("Quel est l'identifiant du test en question ?");
//			int idT = scan.nextInt();
//			Test test = itr.findById(idT).get();
//			listeTests.add(test);
//
//			System.out.println("Souhaitez-vous ajouter un autre test à ce candidat ? 1 pour oui, autre pour non.");
//			selTest = scan.nextInt();
//		} while (selTest == 1);

		List<Notes> listeNotes = new ArrayList<>();
//		System.out.println("Souhaitez-vous ajouter une note à ce candidat ? 1 pour oui, autre pour non.");
//		int selNote = scan.nextInt();
//		if (selNote == 1) {
//			do {
//				System.out.println("Quel est l'identifiant de la note en question ?");
//				int idN = scan.nextInt();
//				Notes note = inr.findById(idN).get();
//				listeNotes.add(note);
//
//				System.out.println("Souhaitez-vous ajouter une autre note à ce candidat ? 1 pour oui, autre pour non.");
//				selNote = scan.nextInt();
//			} while (selNote == 1);
//		}
		
//		System.out.println("test");
//		System.out.println(listeCV);
//		System.out.println(listeProjets);
//		System.out.println(listeNotes);
		


//		listeProjets.add(pro);
		Candidat ca = new Candidat(nouvelID, nom, pren, listeProjets, listeCV, listeNotes);
//		System.out.println(ca.toString());
		icr.save(ca);
		return ca;
	}

}
