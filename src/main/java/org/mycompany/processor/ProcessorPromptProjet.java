package org.mycompany.processor;

import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.ProjetController;
import org.mycompany.controller.CandidatController;
import org.mycompany.model.Projet;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptProjet implements Processor {

	@Autowired
	ProjetController ProjetController;

	@Override
	public void process(Exchange exchange) throws Exception {
		Projet projet = ProjetController.promptProjet();
		ProjetController.ProjetToJSONFile(projet);
		System.out.println("Fin du process");
	}
}
