package eu.jpereira.trainings.designpatterns.behavioral.state.appliance.state;

public class StartedState implements ApplianceStateBehavior {

	@Override
	public ApplianceState getState() {
		return ApplianceState.STARTED;
	}

	@Override
	public ApplianceStateBehavior start() {
		return this;
	}

	@Override
	public ApplianceStateBehavior stop() {
		return ApplianceState.STOPPED.getStateBehavior();
	}

	@Override
	public ApplianceStateBehavior turnOn() {
		return this;
	}

	@Override
	public ApplianceStateBehavior turnOff() {
		return this;
	}
}