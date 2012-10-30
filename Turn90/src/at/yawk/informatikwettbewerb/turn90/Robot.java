package at.yawk.informatikwettbewerb.turn90;

public class Robot {
	private Direction facingDirection = Direction.NORTH;
	private int xPosition = 0;
	private int yPosition = 0;
	private final Map memorizedMap = new Map();
	
	public void tick(final Map totalWorld) {
		final boolean isLookingAtBlockedLocation = totalWorld.getBlockedForCoordinates(getLookingAtX(), getLookingAtY());
		memorizedMap.extendMap(facingDirection, 1);
		memorizedMap.setBlockedForCoordinates(getLookingAtX(), getLookingAtY(), isLookingAtBlockedLocation);
		if(isLookingAtBlockedLocation) {
			onTouch();
		} else {
			driveOn();
		}
	}
	
	private void onTouch() {
		System.out.println("Touch!");
		facingDirection = Direction.values()[(facingDirection.ordinal() + 1) % Direction.values().length];
	}
	
	private void driveOn() {
		xPosition = getLookingAtX();
		yPosition = getLookingAtY();
	}
	
	private int getLookingAtX() {
		return xPosition + (facingDirection == Direction.WEST ? -1 : facingDirection == Direction.EAST ? 1 : 0);
	}
	
	private int getLookingAtY() {
		return yPosition + (facingDirection == Direction.NORTH ? -1 : facingDirection == Direction.SOUTH ? 1 : 0);
	}
	
	public int getX() {
		return xPosition;
	}
	
	public int getY() {
		return yPosition;
	}
}
