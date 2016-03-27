package net.pingpong.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

import net.pingpong.lib.Delay;
import net.pingpong.lib.GameParameters;
import net.pingpong.lib.GameVars;
import net.pingpong.lib.PlayerState;

public class OutputSocket extends Thread {
	DatagramSocket socketOutput;
	GameVars gameVars;
	PlayerState playerState;
	boolean tick;
	long elapsed;

	OutputSocket(GameVars gameVars) {
		this.gameVars = gameVars;
		tick = false;
		playerState = new PlayerState();
		//playerState.setPos(GameParameters.WIDTH/2);
		try {
			socketOutput = new DatagramSocket(GameParameters.CLIENT_PORT_OUT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		Delay delay = new Delay();
		try {
			DatagramPacket packet;
			byte[] message = new byte[256];
			while (true) {
				if (true) {
					//System.out.println(playerState.getShoot());
					message = playerState.toByte();
					packet = new DatagramPacket(message, message.length, gameVars.serverAddress, GameParameters.SERVER_PORT_IN);
					socketOutput.send(packet);
					tick = false;
				}
				elapsed = delay.get();
				Thread.sleep(20);
			}
			//socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void tick() {
		tick = true;
	}
	
	void debug () {
		System.out.println("Elapsed_Output: " + elapsed + " Loc: " + playerState.getPos());
	}

}
