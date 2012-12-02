package at.yawk.informatikwettbewerb.svg.generation.koch;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import at.yawk.informatikwettbewerb.svg.image.SVGLine;

/**
 * Eine Linie der Koch-Schneeflocke. Hat eine bestimmte Position, Richtung,
 * Länge und Einstellung ob Kind-Linien „gespiegelt“ werden sollen.
 * 
 * @version 1.0
 * @author Jonas Konrad
 */
class KochLine {
	
	/**
	 * Normalisiert eine Richtungsangabe, falls sie kleiner als <code>0</code>
	 * oder größer als <code>5</code> (ungültig) ist
	 * */
	private static final byte modFacingDirection(byte direction) {
		return (byte)((direction % 6 + 6) % 6);
	}
	
	/**
	 * Die Ausrichtung der Linie, gesehen von einer horizontalen X-Achse.<br />
	 * <code>gradzahl = facingDirection * 60°</code>
	 * */
	private final byte		facingDirection;
	
	/**
	 * Die Anfangsposition der Linie
	 */
	private final Point2D	beginPoint;
	/**
	 * Die Länge der Linie
	 */
	private final double	length;
	/**
	 * Eigenschaft, ob Kind-Linien „gespiegelt“ dargestellt werden sollen. Ist
	 * diese Eigenschaft <code>true</code>, werden zu den Winkeln beiden
	 * abstehenden Kind-Linien 120 bzw. 240 Grad hinzugefügt. Dies wird
	 * verwendet um sicher zu stellen, dass alle Ecken nach außen und nicht nach
	 * innen zeigen. Sie wird bei der dritten Kind-Linie vom Ausgangspunkt
	 * gesehen invertiert, sonst gleichbleibend an Kind-Linien weitergegeben.
	 */
	private final boolean	reverse;
	
	/**
	 * @param direction
	 *            Richtung der Linie ({@link KochLine#facingDirection})
	 * @param beginPoint
	 *            Ausgangspunkt der Linie
	 * @param length
	 *            Länge der Linie
	 * @param reverse
	 *            Eigenschaft, ob Kindlinien „gespiegelt“ werden sollen (
	 *            {@link KochLine#reverse})
	 */
	KochLine(final byte direction, final Point2D beginPoint, final double length, final boolean reverse) {
		this.facingDirection = modFacingDirection(direction);
		this.beginPoint = beginPoint;
		this.length = length;
		this.reverse = reverse;
	}
	
	/**
	 * Generiert Kind-Linien
	 * 
	 * @param linesToGo
	 *            weitere Iterationen
	 * @param scale
	 *            Skalierung der Linienbreite
	 * @return Falls <code>linesToGo == 0</code> ein singleton-Set mit der
	 *         Linie, sonst eine {@link java.util.ArrayList} mit allen
	 *         SVG-Versionen der Kindlinien
	 */
	Collection<SVGLine> getChildLines(int linesToGo, double scale) {
		if(linesToGo == 0) {
			return Collections.singleton(getLine(scale));
		} else {
			final Collection<SVGLine> lines = new ArrayList<>();
			for(final KochLine kl : generateChildLines()) {
				// rufe diese Funktion für alle Kind-Funktionen auf
				lines.addAll(kl.getChildLines(linesToGo - 1, scale));
			}
			return lines;
		}
	}
	
	/**
	 * 
	 * @param scale
	 *            Linienbreite
	 * @return SVG-Version dieser Linie
	 */
	private SVGLine getLine(final double scale) {
		// generiere mit trigonometrischen Funktionen aus Position, Richtung und
		// Länge Anfangs- und Endposition
		return new SVGLine(new Line2D.Double(beginPoint, new Point2D.Double(beginPoint.getX() + Math.cos(facingDirection * Math.PI / 3) * length, beginPoint.getY() + Math.sin(facingDirection * Math.PI / 3) * length)), scale);
	}
	
	/**
	 * Generiert Kind-Linien
	 * 
	 * @return Eine {@link java.util.List} mit den vier berechneten Kindlinien
	 */
	private Collection<KochLine> generateChildLines() {
		// teile diese Linie in drei Teile, füge den ersten und den letzten Teil
		// sowie die beiden abstehenden Linien zu einem Array zusammen und gib
		// dieses zurück
		return Arrays.asList(new KochLine(facingDirection, beginPoint, length / 3D, reverse), new KochLine(facingDirection, new Point2D.Double(beginPoint.getX() + Math.cos(facingDirection * Math.PI / 3) * length / 3 * 2, beginPoint.getY() + Math.sin(facingDirection * Math.PI / 3) * length / 3 * 2), length / 3D, reverse), getSecondLine(), getThirdLine());
	}
	
	/**
	 * Generiert die zweite (erste abstehende) Kindlinie
	 * 
	 * @return Die generierte Linie
	 */
	private KochLine getSecondLine() {
		// Linienlänge, ein Drittel dieser Linie
		final double lineLength = length / 3D;
		// Winkel der neuen Linie, entweder um 60 oder um -60 Grad verschoben
		final byte newFacingDirection = (byte)(this.facingDirection + (reverse ? 5 : 1));
		// rechne relative Position in Koordinaten um
		final double xOffset = Math.cos(facingDirection * Math.PI / 3) * lineLength;
		final double yOffset = Math.sin(facingDirection * Math.PI / 3) * lineLength;
		final Point2D position = new Point2D.Double(beginPoint.getX() + xOffset, beginPoint.getY() + yOffset);
		return new KochLine(newFacingDirection, position, lineLength, reverse);
	}
	
	/**
	 * Generiert die dritte (zweite abstehende) Kindlinie
	 * 
	 * @return Die generierte Linie
	 */
	private KochLine getThirdLine() {
		// Linienlänge, ein Drittel dieser Linie
		final double lineLength = length / 3D;
		// Winkel der neuen Linie, entweder um 120 oder um -120 Grad verschoben
		final byte newFacingDirection = (byte)(this.facingDirection + (reverse ? 4 : 2));
		// rechne relative Position in Koordinaten um
		final double xOffset = Math.cos(facingDirection * Math.PI / 3) * lineLength * 2;
		final double yOffset = Math.sin(facingDirection * Math.PI / 3) * lineLength * 2;
		final Point2D position = new Point2D.Double(beginPoint.getX() + xOffset, beginPoint.getY() + yOffset);
		// invertierte reverse-Eigenschaft, damit die Ecken auch nach außen
		// zeigen
		return new KochLine(newFacingDirection, position, lineLength, !reverse);
	}
}
