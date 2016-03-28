package net.pingpong.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

import net.pingpong.lib.GameParameters;
import net.pingpong.lib.GameVars;
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
	InputSocket inputSocket;
	OutputSocket outputSocket;
	GameVars gameVars;
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

	Game(GameVars gameVars) {
		// frame
		frame = new JFrame();
		frame.setLocation(100, 100);
		frame.setResizable(false);
		frame.setSize(GameParameters.FRAME_WIDTH, GameParameters.FRAME_HEIGHT);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);		

		// game classes
		this.gameVars = gameVars;
		inputSocket = new InputSocket(gameVars);
		outputSocket = new OutputSocket(gameVars);
		tick = new Tick();
		input = new Input();
		sound = new Sound();
		ground = new Ground();
		pilota = new Pilota(inputSocket.matchState);
		pilotaDraw = new PilotaDraw(ground, playerLoc, playerRem, sound, pilota);
		playerLoc = new Player(ground,input,1,pilota);
		playerRem = new Player(ground,input,2,pilota);
		status = new Status(inputSocket.matchState);
		menu = new Menu();
		System.out.printf("W:%d H:%d",this.getWidth(),this.getHeight());

		// this.canvas
		this.setBackground(Color.GRAY);
		this.addKeyListener(input);
		this.requestFocus();
	}
	
	void Run() {
		// init
		status.init(this.getWidth());
		ground.init(0,status.height+1,this.getWidth(),GameParameters.GAME_HEIGHT-1);
		playerLoc.init();
		playerRem.init();
		//pilotaDraw.init();
		pilota.init(this.getWidth(),GameParameters.GAME_HEIGHT);
		menu.init(this.getWidth(),this.getHeight(),input);
						
		// graphics
		BufferStrategy buffer;
		this.createBufferStrategy(2);
		buffer = this.getBufferStrategy();

		// running
		espera(1);
		tick.start();
		menu.active=true;
		
		inputSocket.start();
		outputSocket.start();
		init = true;
		
		while (running) {
			if (tick.update()) {
				Graphics g = buffer.getDrawGraphics();
				draw(g);
				g.dispose();
				buffer.show();
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
			if (inputSocket.matchState.isPaused()) {
				menu.active=true;
				pilota.goal = 0;
				pilota.active=false;
			}
			else {
				if (menu.active) {
					pilota.active=true;
					sound.horn();
				}
				menu.active=false;
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
			playerLoc.tick();
			//outputSocket.playerState.set(playerLoc.x, false, pilota.get_ya());
			outputSocket.playerState.setPos(playerLoc.x);
			if (pilota.goal==2 && playerLoc.shoot==0) outputSocket.playerState.Goal();
			else outputSocket.playerState.resetGoal();
			outputSocket.playerState.setShoot(playerLoc.shoot);
			outputSocket.tick();
			//if (inputSocket.matchState != null) {
			playerRem.goTo(inputSocket.matchState.getRposX());
			//}
			playerRem.tick();			
			pilota.tick();
			menu.tick();
			//inputSocket.debug();
			//outputSocket.debug();
			pilota.debug();
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
