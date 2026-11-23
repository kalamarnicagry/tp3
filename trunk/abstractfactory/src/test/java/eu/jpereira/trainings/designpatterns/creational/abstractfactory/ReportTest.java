package eu.jpereira.trainings.designpatterns.creational.abstractfactory;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.JSONReportFactory;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.XMLReportFactory;

public class ReportTest {

	@Test
	public void testCreateJSONReport() {
		Report report = new Report(new JSONReportFactory());
		assertEquals("JSON", report.getBody().getType());
		assertEquals("JSON", report.getHeader().getType());
		assertEquals("JSON", report.getFooter().getType());
	}

	@Test
	public void testCreateXMLReport() {
		Report report = new Report(new XMLReportFactory());
		assertEquals("XML", report.getBody().getType());
		assertEquals("XML", report.getHeader().getType());
		assertEquals("XML", report.getFooter().getType());
	}
}