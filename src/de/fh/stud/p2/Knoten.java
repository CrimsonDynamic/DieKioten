package de.fh.stud.p2;

import de.fh.pacman.Pacman;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.kiServer.util.Vector2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Knoten {

	private final Knoten parent;
	private final PacmanTileType[][] view;
	private final Vector2 pos;
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
		this.pos = new Vector2(posX, posY);

		this.view = view.clone();
		PacmanTileType posType = view[posX][posY];
		if( parent != null && (posType == PacmanTileType.DOT || posType == PacmanTileType.POWERPILL || posType == PacmanTileType.EMPTY) )
		{
			view[posX][posY] = PacmanTileType.PACMAN;
			view[parent.pos.x][parent.pos.y] = PacmanTileType.EMPTY;
		}
	}

	/*
		Getter/Setter
	 */
	public Vector2 getPos() { return this.pos; }
	public int getPosX() { return this.pos.x; }
	public int getPosY() { return this.pos.y; }
	public PacmanTileType getPositionType() { return view[pos.x][pos.y]; }

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
		if(children == null)
		{
			children = new LinkedList<>();
			for(int i = -1; i <= 1; i++)
			{
				for(int j = -1; j <= 1; j++)
				{
					int childX = i + pos.x;
					int childY = j + pos.y;
					// Exkludiert den Knoten aus der Liste, der die Methode aufruft und überprüft auf OutOfBounds der view.
					if( getPositionType() != PacmanTileType.WALL && childX > -1 && childY > -1 && childY < view.length
							&&  childX < view[childY].length && ((i != 0  && j == 0) ^ (i == 0 && j != 0)) )
					{
						children.add(new Knoten(this, view, childX, childY));
					}
				}
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

	public PacmanAction determineActionFromParent()
	{
		int distX = parent.getPosX() - this.getPosX();
		int distY = parent.getPosY() - this.getPosY();

		if(distX == 0 && distY == 1)
			return PacmanAction.GO_SOUTH;
		else if(distX == 1 && distY == 0)
			return PacmanAction.GO_EAST;
		else if(distX == 0 && distY == -1)
			return PacmanAction.GO_NORTH;
		else
			return PacmanAction.GO_WEST;
	}

	public boolean compareView(PacmanTileType[][] otherView)
	{
		return Arrays.deepEquals(view, otherView);
	}

	public void printChildren()
	{
		System.out.print("<|");
		for(Knoten child : children)
		{
			System.out.print(" (" + child.pos.x + "," + child.pos.y + ") ");
		}
		System.out.print("|>");
	}

	public void printTree()
	{
		int currentDepth = 0;
		LinkedList<Knoten> currentDepthNodes = new LinkedList<>();
		currentDepthNodes.add(this);
		System.out.println("Tiefe: " + currentDepth + "\n (" + pos.x + "," + pos.y + ")");

		while( !currentDepthNodes.isEmpty() )
		{
			System.out.println("Tiefe: " + currentDepth);
			LinkedList<Knoten> nextDepthNodes = new LinkedList<>();
			for(Knoten node : currentDepthNodes)
			{
				node.printChildren();
				nextDepthNodes.addAll(node.getChildren());
			}
			System.out.println();
			currentDepthNodes = nextDepthNodes;
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		else if(obj == null || obj.getClass() != this.getClass() )
		{
			Knoten knoten = (Knoten) obj;
			return this.pos.x == knoten.pos.x && this.pos.y == knoten.pos.y && this.compareView(knoten.view);
		}

		else
			return false;
	}
}
