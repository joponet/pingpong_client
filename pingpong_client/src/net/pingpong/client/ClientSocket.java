package net.pingpong.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.pingpong.lib.GameConst;
import net.pingpong.lib.MatchState;
import net.pingpong.lib.PlayerState;

public class ClientSocket extends Thread {
	//int idPlayer;
	ObjectInputStream input;
	ObjectOutputStream output;
	PlayerState playerState;
	MatchState matchState;	
	boolean tick;

	ClientSocket() {
		tick = false;
	}
	
	public void run() {
		Socket socket;
		playerState = new PlayerState();
		try {
//			socket = new Socket(GameConst.SERVER_IP,GameConst.PORT);
			socket = new Socket("192.168.1.40",GameConst.PORT);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			//idPlayer = input.readShort();
			//System.out.println(idPlayer);
			while (true) {
				if (tick) {
					output.reset();
					output.writeObject(playerState);
					output.flush();
					System.out.println(playerState.getPos());
					matchState = (MatchState) input.readObject();
					System.out.println(playerState.getPos());
					//System.out.println(matchState.getP1posX());
					//System.out.println(cont);
					tick = false;
				}
				Thread.sleep(5);
			}
			//socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

}
