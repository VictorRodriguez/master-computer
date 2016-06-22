package paint;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
public class Mypanel extends JPanel{
	String color = "";
	String figure = "";

	public Mypanel(){
		super();
		//my_g = g; 
      //draw on g here e.g.

     }
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if  (color == "blue"){
	   		g.setColor(Color.blue);

		}
		if  (figure == "rectangle"){
			 g.fillRect(20, 20, 50, 50);
		}
	   
		if  (figure == "oval"){
			 g.fillOval(20, 20, 50, 50);
		}

	}

    public void draw_figure(String color,String figure){
    	this.color = color;
    	this.figure = figure;

		System.out.println("draw_rectangle");
      	repaint();

    }

 }
