package eu.jpereira.trainings.designpatterns.structural.proxy.controller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import eu.jpereira.trainings.designpatterns.structural.proxy.controller.exceptions.CouldNotConnectException;

public class ProxyTrafficLightController extends TrafficLightController implements InvocationHandler {

	private ITrafficLightController proxy;
	private ITrafficLightController realTrafficLightController;

	private int retries = 3;
	private long msBetweenRetries = 1000;

	public ProxyTrafficLightController(String ipAddress) {
		super(ipAddress);
		this.realTrafficLightController = createRealTrafficController();
		proxy = (ITrafficLightController) Proxy.newProxyInstance(
				this.realTrafficLightController.getClass().getClassLoader(),
				new Class[] { ITrafficLightController.class }, this);
		proxy.setIpAddress(this.realTrafficLightController.getIpAddress());
	}

	protected TrafficLightController createRealTrafficController() {
		return new GenericTrafficLightController(this.getIpAddress());
	}

	@Override
	public void sendPowerCommand(ControllerCommand command) throws CouldNotConnectException {
		proxy.sendPowerCommand(command);
	}

	@Override
	public void sendLightCommand(ControllerCommand command) throws CouldNotConnectException {
		proxy.sendLightCommand(command);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("Im a proxy and invoke the method in a real object");
		int failures = 0;
		boolean success = false;
		Exception lastExeption = null;
		Object result = null;
		while (!success) {
			try {
				result = method.invoke(realTrafficLightController, args);
				success = true;
			} catch (Exception e) {
				failures++;
				System.err.println("Retrying Failure: " + failures);
				lastExeption = e;
				try {
					Thread.sleep(this.msBetweenRetries);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}

			if (failures > retries) {
				throw new CouldNotConnectException(lastExeption);
			}
		}
		return result;
	}
}