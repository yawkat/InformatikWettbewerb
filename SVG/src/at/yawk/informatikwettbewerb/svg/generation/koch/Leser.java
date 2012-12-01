package at.yawk.informatikwettbewerb.svg.generation.koch;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import at.yawk.informatikwettbewerb.svg.image.SVGImage;
import at.yawk.informatikwettbewerb.svg.image.SVGLine;

public class Leser {
	public static void main(String[] args) throws IOException, XMLStreamException {
		if(args.length == 2) {
			// bereite Lesen vor
			final InputStream ausgangsDatei = new FileInputStream(args[0]);
			final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			final XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(ausgangsDatei);
			
			// kleinste / Größte X- und Y-Werte
			double minimumX = Double.MAX_VALUE, maximumX = Double.MIN_VALUE;
			double minimumY = Double.MAX_VALUE, maximumY = Double.MIN_VALUE;
			
			// Gesamtlinienzahl
			int linien = 0;
			
			// lese XML
			while(reader.hasNext()) {
				final int next = reader.next();
				if(next == XMLStreamConstants.START_ELEMENT) {
					if(reader.getName().getLocalPart().equalsIgnoreCase("line")) {
						linien++;
						for(int i = 0; i < reader.getAttributeCount(); i++) {
							if(reader.getAttributeLocalName(i).equalsIgnoreCase("x1")) {
								minimumX = Math.min(minimumX, Double.parseDouble(reader.getAttributeValue(i)));
								maximumX = Math.max(maximumX, Double.parseDouble(reader.getAttributeValue(i)));
							} else if(reader.getAttributeLocalName(i).equalsIgnoreCase("x2")) {
								minimumX = Math.min(minimumX, Double.parseDouble(reader.getAttributeValue(i)));
								maximumX = Math.max(maximumX, Double.parseDouble(reader.getAttributeValue(i)));
							} else if(reader.getAttributeLocalName(i).equalsIgnoreCase("y1")) {
								minimumY = Math.min(minimumY, Double.parseDouble(reader.getAttributeValue(i)));
								maximumY = Math.max(maximumY, Double.parseDouble(reader.getAttributeValue(i)));
							} else if(reader.getAttributeLocalName(i).equalsIgnoreCase("y2")) {
								minimumY = Math.min(minimumY, Double.parseDouble(reader.getAttributeValue(i)));
								maximumY = Math.max(maximumY, Double.parseDouble(reader.getAttributeValue(i)));
							}
						}
					}
				}
			}
			
			// Ausgabe
			final OutputStream outputDatei = new FileOutputStream(args[1]);
			
			// erstelle neue Linie auf Grund von eingelesenen Koordinaten
			final KochLine beginLine = new KochLine((byte)0, new Point2D.Double(minimumX, maximumY), maximumX - minimumX, true);
			// füge zu Bild hinzu und schreibe in Datei
			final SVGImage svgi = new SVGImage();
			for(final SVGLine s : beginLine.getChildLines((int)(Math.log(linien) / Math.log(4)) + 1, 1D / 1))
				svgi.addElement(s);
			svgi.writeXML(1, outputDatei);
			outputDatei.close();
		} else {
			String url = Leser.class.getProtectionDomain().getCodeSource().getLocation().toString();
			try {
				// Versuche die JAR-Datei als URL zu verwenden
				url = new File(Leser.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
			} catch(Exception e) {
				
			}
			System.err.println("Invalid arguments: java -jar \"" + url + "\" <input file> <output file>");
		}
	}
}
