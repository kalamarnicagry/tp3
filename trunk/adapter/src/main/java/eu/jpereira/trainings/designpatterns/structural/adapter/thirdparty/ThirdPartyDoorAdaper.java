package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorAdaper extends ThirdPartyDoor implements Door {

	@Override
	public void open(String code) throws IncorrectDoorCodeException {
		try {
			unlock(code);
			setState(DoorState.OPEN);
		} catch (CannotUnlockDoorException | CannotChangeStateOfLockedDoor e) {
			throw new IncorrectDoorCodeException(e);
		}
	}

	@Override
	public void close() {
		if (getLockStatus() == LockStatus.UNLOCKED) {
			try {
				setState(DoorState.CLOSED);
			} catch (CannotChangeStateOfLockedDoor e) {
				// stan zamka sprawdzony wyżej
			}
			lock();
		}
	}

	@Override
	public boolean isOpen() {
		return getState() == DoorState.OPEN;
	}

	@Override
	public void changeCode(String oldCode, String newCode, String newCodeConfirmation)
			throws IncorrectDoorCodeException, CodeMismatchException {
		if (newCode == null || !newCode.equals(newCodeConfirmation)) {
			throw new CodeMismatchException();
		}

		boolean wasLocked = (getLockStatus() == LockStatus.LOCKED);
		try {
			unlock(oldCode);
			setNewLockCode(newCode);
			if (wasLocked) {
				lock();
			}
		} catch (CannotUnlockDoorException | CannotChangeCodeForUnlockedDoor e) {
			throw new IncorrectDoorCodeException(e);
		}
	}

	@Override
	public boolean testCode(String code) {
		boolean wasLocked = (getLockStatus() == LockStatus.LOCKED);
		try {
			unlock(code);
			if (wasLocked) {
				lock();
			}
			return true;
		} catch (CannotUnlockDoorException e) {
			return false;
		}
	}
}