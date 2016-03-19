package net.pingpong.client;

import static net.pingpong.client.Joystick.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	int xa1=0;
	int ya1=0;
	int xa2=0;
	int ya2=0;
	boolean escape=false;
	boolean pause=false;
	boolean start=false;
	boolean startReleased=true;
	boolean up=false;
	boolean down=false;
	boolean enter=false;
	Joystick joystick1;
	Joystick joystick2;
	
	Input () {
		joystick1 = new Joystick(0);
		joystick2 = new Joystick(1);
	}
	
	public void tick() {
		if (joystick1.tick()) {
			xa1=joystick1.getXdir();
			if (!joystick1.getButton(START_POS,START_VAL)) startReleased = true;
			else if (startReleased) {
				start = true;
				startReleased = false;
			}
		}
		if (joystick2.tick()) xa2=joystick2.getXdir();
	}

	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_RIGHT) xa1=1;
        if (e.getKeyCode()== KeyEvent.VK_LEFT) xa1=-1;
        if (e.getKeyCode()== KeyEvent.VK_UP) {ya1=-1; up=true;}
        if (e.getKeyCode()== KeyEvent.VK_DOWN) {ya1=1; down=true;}

        if (e.getKeyCode()== KeyEvent.VK_C) xa2=1;
        if (e.getKeyCode()== KeyEvent.VK_Z) xa2=-1;
        if (e.getKeyCode()== KeyEvent.VK_S) ya2=-1;
        if (e.getKeyCode()== KeyEvent.VK_X) ya2=1;
        
        if (e.getKeyCode()== KeyEvent.VK_ESCAPE) escape=true;
        if (e.getKeyCode()== KeyEvent.VK_SPACE) start=true;
        if (e.getKeyCode()== KeyEvent.VK_ENTER) enter=true;
    }
	public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_RIGHT) xa1=0;
        if (e.getKeyCode()== KeyEvent.VK_LEFT) xa1=0;
        if (e.getKeyCode()== KeyEvent.VK_UP) ya1=0;
        if (e.getKeyCode()== KeyEvent.VK_DOWN) ya1=0;
        
        if (e.getKeyCode()== KeyEvent.VK_C) xa2=0;
        if (e.getKeyCode()== KeyEvent.VK_Z) xa2=0;
        if (e.getKeyCode()== KeyEvent.VK_S) ya2=0;
        if (e.getKeyCode()== KeyEvent.VK_X) ya2=0;
	}
	public void keyTyped(KeyEvent e) { }
}
