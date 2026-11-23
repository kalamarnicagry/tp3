package eu.jpereira.trainings.designpatterns.creational.prototype.model;

import java.util.Properties;

import eu.jpereira.trainings.designpatterns.creational.prototype.VehiclePart;

public abstract class AbstractVehiclePart implements VehiclePart {
	private Properties partProperties;

	public AbstractVehiclePart(Properties properties) {
		this.partProperties = properties;
	}

	@Override
	public VehiclePart clone() throws CloneNotSupportedException {
		AbstractVehiclePart clonedPart = (AbstractVehiclePart) super.clone();
		if (this.partProperties != null) {
			clonedPart.partProperties = (Properties) this.partProperties.clone();
		}
		return clonedPart;
	}

	public Properties getPartProperties() {
		return this.partProperties;
	}
}