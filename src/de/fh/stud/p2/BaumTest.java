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
		int posX = 2, posY = 2;
		/*
		 * TODO Praktikum 2 [3]: Baut hier basierend auf dem gegebenen 
		 * Anfangszustand (siehe view, posX und posY) den Suchbaum auf.
		 */
		Knoten posPacman = BFS(new Knoten(view, posX, posY));

		// Tiefentest
		Knoten tmp = posPacman;
		int i = 0;
		System.out.print("|");
		while(tmp.getChildren() != null) {
			if(!tmp.getChildren().isEmpty()) {
				System.out.print(i + " |");
				tmp = tmp.getChildren().get(0);
			}
			else
				tmp = null;
		}
		System.out.println("\n");
	}

	// Wurde auf static erweitert um in main aufgerufen werden zu können
	static public Knoten BFS(Knoten node){
		int count = 0;
		LinkedList<Knoten> queue = new LinkedList<>();
		Knoten tmp = node;

		tmp.setSeen(true);
		queue.add(tmp);

		while(!queue.isEmpty()){
			tmp = queue.get(0);
			queue.remove(0);

			if(count == 10)
				return node;

			tmp.expand();
			for(Knoten child : tmp.getChildren()){
				if(!child.isSeen())
				{
					queue.add(child);
					child.setSeen(true);
				}
			}
			count++;
		}

		return node;
	}
}
