package net.pingpong.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.pingpong.lib.GameConst;
import net.pingpong.lib.Pilota;

public class PilotaDraw {
	/*
	int x;
	int y;
	double xd;
	double yd;
	int xa;
	int ya;
	*/
	//int delaystart=GameConst.DELAYSTART;
	//double speed;
	//boolean goal;
	//boolean stop;
	boolean active;
	//boolean reset;
	//Pilota pilota;
	Sound sound;
	Ground ground;
	Player player1, player2;
	
	PilotaDraw (Ground ground, Player player1, Player player2, Sound sound) {
		this.ground = ground;
		this.player1 = player1;
		this.player2 = player2;
		this.sound = sound;
	}
	
	void init() {
		//pilota = new Pilota();
		//pilota.init();
	}
	/*
	void reset() {
		pilota.reset();
	}
	
	void start() {
		pilota.start();
		sound.horn();
	}
	
	void stop() {
		pilota.stop();
	}
	
	boolean stopped () {
		return pilota.stopped();
	}
	*/
	void tick() {
		//pilota.active = active;
		//pilota.tick();
		// goal = pilota.get_goal();
		/*
		if (goal ==1) {
			player1.incgoals();
			sound.goal();
		}
		
		if (goal == 2) {
			player2.incgoals();
			sound.goal();
		}
			
		int centerx=pilota.get_x()+(GameConst.BALL_WIDTH/2);
		if((pilota.get_ya() > 0) && (pilota.get_y()+GameConst.BALL_HEIGHT >= player1.y-32) && (centerx >= player1.x) && (centerx <= player1.x+player1.width-1)) {
			pilota.shoot(-1);
			player1.shoot();
			sound.click();
		}
		else if ((pilota.get_ya() < 0) && (pilota.get_y() <= player2.y-32+player2.height-1) && (centerx >= player2.x) && (centerx <= player2.x+player2.width-1)) {
			pilota.shoot(1);
			player2.shoot();
			sound.click();
		} */
	}
		
	void clear(Graphics g) {
		//g.clearRect(pilota.get_x(), pilota.get_y()+32, GameConst.BALL_WIDTH, GameConst.BALL_HEIGHT);		
	}
	
	void draw(Graphics g) {
		if (!active) return;
//		g.setColor(Color.RED);
//		g.fillOval(x, y, width, height);
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/images/ball_16.png"));
			//g.drawImage(image, pilota.get_x(), pilota.get_y()+32, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
