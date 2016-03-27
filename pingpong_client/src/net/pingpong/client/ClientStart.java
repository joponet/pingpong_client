package net.pingpong.client;

import net.pingpong.lib.GameVars;

public class ClientStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameVars gameVars = new GameVars();
		gameVars.setNetworkInterface(args[0]);
		gameVars.setServerAddress(args[1]);
		Game game = new Game(gameVars);
		game.Run();
	}
}
