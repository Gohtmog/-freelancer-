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
public class CVController {
	Scanner scan = new Scanner(System.in);

	private int count = 0;
	private static String url = "tcp://194.206.91.85:61616";

	@Autowired
	CandidatController cco;

	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	CandidatController cc;

	@Autowired
	ICandidatRepository icr;

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

	@GetMapping("/getCV/{id}")
	public CV getCV(@PathVariable int id) {
		return icvr.findById(id).get();
	}

	@GetMapping("/getCVs")
	public List<CV> getCVs() {
		return icvr.findAll();
	}

	@PostMapping("/saveCV")
	public void saveCV(@RequestBody CV cv) {
		icvr.save(cv);
	}

	@DeleteMapping("/deleteCV/{id}")
	public void deleteCV(@PathVariable int id) {
		icvr.deleteById(id);
	}

	@PutMapping("/updateCV{id}")
	public CV updateCV(@RequestBody CV newCV, @PathVariable int id) {
		return icvr.findById(id).map(CV -> {
			CV.setId(newCV.getId());
			CV.setDescription(newCV.getDescription());
			CV.setCandidat(newCV.getCandidat());
			return icvr.save(CV);
		}).orElseGet(() -> {
			return icvr.save(newCV);
		});
	}

	@GetMapping("/lancerRoute")
	public void lanceRoute() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.createConnection("admin", "adaming2022");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.start();
		producerTemplate.sendBody("direct:start", null);
		context.stop();
	}

	@GetMapping("/CVToJSON")
	public void CVToJSONFile(@RequestBody CV cv) {
//		CV cv = icr.findById(id).get();

		JsonObject CVJSON = new JsonObject();
		CVJSON.put("id", cv.getId());
		CVJSON.put("description", cv.getDescription());
		CVJSON.put("candidat", cco.candidatToJSON(cv.getCandidat()));

		String adresse = "inputfolder/envoi" + count + ".json";
		try (FileWriter file = new FileWriter(adresse)) {
			String output = CVJSON.toJson().toString();
			file.write(output);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JsonObject CVToJSON(CV cv) {
		JsonObject CVJSON = new JsonObject();
		CVJSON.put("id", cv.getId());
		CVJSON.put("description", cv.getDescription());
		CVJSON.put("candidat", cco.candidatToJSON(cv.getCandidat()));
		return CVJSON;

	}

	public String CVToJSONString(CV cv) {
		JsonObject CVJSON = new JsonObject();
		CVJSON.put("id", cv.getId());
		CVJSON.put("description", cv.getDescription());
		CVJSON.put("candidat", cco.candidatToJSON(cv.getCandidat()));
		return CVJSON.toJson().toString();

	}

	public CV promptCV() {
		List<CV> listeCV = this.getCVs();
		int nouvelID = listeCV.size() + 1;

		System.out.println("Rentrez le corps de votre CV svp");
		String corpsCV = scan.nextLine();

		System.out.println("Quel est votre identifiant de candidat svp ?");
		int idC = scan.nextInt();
		Candidat cand = cc.getCandidat(idC);

		CV cv = new CV(nouvelID, corpsCV, cand);
		return cv;
	}

}
