package net.pingpong.client;

import net.pingpong.lib.GameParameters;
import net.pingpong.lib.MatchState;

public class Pilota {
	final int width = GameParameters.BALL_WIDTH;
	final int height = GameParameters.BALL_HEIGHT;
	int xmax;
	int xmin;
	int ymax;
	int ymin;
	int x;
	int y;
	//double xd;
	//double yd;
	//int xa;
	int ya;
	//int delaystart=GameParameters.DELAYSTART;
	double speed;
	int goal; // 0: no goal, 1:goal playerl, 2: goal player2
	boolean stop;
	public boolean active;
	boolean reset;
	boolean shoot;
	
	MatchState matchState;
	
	public Pilota(MatchState matchState) {
		this.matchState = matchState;
	}
	
	public void init(int groundWidth, int groundHeight) {
		xmax = groundWidth-width;
		xmin=0;
		//ymax = GameParameters.HEIGHT-height-1;
		ymax = groundHeight-height;
		ymin = 0;
		//xa=1;
		//ya=1;
		reset();
		stop=true;
		active=false;
	}
	
	public void reset() {
		speed = GameParameters.MAXSPEED;
		goal=0;
		shoot = false;
		reset=false;
		x = xmax/2;
		//xd=x;
		y = ymax/2;
		//yd=y;
	}
	
	public void start() {
		stop=false;
		//delaystart = GameParameters.DELAYSTART;
	}
	
	public void stop() {
		stop=true;
	}
	
	public boolean stopped() {
		return stop;
	}
	
	public void tick() {
		int xTo = matchState.getBposX();
		int yTo = matchState.getBposY();
		
		int xDif = xTo - x;
		int yDif = yTo - y;
		
		//xDif = (int) ((xDif > speed)?speed:xDif);
		//yDif = (int) ((yDif > speed)?speed:xDif);
		
		// posició on arribar
		if (xDif>=0) {
			x += (xDif>speed)?speed:xDif;
		}
		else {
			x += (xDif<speed)?-speed:xDif;
		}
		if (yDif>=0) {
			y += (yDif>speed)?speed:yDif;
		}
		else {
			y += (yDif<speed)?-speed:yDif;
		}
		//x = xTo;
		//y = yTo;
		/*
		if (!active) return;
		if (stop) return;
		if (goal>0) reset();
		if (reset) reset();
		if (delaystart>0) {
			delaystart--;
			return;
		}*/
		
		/*
		xd +=xa*speed;
		x = (int) xd;
		yd +=ya*speed;
		y = (int) yd;
		*/
		/*
		if (x<=xmin) {
			xa=1;
		}

		if (x>=xmax) {
			xa=-1;
		}
		*/
		if (y<=ymin) {
			y = ymin;
			//stop=true;
			//ya=1;
			//goal=1;
		}
		
		if (y>=ymax) {
			y=ymax;
			stop=true;
			ya=-1;
			goal=2;
		}
		/*
		int centerx=x+(width/2);
		if((ya == 1) && (y+height >= player1.) && (centerx >= player1.x) && (centerx <= player1.x+player1.width-1)) {
			ya=-1;
			if (speed<GameConst.MAXSPEED) speed += GameConst.INCSPEED;
			//player1.shoot();
			//sound.click();
			shoot = true;
		}
		else if ((ya <0) && (y <= player2.y+player2.height-1) && (centerx >= player2.x) && (centerx <= player2.x+player2.width-1)) {
			ya=1;
			if (speed<GameConst.MAXSPEED) speed += GameConst.INCSPEED;
			//player2.shoot();
			//sound.click();
			shoot = true;
		}*/
	}	
	
	public int getGoal () {
		int goal;
		goal = this.goal;
		this.goal = 0;
		return goal;
	}
	
	public void shoot(int ya) {
		this.ya = ya;
		//if (speed<GameParameters.MAXSPEED) speed += GameParameters.INCSPEED;
	}
	
	public int get_ymin () {
		return y+33;
	}
	
	public int get_ymax () {
		return y+33+height;
	}

	public int get_xmin () {
		return x;
	}
	
	public int get_xmax () {
		return x+width;
	}

	public int get_ya () {
		return ya;
	}
	
	public int get_centerX () {
		return x+(width/2);
	}
}
