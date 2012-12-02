package at.yawk.informatikwettbewerb.svg.image;

import java.awt.geom.Line2D;

/**
 * Eine SVG-Linie
 * 
 * @author Jonas Konrad
 * @version 1.0
 */
public class SVGLine implements SVGElement {
	/**
	 * Linie als {@link java.awt.geom.Line2D}
	 */
	private final Line2D	line;
	
	/**
	 * Linienbreite relativ zum Skalierungsfaktor (meist <code>1</code>)
	 */
	private final double	relativeLineWidth;
	
	/**
	 * @param line Linie als {@link java.awt.geom.Line2D}
	 * @param lineWidth Linienbreite relativ zum Skalierungsfaktor
	 */
	public SVGLine(final Line2D line, final double lineWidth) {
		this.line = line;
		this.relativeLineWidth = lineWidth;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getXmlRepresentative(double scaleFactor, String color) {
		return "<line x1=\"" + line.getX1() * scaleFactor + "\" y1=\"" + line.getY1() * scaleFactor + "\" x2=\"" + line.getX2() * scaleFactor + "\" y2=\"" + line.getY2() * scaleFactor + "\" stroke=\"" + color + "\" stroke-width=\"" + Math.round(relativeLineWidth * scaleFactor) + "\"/>";
	}
}
