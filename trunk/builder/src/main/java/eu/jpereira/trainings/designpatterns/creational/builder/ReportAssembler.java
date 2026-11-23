package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBodyBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SaleEntry;

public class ReportAssembler {

	private SaleEntry saleEntry;

	public void setSaleEntry(SaleEntry saleEntry) {
		this.saleEntry = saleEntry;
	}

	public Report getReport(ReportBodyBuilder builder) {
		Report report = new Report();
		builder.buildCustomer(this.saleEntry.getCustomer());
		builder.buildItems(this.saleEntry.getSoldItems());
		report.setReportBody(builder.getReportBody());
		return report;
	}
}