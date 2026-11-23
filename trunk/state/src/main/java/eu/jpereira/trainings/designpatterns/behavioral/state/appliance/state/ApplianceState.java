package eu.jpereira.trainings.designpatterns.behavioral.state.appliance.state;

public enum ApplianceState {

	OFF {
		public ApplianceStateBehavior getStateBehavior() {
			return new OffState();
		}
	},
	ON {
		public ApplianceStateBehavior getStateBehavior() {
			return new OnState();
		}
	},
	STOPPED {
		public ApplianceStateBehavior getStateBehavior() {
			return new StoppedState();
		}
	},
	STARTED {
		public ApplianceStateBehavior getStateBehavior() {
			return new StartedState();
		}
	},
	UNKNOW {
		public ApplianceStateBehavior getStateBehavior() {
			return new UnknowState();
		}
	};

	public ApplianceStateBehavior getStateBehavior() {
		return null;
	}
}