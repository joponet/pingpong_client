package net.pingpong.client;

import net.pingpong.lib.GameVars;
import net.pingpong.lib.Interfaces;

public class ClientStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameVars gameVars = new GameVars();
		Interfaces.listInterfaces();
		System.out.printf("Selected: %s\n",Interfaces.getInterface().getName());
//		gameVars.setNetworkInterface(args[0]);
//		gameVars.setServerAddress(args[1]);
		gameVars.setNetworkInterface();
		Game game = new Game(gameVars);
		game.Run();
	}
}
