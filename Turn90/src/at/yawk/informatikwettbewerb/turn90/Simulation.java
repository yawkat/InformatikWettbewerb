package at.yawk.informatikwettbewerb.turn90;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Simulation {
	public static void main(String[] args) throws IOException {
		final List<List<Boolean>> map = new ArrayList<List<Boolean>>();
		final Scanner s = new Scanner(Simulation.class.getResourceAsStream("map"));
		int roboX = 0;
		int roboY = 0;
		try {
			int x = 0, y = 0;
			while(s.hasNext()) {
				final String l = s.nextLine();
				final List<Boolean> line = new ArrayList<Boolean>();
				for(char c : l.toCharArray()) {
					line.add(c == '#');
					if(c == 'R') {
						roboX = x;
						roboY = y;
					}
					x++;
				}
				map.add(line);
				x = 0;
				y++;
			}
		} finally {
			s.close();
		}
		final boolean[][] data = new boolean[map.get(0).size()][map.size()];
		for(int x = 0; x < data.length; x++) {
			for(int y = 0; y < data[0].length; y++) {
				data[x][y] = map.get(y).get(x);
			}
		}
		final int mapScale = 20;
		final BufferedImage image = new BufferedImage(data.length * mapScale, data[0].length * mapScale, BufferedImage.TYPE_3BYTE_BGR);
		final Graphics2D g = image.createGraphics();
		for(int x = 0; x < data.length; x++) {
			for(int y = 0; y < data[0].length; y++) {
				g.setColor(data[x][y] ? Color.BLUE : Color.WHITE);
				g.fillRect(x * mapScale, y * mapScale, mapScale, mapScale);
			}
		}
		g.setColor(Color.RED);
		
		final Map totalMap = new Map(data, roboX, roboY);
		
		final Robot robo = new Robot();
		for(int i = 0; i < 100; i++) {
			g.fillRect((robo.getX() + roboX) * mapScale, (robo.getY() + roboY) * mapScale, mapScale, mapScale);
			ImageIO.write(image, "GIF", new File("map"+i+".gif"));
			robo.tick(totalMap);
		}
		
		g.dispose();
	}
}
