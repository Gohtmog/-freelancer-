package org.mycompany.controller;

import java.io.FileWriter;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.json.simple.JsonObject;
import org.mycompany.model.Notes;
import org.mycompany.model.NotesEntreprise;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ICandidatRepository;
import org.mycompany.repo.IEntrepriseRepository;
import org.mycompany.repo.INotesEntrepriseRepository;
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
public class NotesEntrepriseController {
	private int count = 0;
	private static String url = "tcp://194.206.91.85:61616";

	@Autowired
	ICandidatRepository icr;
	
	@Autowired
	EntrepriseController eco;
	
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
	INotesEntrepriseRepository iner;

	@Autowired
	CandidatController cco;

	@GetMapping("/getNotesEntreprise/{id}")
	public NotesEntreprise getNotesEntreprise(@PathVariable int id) {
		return iner.findById(id).get();
	}

	@GetMapping("/getAllNotesEntreprise")
	public List<NotesEntreprise> getNotesEntreprise() {
		return iner.findAll();
	}

	@PostMapping("/saveNotesEntreprise")
	public void saveNotes(@RequestBody NotesEntreprise notes) {
		iner.save(notes);
	}

	@DeleteMapping("/deleteNotesEntreprise/{id}")
	public void deleteNotesEntreprise(@PathVariable int id) {
		iner.deleteById(id);
	}

	@PutMapping("/updateNotesEntreprise{id}")
	public NotesEntreprise updateNotes(@RequestBody NotesEntreprise newNotes, @PathVariable int id) {
		return iner.findById(id).map(NotesEntreprise -> {
			NotesEntreprise.setId(newNotes.getId());
			NotesEntreprise.setNote(newNotes.getNote());
			NotesEntreprise.setEntreprise(newNotes.getEntreprise());
			return iner.save(NotesEntreprise);
		}).orElseGet(() -> {
			return iner.save(newNotes);
		});
	}
	
	@GetMapping("/lancerRouteNotesEntreprise")
	public void lanceRoute() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.createConnection("admin", "adaming2022");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.start();
		producerTemplate.sendBody("direct:startNotesEntreprise", null);
		context.stop();
	}
	
	@GetMapping("/NotesEntrepriseToJSON")
	public void NotesEntrepriseToJSONFile(@RequestBody NotesEntreprise notes) {

		JsonObject NotesEntrepriseJSON = notesEntrepriseToJSON(notes);
		String adresse = "inputNotes/envoiNotesEntreprise" + count + ".json";

		try (FileWriter file = new FileWriter(adresse)) {
			String output = NotesEntrepriseJSON.toJson().toString();
			file.write(output);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public String notesEntrepriseToJSONString(NotesEntreprise n) {
		JsonObject nj = new JsonObject();
		nj.put("id", n.getId());
		nj.put("note", n.getNote());
		nj.put("entreprise", eco.entrepriseToJSON(n.getEntreprise()));
		String output = nj.toJson().toString();
		return output;
	}

	public JsonObject notesEntrepriseToJSON(NotesEntreprise n) {
		JsonObject nj = new JsonObject();
		nj.put("id", n.getId());
		nj.put("note", n.getNote());
		nj.put("entreprise", eco.entrepriseToJSON(n.getEntreprise()));
		return nj;
	}

}
