package net.pingpong.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.pingpong.lib.GameConst;
import net.pingpong.lib.MatchState;
import net.pingpong.lib.PlayerState;

public class OutputSocket extends Thread {
	//int idPlayer;
	Socket socketOutput;
	//ObjectInputStream input;
	ObjectOutputStream output;
	PlayerState playerState;
	//MatchState matchState;	
	boolean tick;
	long elapsed;

	OutputSocket(Socket socket) {
		tick = false;
		playerState = new PlayerState();
		playerState.setPos(GameConst.WIDTH/2);
		socketOutput = socket; 
	}
	
	public void run() {
		// Socket socketInput;
		long now, last;
		last = System.nanoTime();
		try {
//			socket = new Socket(GameConst.SERVER_IP,GameConst.PORT);
			//socketOutput = new Socket("192.168.1.40",GameConst.PORT);
			//socketInput = new Socket("192.168.1.40",GameConst.PORT+1);
			output = new ObjectOutputStream(socketOutput.getOutputStream());
			//input = new ObjectInputStream(socketInput.getInputStream());
			//idPlayer = input.readShort();
			//System.out.println(idPlayer);
			//int i;
			while (true) {
				if (true) {
					output.reset();
					output.writeObject(playerState);
					//output.writeInt(playerState.getPos());
					output.flush();
					//System.out.println(playerState.getPos());
					//matchState = (MatchState) input.readObject();
					//i = input.readInt();
					//System.out.println(playerState.getPos());
					//System.out.println(matchState.getP1posX());
					//System.out.println(cont);
					tick = false;
				}
				now = System.nanoTime();
				elapsed = (now-last)/1000000;
				last = now;
				//System.out.println("Elapsed: " + elapsed + " Pos: " + playerState.getPos() + "/" + i);
				Thread.sleep(50);
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
