package org.mycompany.processor;

import java.util.List;
import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.CVController;
import org.mycompany.controller.CandidatController;
import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptCV implements Processor {

	@Autowired
	CVController cvController;

	@Autowired
	CandidatController cc;

	Scanner scan = new Scanner(System.in);

	@Override
	public void process(Exchange exchange) throws Exception {
		List<CV> listeCV = cvController.getCVs();
		int nouvelID = listeCV.size() + 1;

		System.out.println("Rentrez le corps de votre CV svp");
		String corpsCV = scan.next();

		System.out.println("Quel est votre identifiant de candidat svp ?");
		int idC = scan.nextInt();
		Candidat cand = cc.getCandidat(idC);

		CV cv = new CV(nouvelID, corpsCV, cand);

		exchange.getIn().setBody(cv);
		System.out.println("On a bien défini le message comme étant " + cv.toString());
	}
}
