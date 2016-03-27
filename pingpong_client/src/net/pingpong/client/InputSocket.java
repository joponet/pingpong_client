package net.pingpong.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

import net.pingpong.lib.Delay;
import net.pingpong.lib.GameParameters;
import net.pingpong.lib.GameVars;
import net.pingpong.lib.MatchState;

public class InputSocket extends Thread {
	DatagramSocket socketInput;
	GameVars gameVars;
	MatchState matchState;	
	boolean tick;
	long elapsed;

	InputSocket(GameVars gameVars) {
		this.gameVars = gameVars;
		matchState = new MatchState();
		tick = false;
		try {
			socketInput = new DatagramSocket(GameParameters.CLIENT_PORT_IN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		Delay delay = new Delay();
		try {
			DatagramPacket packet;
			byte[] buffer = new byte[256];
			packet = new DatagramPacket(buffer, buffer.length);
			while (true) {
				if (true) {
					socketInput.receive(packet);
					matchState.set(packet.getData());
					tick = false;
				}
				elapsed = delay.get();
				Thread.sleep(5);
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
		if (matchState != null) {
			//System.out.println("Elapsed_Input: " + elapsed + " Rem: " + matchState.getRposX());
			System.out.printf("Gols_1: %d Gols_2: %d\n",matchState.getLgoals(),matchState.getRgoals());
			System.out.printf("Pause: %b\n",matchState.isPaused());
		}	
	}
}
