package at.yawk.informatikwettbewerb.svg.image;

/**
 * Ein SVG-Element (z. B. Linie oder Kreis)
 * 
 * @author Jonas Konrad
 * @version 1.0
 */
public interface SVGElement {
	/**
	 * @param scaleFactor Skalierungsfaktor
	 * @param color Farbe des Elements (falls festlegbar)
	 * @return XML-Tag des Elements
	 */
	String getXmlRepresentative(double scaleFactor, String color);
}
