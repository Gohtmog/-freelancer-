package org.mycompany.controller;

import java.util.List;

import org.mycompany.model.Notes;
import org.mycompany.repo.INotesRepository;
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
	INotesRepository ier;

	@GetMapping("/getNotes/{id}")
	public Notes getNotes(@PathVariable int id) {
		return ier.findById(id).get();
	}

	@GetMapping("/getAllNotes")
	public List<Notes> getNotess() {
		return ier.findAll();
	}

	@PostMapping("/saveNotes")
	public void saveNotes(@RequestBody Notes notes) {
		ier.save(notes);
	}

	@DeleteMapping("/deleteNotes/{id}")
	public void deleteNotes(@PathVariable int id) {
		ier.deleteById(id);
	}

	@PutMapping("/updateNotes{id}")
	public Notes updateNotes(@RequestBody Notes newNotes, @PathVariable int id) {
		return ier.findById(id).map(Notes -> {
			Notes.setId(newNotes.getId());
			Notes.setNote(newNotes.getNote());
			Notes.setCandidat(newNotes.getCandidat());
			return ier.save(Notes);
		}).orElseGet(() -> {
			return ier.save(newNotes);
		});
	}
	
}
