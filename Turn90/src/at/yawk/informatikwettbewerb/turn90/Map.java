package at.yawk.informatikwettbewerb.turn90;

public class Map {
	private boolean[][]	map;
	private int			mapXOffset;
	private int			mapYOffset;
	
	public Map() {
		this(1, 1, 0, 0);
	}
	
	public Map(int mapWidth, int mapHeight, int centerX, int centerY) {
		this(new boolean[mapWidth][mapHeight], centerX, centerY);
	}
	
	public Map(final boolean[][] data, int centerX, int centerY) {
		map = data;
		mapXOffset = -centerX;
		mapYOffset = -centerY;
	}
	
	public void extendMap(int west, int north, int east, int south) {
		final boolean[][] newMap = new boolean[getWidth() + west + east][getHeight() + north + south];
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				newMap[x + west][y + north] = map[x][y];
			}
		}
		mapXOffset -= west;
		mapYOffset -= north;
		this.map = newMap;
	}
	
	public boolean getBlockedForCoordinates(final int x, final int y) {
		return map[x - mapXOffset][y - mapYOffset];
	}
	
	public void extendMap(final Direction d, final int amount) {
		extendMap(d == Direction.WEST ? amount : 0, d == Direction.NORTH ? amount : 0, d == Direction.EAST ? amount : 0, d == Direction.SOUTH ? amount : 0);
	}
	
	public int getWidth() {
		return map.length;
	}
	
	public int getHeight() {
		return map[0].length;
	}
	
	public void setBlockedForCoordinates(final int x, final int y, final boolean blocked) {
		map[x - mapXOffset][y - mapYOffset] = blocked;
	}
}
