package org.mycompany.processor;

import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.CandidatController;
import org.mycompany.model.Candidat;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptCandidat implements Processor {

	@Autowired
	CandidatController CandidatController;


	Scanner scan = new Scanner(System.in);

	@Override
	public void process(Exchange exchange) throws Exception {
		Candidat candidat = CandidatController.promptCandidat();
		CandidatController.CandidatToJSONFile(candidat);
		System.out.println("Fin du process");
	}
}
