package net.pingpong.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	final int size = 24;
	final int offset = 50;
	final int btOffset = 15;
	int btWidth;
	int width, height;
	int selected=0;
	boolean active=false;
	boolean enter=false;
	Input input;
	String[] option;
	Menu () {
		
	}
	
	void init (int width, int height, Input input) {
		this.width = width;
		this.height = height;
		this.input = input;
		btWidth = width - (offset*2);
		option = new String[]{"Start","Continue","Quit"};
		active=false;
	}
	
	void tick() {
		if (!active) return;
		if (input.up) {selected--; input.up=false;}
		if (input.down) {selected++; input.down=false;}
		if (selected<0) selected=0;
		if (selected>option.length-1) selected=option.length-1;
		if (input.enter) {
			enter=true;
			input.enter=false;
		}
	}
	
	void draw(Graphics g) {
		if (!active) return;
		Font font = new Font(Font.SERIF, Font.PLAIN, size);
		g.setFont(font);

		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fontMetrics = g2d.getFontMetrics();
		for (int i=0; i<option.length; i++) {
			int x = (width-fontMetrics.stringWidth(option[i]))/2;
			int y = height/2 + (fontMetrics.getHeight()+btOffset)*i;
			if (i==selected) g.setColor(Color.YELLOW);
			else g.setColor(Color.BLUE);
			g.drawRoundRect((width-btWidth)/2, y-fontMetrics.getAscent(), btWidth, fontMetrics.getHeight(),15,15);
			g.fillRoundRect((width-btWidth)/2, y-fontMetrics.getAscent(), btWidth, fontMetrics.getHeight(),15,15);
			g.setColor(Color.RED);
			g.drawString(option[i], x, y);
		}
	}
}
