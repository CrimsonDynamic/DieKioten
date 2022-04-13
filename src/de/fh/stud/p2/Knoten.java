package de.fh.stud.p2;

import de.fh.pacman.enums.PacmanTileType;

import java.util.LinkedList;
import java.util.List;

public class Knoten {

	private final Knoten parent;
	private final PacmanTileType[][] view;
	private final int posX;
	private final int posY;
	private LinkedList<Knoten> children; // wird mit expand() gefüllt nicht über den Konstruktor

	private boolean seen;

	/*
	 * TODO Praktikum 2 [1]: Erweitert diese Klasse um die notwendigen
	 * Attribute, Methoden und Konstruktoren um die möglichen verschiedenen Weltzustände darstellen und
	 * einen Suchbaum aufspannen zu können.
	 */

	/**
	 * Initialisiert einen Knoten ohne Elternknoten. Somit ist parent == null.
	 * @param view
	 * Derzeitig betrachtete Welt auf die die Baumstruktur basiert
	 * @param posX
	 * X-Koordinate orientierend an der linken oberen Ecke von view.
	 * @param posY
	 * Y-Koordinate orientierend an der linken oberen Ecke von view.
	 */
	public Knoten(PacmanTileType[][] view, int posX, int posY)
	{
		this(null, view, posX, posY);
	}

	/**
	 * Initialisiert einen Knoten.
	 * @param parent
	 * Eltern Knoten von dem dieser Knoten expandiert wurde.
	 * @param view
	 * Derzeitig betrachtete Welt auf die die Baumstruktur basiert
	 * @param posX
	 * X-Koordinate orientierend an der linken oberen Ecke von view.
	 * @param posY
	 * Y-Koordinate orientierend an der linken oberen Ecke von view.
	 */
	public Knoten (Knoten parent, PacmanTileType[][] view, int posX, int posY)
	{
		this.parent = parent;
		this.view = view;
		this.posX = posX;
		this.posY = posY;
	}
	/*
		Getter/Setter
	 */
	public void setSeen(boolean seen) { this.seen = seen; }
	public boolean isSeen(){ return this.seen; }

	/*
	public int getPosX(){ return this.PosX; }
	public int getPosY(){ return this.PosY; }
	*/

	public LinkedList<Knoten> getChildren() { return this.children; }

	/**
	 * Expandiert den Wegbaum um die Nachbarn des zu expandierenden Knotens.
	 * Betrachtet derzeitig ALLE Nachbarn des Knotens und nicht nur die Relevanten für eine Änderung des
	 * Weltzustandes!
	 */
	public List<Knoten> expand() {
		/*
		 * TODO Praktikum 2 [2]: Implementiert in dieser Methode das Expandieren des Knotens.
		 * Die Methode soll die neu erzeugten Knoten (die Kinder des Knoten) zurückgeben.
		 */
		children = new LinkedList<>();
		for(int i = -1; i <= 1; i++)
		{
			for(int j = -1; j <= 1; j++)
			{
				int childX = i + posX;
				int childY = i + posY;
				// Exkludiert den Knoten aus der Liste, der die Methode aufruft und überprüft auf OutOfBounds der view.
				if( i != 0 && j != 0 && childX > -1 && childY > -1 && childY < view.length &&  childX < view[childY].length)
					children.add(new Knoten(view, childX, childY));
			}
		}

		return children;
	}

	public Knoten getParent() {
		return parent;
	}
}
