package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.ThirdPartyDoor.DoorState;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.ThirdPartyDoor.LockStatus;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorObjectAdapter implements Door {

	private ThirdPartyDoor thirdPartyDoor;

	public ThirdPartyDoorObjectAdapter() {
		this.thirdPartyDoor = new ThirdPartyDoor();
	}

	@Override
	public void open(String code) throws IncorrectDoorCodeException {
		try {
			this.thirdPartyDoor.unlock(code);
			this.thirdPartyDoor.setState(DoorState.OPEN);
		} catch (CannotUnlockDoorException | CannotChangeStateOfLockedDoor e) {
			throw new IncorrectDoorCodeException(e);
		}
	}

	@Override
	public void close() {
		if (this.thirdPartyDoor.getLockStatus() == LockStatus.UNLOCKED) {
			try {
				this.thirdPartyDoor.setState(DoorState.CLOSED);
			} catch (CannotChangeStateOfLockedDoor e) {
				// stan zamka sprawdzony wyżej
			}
			this.thirdPartyDoor.lock();
		}
	}

	@Override
	public boolean isOpen() {
		return this.thirdPartyDoor.getState() == DoorState.OPEN;
	}

	@Override
	public void changeCode(String oldCode, String newCode, String newCodeConfirmation)
			throws IncorrectDoorCodeException, CodeMismatchException {
		if (newCode == null || !newCode.equals(newCodeConfirmation)) {
			throw new CodeMismatchException();
		}

		boolean wasLocked = (this.thirdPartyDoor.getLockStatus() == LockStatus.LOCKED);
		try {
			this.thirdPartyDoor.unlock(oldCode);
			this.thirdPartyDoor.setNewLockCode(newCode);
			if (wasLocked) {
				this.thirdPartyDoor.lock();
			}
		} catch (CannotUnlockDoorException | CannotChangeCodeForUnlockedDoor e) {
			throw new IncorrectDoorCodeException(e);
		}
	}

	@Override
	public boolean testCode(String code) {
		boolean wasLocked = (this.thirdPartyDoor.getLockStatus() == LockStatus.LOCKED);
		try {
			this.thirdPartyDoor.unlock(code);
			if (wasLocked) {
				this.thirdPartyDoor.lock();
			}
			return true;
		} catch (CannotUnlockDoorException e) {
			return false;
		}
	}
}