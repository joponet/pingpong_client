package net.pingpong.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pilota {
	final int SPEED = 1;
	final double INCSPEED = 0.1;
	final int MAXSPEED = 3;
	final int DELAYSTART = 180;
	final int width = 16;
	final int height = 16;
	int xmax;
	int xmin;
	int ymax;
	int ymin;
	int x;
	int y;
	double xd;
	double yd;
	int xa;
	int ya;
	int delaystart=DELAYSTART;
	double speed;
	boolean goal;
	boolean stop;
	boolean active;
	boolean reset;
	Sound sound;
	Ground ground;
	Player player1, player2;
	
	Pilota (Ground ground, Player player1, Player player2, Sound sound) {
		this.ground = ground;
		this.player1 = player1;
		this.player2 = player2;
		this.sound = sound;
	}
	
	void init() {
		xmax = ground.width-this.width;
		xmin=0;
		ymax = ground.y+ground.height-1-this.height;
		ymin = ground.y;
		xa=1;
		ya=-1;
		reset();
		stop=true;
		active=false;
//		this.start();
	}
	
	void reset() {
		speed = SPEED;
		goal=false;
		reset=false;
		x = xmax/2;
		xd=x;
		y = ymax/2;
		yd=y;
	}
	
	void start() {
		stop=false;
		delaystart = DELAYSTART;
		sound.horn();
	}
	
	void stop() {
		stop=true;
	}
	
	void tick() {
		if (!active) return;
		if (stop) return;
		if (goal) reset();
		if (reset) reset();
		if (delaystart>0) {
			delaystart--;
			return;
		}
		
		xd +=xa*speed;
		x = (int) xd;
		yd +=ya*speed;
		y = (int) yd;
		
		if (x<=xmin) {
			xa=1;
		}

		if (x>=xmax) {
			xa=-1;
		}

		if (y<ymin) {
			ya=1;
			player1.incgoals();
			sound.goal();
			goal=true;
			stop=true;
		}
		
		if (y>ymax) {
			ya=-1;
			player2.incgoals();
			sound.goal();
			goal=true;
			stop=true;
		}
		
		int centerx=x+(width/2);
		if((ya == 1) && (y+height >= player1.y) && (centerx >= player1.x) && (centerx <= player1.x+player1.width-1)) {
			ya=-1;
			if (speed<MAXSPEED) speed += INCSPEED;
			player1.shoot();
			sound.click();
		}
		else if ((ya <0) && (y <= player2.y+player2.height-1) && (centerx >= player2.x) && (centerx <= player2.x+player2.width-1)) {
			ya=1;
			if (speed<MAXSPEED) speed += INCSPEED;
			player2.shoot();
			sound.click();
		}			
	}
		
	void clear(Graphics g) {
		g.clearRect(x, y, width, height);		
	}
	
	void draw(Graphics g) {
		if (!active) return;
//		g.setColor(Color.RED);
//		g.fillOval(x, y, width, height);
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/images/ball_16.png"));
			g.drawImage(image, x, y, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
