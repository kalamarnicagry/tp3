package eu.jpereira.trainings.designpatterns.creational.factorymethod;

public abstract class ReportGenerator {

	protected abstract Report createReport();

	public Report generateReport(ReportData data) {
		Report generatedReport = createReport();
		generatedReport.generateReport(data);
		return generatedReport;
	}
}