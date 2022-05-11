package de.fh.stud.p2;

import de.fh.pacman.Pacman;
import de.fh.pacman.enums.PacmanTileType;

import java.util.LinkedList;
import java.util.List;

public class Knoten {

	private final Knoten parent;
	private final PacmanTileType[][] view;
	private final int posX;
	private final int posY;
	private LinkedList<Knoten> children; // wird mit expand() gefüllt nicht über den Konstruktor

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
		this.posX = posX;
		this.posY = posY;

		this.view = view.clone();
		PacmanTileType posType = view[posX][posY];
		if( parent != null && (posType == PacmanTileType.DOT || posType == PacmanTileType.POWERPILL || posType == PacmanTileType.EMPTY) )
		{
			view[posX][posY] = PacmanTileType.PACMAN;
			view[parent.posX][parent.posY] = PacmanTileType.EMPTY;
		}
	}

	/*
		Getter/Setter
	 */
	public int getPosX() { return this.posX; }
	public int getPosY() { return this.posY; }
	public PacmanTileType getPositionType() { return view[posX][posY]; }

	public LinkedList<Knoten> getChildren() { return this.children; }

	public Knoten getParent() { return parent; }

	/**
	 * Expandiert den Wegbaum um die Nachbarn des zu expandierenden Knotens.
	 * Betrachtet derzeitig ALLE Nachbarn des Knotens und nicht nur die Relevanten für eine Änderung des
	 * Weltzustandes!
	 */
	public LinkedList<Knoten> expand() {
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
				int childY = j + posY;
				// Exkludiert den Knoten aus der Liste, der die Methode aufruft und überprüft auf OutOfBounds der view.
				if( getPositionType() != PacmanTileType.WALL && childX > -1 && childY > -1 && childY < view.length &&  childX < view[childY].length && ((i != 0  && j == 0) ^ (i == 0 && j != 0)) )
					children.add(new Knoten(this, view, childX, childY));
			}
		}

		return children;
	}

	public boolean checkTileAvailability(PacmanTileType tileType)
	{
		for(PacmanTileType[] arr : view )
			for(PacmanTileType tile : arr)
				if(tile == tileType)
					return true;
		return false;
	}

	public boolean compareView(PacmanTileType[][] otherView)
	{
		if (view.length != otherView.length)
			return false;

		for(int i = 0; i < view.length; i++)
		{
			if(view[i].length != otherView[i].length)
				return false;

			for(int j = 0; j < view[i].length; j++)
				if(view[i][j] != otherView[i][j])
					return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		else if(obj == null || obj.getClass() != this.getClass() )
		{
			Knoten knoten = (Knoten) obj;
			return this.posX == knoten.posX && this.posY == knoten.posY && this.compareView(knoten.view);
		}

		else
			return false;
	}
}
