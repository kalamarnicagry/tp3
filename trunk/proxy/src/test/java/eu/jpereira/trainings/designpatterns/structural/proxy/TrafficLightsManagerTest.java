package eu.jpereira.trainings.designpatterns.structural.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.jpereira.trainings.designpatterns.structural.proxy.fakes.FakeTrafficLightFactory;
import eu.jpereira.trainings.designpatterns.structural.proxy.model.TrafficLight;
import eu.jpereira.trainings.designpatterns.structural.proxy.model.TrafficLight.LightColors;
import eu.jpereira.trainings.designpatterns.structural.proxy.model.TrafficLight.PowerStatus;
import eu.jpereira.trainings.designpatterns.structural.proxy.model.exceptions.CouldNotSendCommandException;
import eu.jpereira.trainings.designpatterns.structural.proxy.testconfig.TestConfiguration;

public class TrafficLightsManagerTest {

	@Before
	public void setUp() {
		TestConfiguration.fakeFailuresInController = false;
	}

	@Test
	public void testManagePower() throws CouldNotSendCommandException {
		TrafficLightsManager manager = createTrafficLightsManager();
		TrafficLight tl = manager.getTrafficLight("127.0.0.1");

		assertEquals(PowerStatus.OFF, tl.getPowerStatus());
		tl.powerOn();
		assertEquals(PowerStatus.ON, tl.getPowerStatus());
		tl.powerOff();
		assertEquals(PowerStatus.OFF, tl.getPowerStatus());
	}

	@Test
	public void testManageLights() throws CouldNotSendCommandException {
		TrafficLightsManager manager = createTrafficLightsManager();
		TrafficLight tl = manager.getTrafficLight("127.0.0.1");

		assertEquals(LightColors.UNKNOW, tl.getColor());
		tl.setColor(LightColors.GREEN);
		assertEquals(LightColors.GREEN, tl.getColor());

		assertFalse(tl.isFlashing());
		tl.setFlashing(true);
		assertTrue(tl.isFlashing());
	}

	protected TrafficLightsManager createTrafficLightsManager() {
		TrafficLightsManager manager = new TrafficLightsManager(new FakeTrafficLightFactory());
		return manager;
	}
}