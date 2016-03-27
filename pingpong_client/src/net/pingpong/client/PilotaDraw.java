package net.pingpong.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.pingpong.lib.GameParameters;

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
	Pilota pilota;
	Sound sound;
	Ground ground;
	Player player1, player2;
	
	PilotaDraw (Ground ground, Player player1, Player player2, Sound sound, Pilota pilota) {
		this.ground = ground;
		this.player1 = player1;
		this.player2 = player2;
		this.sound = sound;
		this.pilota = pilota;
	}
			
	void clear(Graphics g) {
		g.clearRect(pilota.get_xmin(), pilota.get_ymax(), GameParameters.BALL_WIDTH, GameParameters.BALL_HEIGHT);		
	}
	
	void draw(Graphics g) {
		if (!pilota.active) return;
//		g.setColor(Color.RED);
//		g.fillOval(x, y, width, height);
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/images/ball_16.png"));
			g.drawImage(image, pilota.get_xmin(), pilota.get_ymin(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
