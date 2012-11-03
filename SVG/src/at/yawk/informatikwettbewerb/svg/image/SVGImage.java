package at.yawk.informatikwettbewerb.svg.image;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Sammlungsklasse für ein SVG-Bild
 * 
 * @author Jonas Konrad
 * @version 1.0
 */
public class SVGImage {
	/**
	 * {@link java.util.Collection} aller Elemente dieses Bildes
	 */
	private final Collection<SVGElement>	elements	= new ArrayList<>();
	
	/**
	 * Füge ein Element hinzu
	 * @param element
	 */
	public void addElement(SVGElement element) {
		elements.add(element);
	}
	
	/**
	 * Schreibt XML-Header und alle Kindelemente in einen Stream
	 * 
	 * @param scale Skalierungsfaktor
	 * @param out Ausgabestream
	 * @throws IOException Falls ein Fehler beim Schreiben in den Stream auftritt
	 */
	public void writeXML(int scale, OutputStream out) throws IOException {
		out.write("<?xml version=\"1.0\"?><svg xmlns=\"http://www.w3.org/2000/svg\">".getBytes());
		for(final SVGElement s : elements)
			out.write(s.getXmlRepresentative(scale, "black").getBytes());
		out.write("</svg>".getBytes());
	}
}
