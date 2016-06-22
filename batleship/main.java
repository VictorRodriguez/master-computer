package paint;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class main extends JFrame implements ActionListener{
	
	String global_color="blue";
	
	JRadioButton red_button;
	JRadioButton blue_button;
	JRadioButton black_button;
	JRadioButton yellow_button;
	JRadioButton green_button;
	Mypanel draw=new Mypanel();
	
	JButton rectangle_button;
	JButton oval_button;
	
	public main(Mypanel draw){
		
		this.draw = draw;
		
		red_button= new JRadioButton("red");
		red_button.addActionListener(this);
		
		blue_button=new JRadioButton("blue");
		blue_button.addActionListener(this);

		black_button=new JRadioButton("black");
		black_button.addActionListener(this);

		yellow_button=new JRadioButton("yellow");
		yellow_button.addActionListener(this);

		green_button=new JRadioButton("green");
		green_button.addActionListener(this);

		oval_button=new JButton("circle");
		oval_button.addActionListener(this);

		rectangle_button=new JButton("rectangle");
		rectangle_button.addActionListener(this);
		
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(red_button);
		radioPanel.add(blue_button);
		radioPanel.add(black_button);
		radioPanel.add(yellow_button);
		radioPanel.add(green_button);
		radioPanel.add(oval_button);
		radioPanel.add(rectangle_button);
		
		JPanel fullPanel = new JPanel(new GridLayout(0,2));
		fullPanel.add(radioPanel);
		fullPanel.add(draw);
		
		add(fullPanel,BorderLayout.WEST);
        //setContentPane(new DrawPane());

        //setContentPane(draw);

		//add(draw,BorderLayout.CENTER);
	}		
	
	 //create a component that you can actually draw on.
    class DrawPane extends JPanel{
      public void paintComponent(Graphics g){
        //draw on g here e.g.
    	 g.setColor(Color.red);
         g.fillRect(20, 20, 100, 200);
       }
   }

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == red_button){
			global_color="red";
			System.out.println("red");
			
		} else if(e.getSource() == blue_button){
			global_color="blue";
			System.out.println("blue");
		}
		else if(e.getSource()==black_button){
			global_color="black";
			System.out.println("black");
		}
		else if(e.getSource()==yellow_button){
			global_color="yellow";
			System.out.println("yellow");
		}
		else if(e.getSource()==green_button){
			global_color="green";
			System.out.println("green");

		}
		else if (e.getSource()==rectangle_button){
			System.out.println("rectangle_button");
			draw.draw_figure(global_color,"rectangle");

		}
		
		else if (e.getSource()==oval_button){
			System.out.println("oval_button");
			draw.draw_figure(global_color,"oval");

		}
	}
	
	public static void main(String[] args){
		Mypanel draw=new Mypanel();
		main window=new main(draw);
		window.setBounds(200,200,200,200);
		window.pack();
		window.setVisible(true);
	}

}