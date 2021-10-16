package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import visuals.Display;

public class Mazes {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Maze");
		
		addComponentsToPane(frame.getContentPane());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void addComponentsToPane(Container pane) {
		Display display = new Display();
		display.setPreferredSize(new Dimension(500, 500));
		display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));
		
		String[] mazes = {"DFS", "Prim's"};
		JComboBox mazeList = new JComboBox(mazes);
		mazeList.setPreferredSize(new Dimension(50, 50));
		
		String[] solvers = {"Wall Follower"};
		JComboBox solverList = new JComboBox(solvers);
		solverList.setPreferredSize(new Dimension(50, 50));
		
		JButton go = new JButton("Go");
		go.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(String.valueOf(mazeList.getSelectedItem()));
				display.changeRestart(true);
				display.switchMazeGen(String.valueOf(mazeList.getSelectedItem()));
				display.repaint();
				display.changeRestart(false);
				display.repaint();
			}
		});
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.reset();
			}
		});
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.LINE_AXIS));
		bottom.add(Box.createHorizontalGlue());
		bottom.add(mazeList);
		bottom.add(solverList);
		bottom.add(go);
		bottom.add(stop);
		
		
		
		
		pane.add(display, BorderLayout.CENTER);
		pane.add(bottom, BorderLayout.PAGE_END);
	}
}
