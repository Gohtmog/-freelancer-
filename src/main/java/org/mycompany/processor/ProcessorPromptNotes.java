package org.mycompany.processor;

import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.NotesController;
import org.mycompany.controller.CandidatController;
import org.mycompany.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptNotes implements Processor {

	@Autowired
	NotesController NotesController;

	@Override
	public void process(Exchange exchange) throws Exception {
		Notes notes = NotesController.promptNotes();
		NotesController.NotesToJSONFile(notes);
		System.out.println("Fin du process");
	}
}
