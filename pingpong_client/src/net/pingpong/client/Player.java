package net.pingpong.client;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	final int SHOOTTICK = 20;
	final int SPEED = 3;
	final int width=50;
	final int height=10;
	int x;
	int xTo;
	int xa2;
	int y;
	int xmax;
	int xmin;
	int shoottick =0;
	int player;
	int shoot;
	int goals=0;
	Input input;
	Ground ground;
	Pilota pilota;
	Joystick joystick;
	Sound sound;
	
	//int ymax;
	Player (Ground ground, Input input, int player, Pilota pilota) {
		this.ground = ground;
		this.input = input;
		this.player = player;
		this.pilota = pilota;
		sound = new Sound();
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
	
	void goTo (int x) {
		xTo = x;
	}

	void tick() {
		if (player==1) x += input.xa1*SPEED;
		else {
			if (x > xTo + SPEED) {
				xa2=-1;
			}
			else if (x < xTo - SPEED) {
				xa2=1;
			}
			else {
				xa2=0;
				x=xTo;
			}
			x += xa2*(SPEED+1);
		}
		if (x<xmin) x=0;
		if (x>xmax) x=xmax;
		int centerx=pilota.get_centerX();
		//if (player==1) System.out.printf("id:%d pilota_y:%d player_y:%d player_x:%d\n",player,pilota.get_ymax(),y,x);
		//if((player == 1) && (pilota.get_ya() > 0) && (pilota.get_ymax() >= y) && (centerx >= x) && (centerx <= x+width-1)) {
		// shoot
		if((player == 1) && (pilota.get_ymax() >= y) && (centerx >= x) && (centerx <= x+width-1)) {
			pilota.shoot(-1);
			shoottick=SHOOTTICK;
			if (shoot==0) sound.click();
			shoot = pilota.get_ya();
			shoot();
		}
		else {
			shoot=0;
		}
		if ((player == 2) && (pilota.get_ya() <0) && (pilota.get_ymin() <= y+height-1) && (centerx >= x) && (centerx <= x+width-1)) {
			pilota.shoot(1);
			shoot = pilota.get_ya();
			System.out.println(shoot);
			//player2.shoot();
			//sound.click();
			//shoot = true;
		}
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
