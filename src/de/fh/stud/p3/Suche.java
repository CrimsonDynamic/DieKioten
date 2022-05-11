package de.fh.stud.p3;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.Knoten;

import java.util.LinkedList;

public class Suche {
	
	/*
	 * TODO Praktikum 3 [1]: Erweitern Sie diese Klasse um die notwendigen
	 * Attribute und Methoden um eine Tiefensuche durchführen zu können.
	 * Die Erweiterung um weitere Suchstrategien folgt in Praktikum 4.
	 */
	Knoten posPacman;

	public Suche(Knoten rootNode)
	{
		this.posPacman = rootNode;
	}

	/*
	 * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
	 * zu unterstützen.
	 */
	
	public Knoten start() {
		/*
		 * TODO Praktikum 3 [2]: Implementieren Sie hier den Algorithmus einer Tiefensuche.
		 */
		LinkedList<Knoten> openList = new LinkedList<>();
		LinkedList<Knoten> closedList = new LinkedList<>();

		Knoten current = posPacman;
		openList.add(current);

		while(!openList.isEmpty())
		{
		
		}
		return null;
	}
}
