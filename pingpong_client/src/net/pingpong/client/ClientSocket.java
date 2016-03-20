package net.pingpong.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.pingpong.lib.MatchState;
import net.pingpong.lib.PlayerState;

public class ClientSocket extends Thread {
	int idPlayer;
	ObjectInputStream input;
	ObjectOutputStream output;
	PlayerState playerState;
	MatchState matchState;		

	ClientSocket() {
		
	}
	
	public void run() {
		Socket socket;
		playerState = new PlayerState();
		try {
			socket = new Socket("192.168.1.40",5000);
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			idPlayer = input.readShort();
			System.out.println(idPlayer);
			while (true) {
				matchState = (MatchState) input.readObject();
				output.writeObject(playerState);
				output.flush();
				//System.out.println(playerState.getPos());
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
		}
	}

}
