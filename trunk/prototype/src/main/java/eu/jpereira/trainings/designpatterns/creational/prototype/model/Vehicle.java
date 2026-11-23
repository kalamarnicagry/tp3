package eu.jpereira.trainings.designpatterns.creational.prototype.model;

import java.util.ArrayList;
import java.util.List;

import eu.jpereira.trainings.designpatterns.creational.prototype.VehiclePart;
import eu.jpereira.trainings.designpatterns.creational.prototype.exception.VehicleDoesNotHavePartsException;

public class Vehicle implements Cloneable {

	private List<VehiclePart> parts;

	public void setParts(List<VehiclePart> vehicleParts) {
		this.parts = vehicleParts;
	}

	public Object countParts() throws VehicleDoesNotHavePartsException {
		if (this.parts != null) {
			return this.parts.size();
		} else {
			throw new VehicleDoesNotHavePartsException();
		}
	}

	public List<VehiclePart> getParts(VehiclePartEnumeration type) throws VehicleDoesNotHavePartsException {
		if (this.parts == null) {
			throw new VehicleDoesNotHavePartsException();
		}
		List<VehiclePart> match = new ArrayList<VehiclePart>();
		for (VehiclePart vehiclePart : this.parts) {
			if (vehiclePart.is(type)) {
				match.add(vehiclePart);
			}
		}
		return match;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Vehicle clonedVehicle = (Vehicle) super.clone();
		if (this.parts != null) {
			List<VehiclePart> clonedParts = new ArrayList<VehiclePart>();
			for (VehiclePart part : this.parts) {
				clonedParts.add(part.clone());
			}
			clonedVehicle.setParts(clonedParts);
		}
		return clonedVehicle;
	}
}