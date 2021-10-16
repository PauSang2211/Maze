package visuals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.Timer;

import misc.Cells;
import misc.Constants;
import solvers.DFS_Solver;
import generation.DFS;
import generation.Prim;

public class Display extends JPanel implements ActionListener{
	private Timer timer;
	private Queue<Cells> backtrace;
	private LinkedList<Cells> drawBT;
	private static Cells[][] map, pred, wasHere;
	private DFS dfs;
	private Prim prim;
	private DFS_Solver dfs_Solver;
	private String mazeGen;
	private boolean restart, finishedGen, finishedSolve;
	
	public Display() {
		this.backtrace = new LinkedList<Cells>();
		this.drawBT = new LinkedList<Cells>();
		
		this.restart = true;
		this.finishedGen = false;
		this.finishedSolve = false;
		this.mazeGen = "DFS";
		
		timer = new Timer(0, this);
		
		this.addMouseListener(new MouseHandler());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (!restart) {
			if (!timer.isRunning()) {
				timer.start();
			}
			if (finishedGen) {
				if (!finishedSolve) {
					g.setColor(Color.GRAY);
					for(int i = 0; i < Constants.MAP_WIDTH; i++) {
						for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
							Cells currCell = wasHere[i][j];
							if (currCell != null) {
								g.fillRect(Constants.SIZE*i, Constants.SIZE*j, Constants.SIZE, Constants.SIZE);
							}
						}
					}
				} else {
					start(g);
				}
				
				
				
				
				g.setColor(Color.CYAN);
				for(Cells cell : drawBT) {
					
					g.fillRect(Constants.SIZE*cell.x(), Constants.SIZE*cell.y(), Constants.SIZE, Constants.SIZE);
				}
				
				if (!backtrace.isEmpty()) {
					g.setColor(Color.CYAN);
					Cells currCell = backtrace.peek();
					
					g.fillRect(Constants.SIZE*currCell.x(), Constants.SIZE*currCell.y(), Constants.SIZE, Constants.SIZE);
					
					drawBT.add(backtrace.remove());
				}
				
				g.setColor(Color.YELLOW);
				g.fillRect(Constants.SIZE*dfs_Solver.startX(), Constants.SIZE*dfs_Solver.startY(), Constants.SIZE, Constants.SIZE);
				g.setColor(Color.ORANGE);
				g.fillRect(Constants.SIZE*dfs_Solver.endX(), Constants.SIZE*dfs_Solver.endY(), Constants.SIZE, Constants.SIZE);
				
				/*
				if (!backtrace.isEmpty()) {
					g.setColor(Color.CYAN);
					Cells currCell = backtrace.peek();
					if (prim != null) {
						g.fillRect(Constants.SIZE*currCell.x(), Constants.SIZE*currCell.y(), Constants.SIZE, Constants.SIZE);
					}
					
					backtrace.remove();
				}
				*/
			} else {
				start(g);
			}
		} else {
			dfs = null;
			prim = null;
			
			map = null;
			pred = null;
			
			backtrace.clear();
			drawBT.clear();
			
			finishedSolve = false;
			finishedGen = false;
			
			if (mazeGen == "DFS") {
				dfs = new DFS();
				map = dfs.getMap();
				pred = dfs.getPred();
			} else if (mazeGen == "Prim's") {
				prim = new Prim();
				map = prim.getMap();
			}
			
			restart = false;
		}
	}

	//For animation
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (mazeGen == "DFS" && dfs != null && !dfs.isFinished()) {
			dfs.run();
		} else if (mazeGen == "Prim's" && prim != null) {
			prim.run();
		}
		
		if (prim != null && prim.isFinished() && !finishedGen) {
			dfs_Solver = new DFS_Solver(map);
			pred = dfs_Solver.getPred();
			wasHere = dfs_Solver.wasHere();
			finishedGen = true;
		}
		
		if (prim != null && finishedGen) {
			if (!dfs_Solver.isFinished()) {
				dfs_Solver.run();
			} else {
				if (!finishedSolve) {
					Cells temp = pred[dfs_Solver.endX()][dfs_Solver.endY()];
					backtrace.add(temp);
					
					while(true) {
						System.out.println(temp.x() != dfs_Solver.startX() && temp.y() != dfs_Solver.startY());
						if (temp.x() == dfs_Solver.startX() && temp.y() == dfs_Solver.startY()) {
							System.out.println("Temp: " + temp.x() + ", " + temp.y());
							System.out.println("Start: " + dfs_Solver.startX() + ", " + dfs_Solver.startY());
							break;
						}
						temp = pred[temp.x()][temp.y()];
						backtrace.add(temp);
						System.out.println();
						
						
					}
					finishedSolve = true;
				}
			}
		}
		
		if (!restart) {
			repaint();
		}
		
		/*
		if (!dfs.isFinished()) {
			dfs.run();
			repaint();
		}
		*/
	}
	
	//SETTERS
	public void switchMazeGen(String mazeGen) {
		this.mazeGen = mazeGen;
	}
	
	public void changeRestart(boolean restart) {
		this.restart = restart;
	}
	
	//METHODS
	public void reset() {
		if (timer.isRunning()) {
			timer.stop();
			restart = true;
			System.out.println("STOP");
		}
	}
	
	public void start(Graphics g) {
		if (mazeGen == "DFS") {
			runDFS(g);
		} else if (mazeGen == "Prim's") {
			runPrim(g);
		}
	}
	
	private void runDFS_Solver(Graphics g) {
		
	}
	
	private void runPrim(Graphics g) {
		for(int i = 0; i < Constants.MAP_WIDTH; i++) {
			for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
				String type = map[i][j].getName();
				
				if (type == "P") {
					g.setColor(Color.BLACK);
				} else if (type == "W") {
					g.setColor(Color.BLACK);
				} else if (type == "R") {
					g.setColor(Color.WHITE);
				}
				
				g.fillRect(Constants.SIZE*i, Constants.SIZE*j, Constants.SIZE, Constants.SIZE);
			}
		}
		
		g.setColor(Color.RED);
		for(int i = 0; i < Constants.MAP_WIDTH; i++) {
			for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
				Cells cell = map[i][j];
				if (cell.getVisit()) {
					g.fillRect(Constants.SIZE*i, Constants.SIZE*j, Constants.SIZE, Constants.SIZE);
				}
			}
		}
	}
	
	private void runDFS(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i < Constants.MAP_WIDTH; i++) {
			for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
				g.drawRect(Constants.SIZE*i, Constants.SIZE*j, Constants.SIZE, Constants.SIZE);
			}
		}
		
		g.setColor(Color.CYAN);
		for(int i = 0; i < Constants.MAP_WIDTH; i++) {
			for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
				if (map[i][j].getVisit()) {
					carvePath(g, i, j);
				}
			}
		}
	}
	
	private void carvePath(Graphics g, int i, int j) {
		int dir = map[i][j].getDirection();
		
		switch(dir) {
		case 0:
			g.fillRect(Constants.SIZE*i+1, Constants.SIZE*(j-1)+1, 4, 9);
			break;
		case 1:
			g.fillRect(Constants.SIZE*i+1, Constants.SIZE*j+1, 9, 4);
			break;
		case 2:
			g.fillRect(Constants.SIZE*i+1, Constants.SIZE*j+1, 4, 9);
			break;
		case 3:
			g.fillRect(Constants.SIZE*(i-1)+1, Constants.SIZE*j+1, 9, 4);
			break;
		}
		
		int initialDir = map[i][j].getInitialDirection();
		switch(initialDir) {
		case 0:
			g.fillRect(Constants.SIZE*i+1, Constants.SIZE*(j-1)+1, 4, 9);
			break;
		case 1:
			g.fillRect(Constants.SIZE*i+1, Constants.SIZE*j+1, 9, 4);
			break;
		case 2:
			g.fillRect(Constants.SIZE*i+1, Constants.SIZE*j+1, 4, 9);
			break;
		case 3:
			g.fillRect(Constants.SIZE*(i-1)+1, Constants.SIZE*j+1, 9, 4);
			break;
		}
	}
	
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			
		}
		
		public void mouseReleased(MouseEvent e) {
			Point p = e.getPoint();
			int mapX = (p.x)/Constants.SIZE;
			int mapY = (p.y)/Constants.SIZE;
			Cells cell = map[mapX][mapY];
			
			if (mazeGen == "Prim's") {
				System.out.println("Name: " + cell.getName() + "\tX: " + cell.x() + "\tY: " + cell.y());
				if (dfs_Solver != null && dfs_Solver.isFinished()) {
					System.out.println("Start: " + dfs_Solver.startX() + ", " + dfs_Solver.startY());
					System.out.println("End: " + dfs_Solver.endX() + ", " + dfs_Solver.endY());
					System.out.println();
					
					System.out.println("Prev: " + pred[mapX][mapY].x() + ", " + pred[mapX][mapY].y());
				}
			}
			System.out.println("------------------------");
			
			/*
			Cells prev = pred[mapX][mapY];
			System.out.println("X: " + mapX + "\tY: " + mapY);
			System.out.println("Visit: " + cell.getVisit());
			System.out.println("Dir: " + cell.getDirection());
			System.out.println("Pred: ");
			System.out.println("X: " + prev.x() + "\tY: " + prev.y());
			System.out.println("Visit: " + prev.getVisit());
			System.out.println("Dir: " + prev.getDirection());
			*/
		}
	}
}
