package eu.jpereira.trainings.designpatterns.creational.prototype;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import org.junit.Test;

import eu.jpereira.trainings.designpatterns.creational.prototype.model.AbstractVehiclePart;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.Shell;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.Tire;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.Vehicle;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.VehiclePartEnumeration;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.VehiclePartPropertiesEnumeration;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.Window;

public class VehicleCloneTest {

	@Test
	public void testCloneVehicleDeepCopy() throws Exception {
		Client client = new Client();

		Properties tireProps = new Properties();
		tireProps.put(VehiclePartPropertiesEnumeration.SIZE, 16);

		Properties shellProps = new Properties();
		shellProps.put(VehiclePartPropertiesEnumeration.COLOR, "red");

		Properties windowProps = new Properties();
		windowProps.put(VehiclePartPropertiesEnumeration.WIDTH, 50);

		Vehicle original = client.vehicleBuilder()
				.createVehicle()
				.with(new Tire(tireProps)).times(4)
				.with(new Window(windowProps)).times(4)
				.with(new Shell(shellProps)).times(1)
				.packIt();

		Vehicle cloned = (Vehicle) original.clone();

		assertNotSame(original, cloned);
		assertEquals(original.countParts(), cloned.countParts());

		List<VehiclePart> originalTires = original.getParts(VehiclePartEnumeration.TIRE);
		List<VehiclePart> clonedTires = cloned.getParts(VehiclePartEnumeration.TIRE);
		assertEquals(4, clonedTires.size());
		assertNotSame(originalTires.get(0), clonedTires.get(0));

		AbstractVehiclePart origTire = (AbstractVehiclePart) originalTires.get(0);
		AbstractVehiclePart copyTire = (AbstractVehiclePart) clonedTires.get(0);
		assertNotSame(origTire.getPartProperties(), copyTire.getPartProperties());
		assertEquals(origTire.getPartProperties(), copyTire.getPartProperties());

		// Zmiana właściwości w klonie nie może wpłynąć na oryginał
		copyTire.getPartProperties().put(VehiclePartPropertiesEnumeration.SIZE, 19);
		assertEquals(16, origTire.getPartProperties().get(VehiclePartPropertiesEnumeration.SIZE));
	}
}