package eu.jpereira.trainings.designpatterns.creational.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.jpereira.trainings.designpatterns.creational.singleton.crwaling.CannotCrawlException;
import eu.jpereira.trainings.designpatterns.creational.singleton.crwaling.DummySiteCrawler;
import eu.jpereira.trainings.designpatterns.creational.singleton.crwaling.SiteCrawler;

public class ReportBuilder {

	// Instance variables
	private Map<String, StringBuffer> sitesContens;

	private SiteCrawler siteCrawler;

	// Class variables
	// Single instance
	private static volatile ReportBuilder instance;
	private static List<String> configuredSites;

	// Class initializer block
	static {
		configuredSites = new ArrayList<String>();
		configuredSites.add("http://www.wikipedia.com");
		configuredSites.add("http://jpereira.eu");
		configuredSites.add("http://stackoverflow.com");
	}

	private ReportBuilder() {
		initiatlize();
	}

	private void initiatlize() {
		this.siteCrawler = createSiteCrawler();

		for (String url : configuredSites) {
			this.siteCrawler.withURL(url);
		}
		try {
			this.setSitesContens(this.siteCrawler.crawl().packSiteContens());
		} catch (CannotCrawlException e) {
			throw new RuntimeException("Could not load sites:" + e.getMessage());
		}
	}

	protected SiteCrawler createSiteCrawler() {
		return new DummySiteCrawler();
	}

	public static ReportBuilder getInstance() {
		System.out.println("Getting instance for Thread " + Thread.currentThread().getId());
		if (instance == null) {
			synchronized (ReportBuilder.class) {
				if (instance == null) {
					System.out.println("Instance is null for Thread " + Thread.currentThread().getId());
					instance = new ReportBuilder();
					System.out.println("Returing " + instance.hashCode() + " instance to Thread " + Thread.currentThread().getId());
				}
			}
		}
		return instance;
	}

	public Map<String, StringBuffer> getSitesContens() {
		return sitesContens;
	}

	private void setSitesContens(Map<String, StringBuffer> sitesContens) {
		this.sitesContens = sitesContens;
	}

	public static void resetInstance() {
		instance = null;
	}
}