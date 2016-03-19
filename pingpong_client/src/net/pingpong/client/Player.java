package net.pingpong.client;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	final int SHOOTTICK = 20;
	final int SPEED = 3;
	final int width=50;
	final int height=10;
	int x;
	int y;
	int xmax;
	int xmin;
	int shoottick =0;
	int player;
	int goals=0;
	Input input;
	Ground ground;
	Joystick joystick;
	//int ymax;
	Player (Ground ground, Input input, int player) {
		this.ground = ground;
		this.input = input;
		this.player = player;
	}
	void init () {
		xmax = ground.width-width;
		xmin=0;
		if (player == 1) y = ground.y+ground.height-height;
		else y = ground.y;
		x = xmax/2;
		shoottick=0;
	}
	
	
	void incgoals() {
		goals++;
	}
	
	void shoot() {
		shoottick=SHOOTTICK;
	}

	void tick() {
		if (player==1) x += input.xa1*SPEED;
		else x += input.xa2*SPEED;
		if (x<xmin) x=0;
		if (x>xmax) x=xmax;
	}
		
	void clear(Graphics g) {
		g.clearRect(x, y, width, height);		
	}
	
	void draw(Graphics g) {
		if (shoottick >0) {
			g.setColor(Color.GREEN);
			shoottick--;
		}
		else {
			g.setColor(Color.CYAN);			
		}
		g.drawRoundRect(x, y, width-1, height-1, 5, 5);
		g.fillRoundRect(x, y, width-1, height-1,5,5);
	}
}
