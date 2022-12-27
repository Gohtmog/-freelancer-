package org.mycompany.processor;

import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.CVController;
import org.mycompany.controller.CandidatController;
import org.mycompany.model.CV;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptCV implements Processor {

	@Autowired
	CVController cvController;

	@Autowired
	CandidatController cc;

	Scanner scan = new Scanner(System.in);

	@Override
	public void process(Exchange exchange) throws Exception {
		CV cv = cvController.promptCV();
		cvController.CVToJSONFile(cv);
		System.out.println("Fin du process");
	}
}
