package eu.jpereira.trainings.designpatterns.creational.builder.model;

import java.util.List;

public interface ReportBodyBuilder {
	void buildCustomer(Customer customer);
	void buildItems(List<SoldItem> items);
	ReportBody getReportBody();
}