package net.pingpong.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import net.pingpong.lib.Pilota;
import net.pingpong.lib.Tick;

public class Game extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1231466043028324788L;
	/**
	 * 
	 */
	JFrame frame;
	int xa=0;
	int ya=0;
	boolean running=true;
	int gameFinish=10;

	Tick tick;
	ClientSocket clientSocket;
	Input input;
	Status status;
	Ground ground;
	Player playerLoc;
	Player playerRem;
	PilotaDraw pilotaDraw;
	Pilota pilota;
	Sound sound;
	Menu menu;
	boolean init = false;

	Game() {
		// frame
		frame = new JFrame();
		frame.setLocation(100, 100);
		frame.setResizable(false);
		frame.setSize(300, 500);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);		

		// game classes
		tick = new Tick();
		clientSocket = new ClientSocket();
		input = new Input();
		sound = new Sound();
		ground = new Ground();
		playerLoc = new Player(ground,input,1);
		playerRem = new Player(ground,input,2);
		pilotaDraw = new PilotaDraw(ground, playerLoc, playerRem, sound);
		pilota = new Pilota();
		status = new Status(playerLoc,playerRem);
		menu = new Menu();

		// this.canvas
		this.setBackground(Color.GRAY);
		this.addKeyListener(input);
		this.requestFocus();
	}
	
	void Run() {
		// init
		status.init(this.getWidth());
		ground.init(0,status.height+1,this.getWidth(),this.getHeight()-status.height-1);
		playerLoc.init();
		playerRem.init();
		pilotaDraw.init();
		pilota.init();
		menu.init(this.getWidth(),this.getHeight(),input);
		init = true;
				
		// graphics
		BufferStrategy buffer;
		this.createBufferStrategy(2);
		buffer = this.getBufferStrategy();

		// running
		espera(1);
		tick.start();
		menu.active=true;
		
		clientSocket.start();
		
		while (running) {
			if (tick.update()) {
				
				Graphics g = buffer.getDrawGraphics();
				draw(g);
				g.dispose();
				buffer.show();
				clientSocket.playerState.set(playerLoc.x, false, pilota.get_ya());					
			}
			if (input.escape) running = false;
			if (input.start) {
				input.start=false;
				if (pilota.stopped()) {
					pilota.start();
					sound.horn();
				}
				else {
					pilota.stop();
					pilota.active=false;
					menu.active=true;
				}
			}
			if (menu.enter) {
				menu.enter=false;
				if (menu.selected==0) {
					menu.active=false;
					pilota.active=true;
					pilota.reset();
					playerLoc.goals=0;
					playerRem.goals=0;
					pilota.start();
					sound.horn();
				}
				if (menu.selected==1) {
					menu.active=false;
					pilota.active=true;
					pilota.start();
					sound.horn();
				}
				if (menu.selected==2) running=false;
			}
		}
		System.exit(0);
	}
	
	public void draw(Graphics g) {
		if (!init) return;
		input.tick();

		// clear
		pilotaDraw.clear(g);
		playerLoc.clear(g);
		playerRem.clear(g);
		status.clear(g);
		
		// tick
		if (init) {
			clientSocket.tick();
			playerLoc.tick();
			playerRem.tick();
			playerRem.x = clientSocket.matchState.getRposX();
			pilota.tick();
			menu.tick();
		}
		
		// draw
		ground.draw(g);
		playerLoc.draw(g);
		playerRem.draw(g);
		status.draw(g);
		pilotaDraw.draw(g);
		menu.draw(g);
	}
		
	void espera(long temps) {
		long ms = temps*1000L;
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void ticktime () {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void paint(Graphics g) {
		draw(g);
	}

}
