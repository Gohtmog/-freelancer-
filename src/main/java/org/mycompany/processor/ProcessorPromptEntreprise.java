package org.mycompany.processor;

import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.EntrepriseController;
import org.mycompany.controller.CandidatController;
import org.mycompany.model.Entreprise;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptEntreprise implements Processor {

	@Autowired
	EntrepriseController EntrepriseController;

	@Override
	public void process(Exchange exchange) throws Exception {
		Entreprise entreprise = EntrepriseController.promptEntreprise();
		EntrepriseController.EntrepriseToJSONFile(entreprise);
		System.out.println("Fin du process");
	}
}
