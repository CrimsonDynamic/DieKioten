package de.fh.stud.p3;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Util;
import de.fh.pacman.PacmanAgent_2021;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.stud.p2.Knoten;
import java.util.LinkedList;

public class MyAgent_P3 extends PacmanAgent_2021 {

	/**
	 * Die als nächstes auszuführende Aktion
	 */
	private PacmanAction nextAction;
	private LinkedList<PacmanAction> actionList;

	/**
	 * Der gefundene Lösungknoten der Suche
	 */
	private Knoten loesungsKnoten;
	
	public MyAgent_P3(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		MyAgent_P3 agent = new MyAgent_P3("MyAgent");
		Agent.start(agent, "127.0.0.1", 5000);
	}

	/**
	 * @param percept - Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand aller Felder der Welt.
	 * @param actionEffect - Aktuelle Rückmeldung des Server auf die letzte übermittelte Aktion.
	 */
	@Override
	public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {
		
		//Gebe den aktuellen Zustand der Welt auf der Konsole aus
		Util.printView(percept.getView());
		
		//Wenn noch keine Lösung gefunden wurde, dann starte die Suche
		if (loesungsKnoten == null) {
			/*
			 * TODO Praktikum 3 [3]: Übergebt hier der Suche die notwendigen Informationen, um
			 * von einem Startzustand zu einem Zielzustand zu gelangen.
			 */
			/*
			 * TODO Praktikum 4 [2]: Entscheidet hier welches Suchverfahren ausgeführt werden soll.
			 */
			Knoten posPacman = new Knoten(percept.getView(), percept.getPosition().x, percept.getPosition().y);
			Suche suche = new Suche(posPacman);
			loesungsKnoten = suche.start();
		}
		
		//Wenn die Suche eine Lösung gefunden hat, dann ermittle die als nächstes auszuführende Aktion
		if (loesungsKnoten != null) {
			/*
			 * TODO Praktikum 3 [4]: Ermittelt hier die als naechstes auszufuehrende Aktion,
			 * basierend auf dem loesungsknoten und weist diese nextAction zu.
			 */
			if(actionList.isEmpty())
			{
				/*
				Knoten current = loesungsKnoten;
				while(current != null)
				{

				}*/
			}
			nextAction = PacmanAction.GO_EAST;
			
		} else {
			//Ansonsten wurde keine Lösung gefunden und der Pacman kann das Spiel aufgeben
			nextAction = PacmanAction.QUIT_GAME;
		}
		
		return nextAction;
	}

	@Override
	protected void onGameStart(PacmanStartInfo startInfo) {
		
	}

	@Override
	protected void onGameover(PacmanGameResult gameResult) {
		
	}
	
}
