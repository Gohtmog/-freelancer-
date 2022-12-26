//package org.mycompany.route;
//
//import javax.xml.bind.JAXBContext;
//
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.jackson.JacksonDataFormat;
//import org.apache.camel.converter.jaxb.JaxbDataFormat;
//import org.mycompany.model.CV;
//
////public class RouteJSONEnvoiCV extends RouteBuilder {
//	
//	@Override
//	public void configure() throws Exception {
//		JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
//		JAXBContext con = JAXBContext.newInstance(CV.class);
//		xmlDataFormat.setContext(con);
//		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(CV.class);
//
//		from("file:inputFolder?noop=true")
//		.doTry()
//		.se
//
//	}
//
//	}}}
