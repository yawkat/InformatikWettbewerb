package at.yawk.informatikwettbewerb.svg.generation.koch;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import at.yawk.informatikwettbewerb.svg.image.SVGImage;
import at.yawk.informatikwettbewerb.svg.image.SVGLine;

/**
 * Generatorklasse für eine Schneeflockenkurve
 * 
 * @author Jonas Konrad
 * @version 1.0
 */
final class Generator {
	/**
	 * Dummy-Konstruktor, um Instanzen zu vermeiden
	 */
	private Generator() {
		
	}
	
	/**
	 * @param args
	 *            Kommandozeilen-Parameter
	 * @throws IOException
	 *             Wenn ein Fehler beim Erstellen oder Schreiben der
	 *             Ausgabedatei auftritt
	 */
	public static void main(String[] args) throws IOException {
		// überprüfe Argumente
		if(args.length == 3 && args[0].matches("[0-9]+") && args[1].matches("[0-9]+")) {
			System.out.println("Preparing");
			
			// wandle Skalierung und Iterationszahl in Integer-Werte um
			final int scale = Integer.parseInt(args[0]);
			final int iterations = Integer.parseInt(args[1]);
			
			// öffne die Ziel-Datei. Dies wird schon hier getan, damit Fehler
			// schon vor der Rechnung auffallen.
			final File f = new File(args[2]);
			if(f.getParentFile() != null)
				f.getParentFile().mkdirs();
			final OutputStream os = new FileOutputStream(f);
			
			System.out.println("Generating 3 * 4 ^ " + iterations + " (" + (int)(3 * Math.pow(4, iterations)) + ") lines");
			
			final SVGImage svgi = new SVGImage();
			{
				// generiere Linie unten links
				System.out.println("Generating Part A");
				final KochLine beginLine = new KochLine((byte)1, new Point2D.Double(1, 1), 1, false);
				for(final SVGLine s : beginLine.getChildLines(iterations, 1D / scale))
					svgi.addElement(s);
			}
			{
				// generiere Linie unten rechts
				System.out.println("Generating Part B");
				final KochLine beginLine = new KochLine((byte)2, new Point2D.Double(2, 1), 1, true);
				for(final SVGLine s : beginLine.getChildLines(iterations, 1D / scale))
					svgi.addElement(s);
			}
			{
				// generiere Linie oben
				System.out.println("Generating Part C");
				final KochLine beginLine = new KochLine((byte)0, new Point2D.Double(1, 1), 1, true);
				for(final SVGLine s : beginLine.getChildLines(iterations, 1D / scale))
					svgi.addElement(s);
			}
			System.out.println("Writing to file");
			// schreibe in Datei. Dies geschieht ohne Umweg über einen String,
			// um einen Bufferüberlauf zu vermeiden.
			svgi.writeXML(scale, os);
			os.close();
			System.out.println("Done!");
		} else {
			String url = Generator.class.getProtectionDomain().getCodeSource().getLocation().toString();
			try {
				// Versuche die JAR-Datei als URL zu verwenden
				url = new File(Generator.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
			} catch(Exception e) {
				
			}
			System.err.println("Invalid arguments: java -jar \"" + url + "\" <scale> <iterations> <output file>");
			System.err.println("An iteration count over 10 is not recommended, as about 12 million lines will be generated, which will likely cause a heap overflow. A good iteration count is 5.");
		}
	}
}
