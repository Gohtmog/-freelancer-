package org.mycompany.controller;

import java.io.FileWriter;
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
import org.mycompany.model.Entreprise;
import org.mycompany.model.Notes;
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

@RestController
public class NotesController {
	private int count = 0;
	private static String url = "tcp://194.206.91.85:61616";
	Scanner scan = new Scanner(System.in);

	@Autowired
	CandidatController cc;

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

	@Autowired
	CandidatController cco;

	@GetMapping("/getNotes/{id}")
	public Notes getNotes(@PathVariable int id) {
		return inr.findById(id).get();
	}

	@GetMapping("/getAllNotes")
	public List<Notes> getNotess() {
		return inr.findAll();
	}

	@PostMapping("/saveNotes")
	public void saveNotes(@RequestBody Notes notes) {
		inr.save(notes);
	}

	@DeleteMapping("/deleteNotes/{id}")
	public void deleteNotes(@PathVariable int id) {
		inr.deleteById(id);
	}

	@PutMapping("/updateNotes{id}")
	public Notes updateNotes(@RequestBody Notes newNotes, @PathVariable int id) {
		return inr.findById(id).map(Notes -> {
			Notes.setId(newNotes.getId());
			Notes.setNote(newNotes.getNote());
			Notes.setCandidat(newNotes.getCandidat());
			return inr.save(Notes);
		}).orElseGet(() -> {
			return inr.save(newNotes);
		});
	}
	
	@GetMapping("/lancerRouteNotes")
	public void lanceRoute() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.createConnection("admin", "adaming2022");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.start();
		producerTemplate.sendBody("direct:startNotes", null);
		context.stop();
	}
	
	@GetMapping("/NotesToJSON")
	public void NotesToJSONFile(@RequestBody Notes notes) {

		JsonObject NotesJSON = notesToJSON(notes);
		String adresse = "inputNotes/envoiNotes" + count + ".json";

		try (FileWriter file = new FileWriter(adresse)) {
			String output = NotesJSON.toJson().toString();
			file.write(output);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public String notesToJSONString(Notes n) {
		JsonObject nj = new JsonObject();
		nj.put("id", n.getId());
		nj.put("note", n.getNote());
		nj.put("candidat", cco.candidatToJSON(n.getCandidat()));
		String output = nj.toJson().toString();
		return output;
	}

	public JsonObject notesToJSON(Notes n) {
		JsonObject nj = new JsonObject();
		nj.put("id", n.getId());
		nj.put("note", n.getNote());
		nj.put("candidat", cco.candidatToJSON(n.getCandidat()));
		return nj;
	}
	public Notes promptNotes() {
		List<Notes> listeNotes = this.getNotess();
		int nouvelID = listeNotes.size() + 1;

		System.out.println("Rentrez la notes(sur 5) svp");
		int note = scan.nextInt();

		System.out.println("Quel est l'identifiant du candidat svp ?");
		int idC = scan.nextInt();
		Candidat cand = cc.getCandidat(idC);

		Notes no = new Notes(nouvelID, note, cand);
		return no;
	}
}
