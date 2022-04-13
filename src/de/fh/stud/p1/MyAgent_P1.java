package de.fh.stud.p1;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Util;
import de.fh.pacman.*;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;

public class MyAgent_P1 extends PacmanAgent_2021 {
	/**
	 * Array vom Typs PacmanAction, welches eine Kompass-Reihenfolge für die Laufrichtungen des Agenten festlegt.
	 * Die aufsteigende Reihenfolge ist so festgelegt, dass der Nachfolger einer Richtung, rechts vom Vorgänger liegt.
	 * Der Kompass wird dementsprechend im Uhrzeigersinn gelesen.
	 */
	private PacmanAction[] compass = {PacmanAction.GO_NORTH, PacmanAction.GO_EAST, PacmanAction.GO_SOUTH, PacmanAction.GO_WEST};

	/**
	 * int-Wert der den Index des äquivalenten Wertes der derzeitigen nextAction in compass repräsentiert.
	 */
	private int nextActionID = 0;

	/**
	 * Die als nächstes auszuführende Aktion
	 */
	private PacmanAction nextAction;
	
	public MyAgent_P1(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		MyAgent_P1 agent = new MyAgent_P1("MyAgent");
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
		
		//Nachdem das Spiel gestartet wurde, geht der Agent nach Osten
		if(actionEffect == PacmanActionEffect.GAME_INITIALIZED) {
			nextActionID = 1;
			nextAction = compass[nextActionID]; //PacmanAction.GO_EAST;
		}

		// Wenn der Agent kontakt mit einer Wand hat, geht er rechts von seiner Blickrichtung weiter
		if(actionEffect == PacmanActionEffect.BUMPED_INTO_WALL){
			nextActionID = (++nextActionID) %4;
			nextAction = compass[nextActionID];
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
