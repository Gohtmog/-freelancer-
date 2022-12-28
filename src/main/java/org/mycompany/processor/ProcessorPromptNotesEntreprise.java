package org.mycompany.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.NotesEntrepriseController;
import org.mycompany.model.NotesEntreprise;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptNotesEntreprise implements Processor {

	@Autowired
	NotesEntrepriseController NotesEntrepriseController;

	@Override
	public void process(Exchange exchange) throws Exception {
		NotesEntreprise notes = NotesEntrepriseController.promptNotesEntreprise();
		NotesEntrepriseController.NotesEntrepriseToJSONFile(notes);
		System.out.println("Fin du process");
	}
}
