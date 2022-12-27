package org.mycompany.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.json.simple.JsonObject;
import org.mycompany.model.Test;
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
public class TestController {

	@Autowired
	ITestRepository itr;

	@GetMapping("/getTest/{id}")
	public Test getTest(@PathVariable int id) {
		return itr.findById(id).get();
	}

	@GetMapping("/getTests")
	public List<Test> getTests() {
		return itr.findAll();
	}

	@PostMapping("/saveTest")
	public void saveTest(@RequestBody Test pro) {
		itr.save(pro);
	}

	@DeleteMapping("/deleteTest/{id}")
	public void deleteTest(@PathVariable int id) {
		itr.deleteById(id);
	}

	@PutMapping("/updateTest{id}")
	public Test updateTest(@RequestBody Test newTest, @PathVariable int id) {
		return itr.findById(id).map(Test -> {
			Test.setId(newTest.getId());
			Test.setSujet(newTest.getSujet());
			Test.setValide(newTest.getValide());
			Test.setListeCandidats(newTest.getListeCandidats());
			Test.setListeEntreprises(newTest.getListeEntreprises());
			return itr.save(Test);
		}).orElseGet(() -> {
			return itr.save(newTest);
		});
	}

	public String testToJsonString(Test test) {
		JsonObject tJSON = new JsonObject();
		tJSON.put("id", test.getId());
		tJSON.put("sujet", test.getSujet());
		tJSON.put("valide", test.getValide());
		tJSON.put("listeCandidats", new ArrayList<>());
		tJSON.put("listeEntreprises", new ArrayList<>());
		String output = tJSON.toJson().toString();
		return output;
	}

	public JsonObject testToJson(Test test) {
		JsonObject tJSON = new JsonObject();
		tJSON.put("id", test.getId());
		tJSON.put("sujet", test.getSujet());
		tJSON.put("valide", test.getValide());
		tJSON.put("listeCandidats", new ArrayList<>());
		tJSON.put("listeEntreprises", new ArrayList<>());
		return tJSON;
	}

}
