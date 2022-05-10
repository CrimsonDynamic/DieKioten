package de.fh.stud.p2;

import de.fh.pacman.enums.PacmanTileType;
import java.util.LinkedList;
public class BaumTest {
	public static void main(String[] args) {
		//Anfangszustand nach Aufgabe
		PacmanTileType[][] view = {
				{PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.EMPTY,PacmanTileType.DOT,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.DOT,PacmanTileType.WALL,PacmanTileType.WALL},
				{PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL,PacmanTileType.WALL}
		};
		//Startposition des Pacman
		int posX = 1, posY = 1;
		/*
		 * TODO Praktikum 2 [3]: Baut hier basierend auf dem gegebenen 
		 * Anfangszustand (siehe view, posX und posY) den Suchbaum auf.
		 */
		Knoten posPacman = BFS(new Knoten(view, posX, posY));

		// Tiefentest
		int tiefe = 0;
		LinkedList<Knoten> tiefenListe = new LinkedList<>();
		tiefenListe.add(posPacman);

		while(!tiefenListe.isEmpty()) {
			System.out.println("Tiefe: " + tiefe);
			LinkedList<Knoten> tmp = new LinkedList<>();

			System.out.print("| ");
			for(Knoten node : tiefenListe)
			{
				System.out.print("(" + node.getPosX() + "," + node.getPosY() + ") |");
				LinkedList<Knoten> childList = node.getChildren();
				if(childList != null)
					for(Knoten child : childList)
						tmp.add(child);
			}

			tiefenListe = tmp;
			tiefe++;
			System.out.println("\n");
		}
		System.out.println("\n");
	}

	// Wurde auf static erweitert um in main aufgerufen werden zu können
	static public Knoten BFS(Knoten node){
		int depth = 0;
		LinkedList<Knoten> openList = new LinkedList<>();
		LinkedList<Knoten> closedList = new LinkedList<>();

		Knoten current = node;
		openList.add(current);

		while(!openList.isEmpty()){
			current = openList.get(0);
			openList.remove(0);
			closedList.add(current);

			if(depth == 10)
				return node;

			current.expand();
			for(Knoten child : current.getChildren())
			{
				boolean seen = false;
				for(Knoten closedChild : closedList)
					if(child.equals(closedChild))
					{
						seen = true;
						break;
					}

				if(!(seen || child.getPositionType() == PacmanTileType.WALL))
					openList.add(child);
			}
			depth++;
		}

		return node;
	}
}
