package net.pingpong.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2479557697506120532L;
	JFrame frame;
	int xa=0;
	int ya=0;
	boolean running=true;
	int gameFinish=10;

	Input input;
	Status status;
	Ground ground;
	Player player1;
	Player player2;
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
		input = new Input();
		sound = new Sound();
		ground = new Ground();
		player1 = new Player(ground,input,1);
		player2 = new Player(ground,input,2);
		pilota = new Pilota(ground, player1, player2, sound);
		status = new Status(player1,player2);
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
		player1.init();
		player2.init();
		pilota.init();
		menu.init(this.getWidth(),this.getHeight(),input);
		init = true;
		
		// game time
		final double nstick = 1000000000D / 120D;
		double delay = 0;
		
		// graphics
		BufferStrategy buffer;
		this.createBufferStrategy(2);
		buffer = this.getBufferStrategy();

		// running
		espera(1);
		long lasttime = System.nanoTime();
		menu.active=true;
		while (running) {
			long now = System.nanoTime();
			if (input.pause) lasttime=now;
			delay += (now-lasttime) / nstick;
			lasttime = now;
			if (delay>=1) {
				Graphics g = buffer.getDrawGraphics();
				draw(g);
				g.dispose();
				buffer.show();
				delay--;
			}
			if (input.escape) running = false;
			if (input.start) {
				input.start=false;
				if (pilota.stop) {
					pilota.start();					
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
					pilota.reset=true;
					player1.goals=0;
					player2.goals=0;
					pilota.start();
				}
				if (menu.selected==1) {
					menu.active=false;
					pilota.active=true;
					pilota.start();
				}
				if (menu.selected==2) running=false;
			}
			ticktime();
		}
		System.exit(0);
	}
	
	public void draw(Graphics g) {
		if (!init) return;
		input.tick();

		// clear
		pilota.clear(g);
		player1.clear(g);
		player2.clear(g);
		status.clear(g);
		
		// tick
		if (init) {
			player1.tick();
			player2.tick();
			pilota.tick();
			menu.tick();
		}
		
		// draw
		ground.draw(g);
		player1.draw(g);
		player2.draw(g);
		status.draw(g);
		pilota.draw(g);
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
