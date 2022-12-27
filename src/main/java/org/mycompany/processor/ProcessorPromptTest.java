package org.mycompany.processor;

import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.controller.TestController;
import org.mycompany.controller.CandidatController;
import org.mycompany.model.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorPromptTest implements Processor {

	@Autowired
	TestController TestController;

	@Override
	public void process(Exchange exchange) throws Exception {
		Test test = TestController.promptTest();
		TestController.TestToJSONFile(test);
		System.out.println("Fin du process");
	}
}
