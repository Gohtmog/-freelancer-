package org.mycompany.controller;

import java.util.List;

import org.apache.camel.json.simple.JsonObject;
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

}
