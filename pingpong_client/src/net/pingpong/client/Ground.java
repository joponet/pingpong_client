package net.pingpong.client;

import java.awt.Color;
import java.awt.Graphics;

public class Ground {
	final int thickness=5;
	int x,y,width,height;
	Ground () {
	}
	
	void init (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width=width;
		this.height=height;
	}
	
	void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, height);
		g.clearRect(x+thickness, y+thickness, width-thickness*2, height-thickness*2);
	}
}
