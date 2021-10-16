package solvers;

import java.util.LinkedList;
import java.util.Queue;

import misc.Cells;
import misc.Constants;

public class DFS_Solver {
	private Cells[][] map; 
	private static Cells[][] pred, wasHere;
	private Queue<Cells> trail;
	private int startX, startY, endX, endY;
	private final int MIN_DIST = 100;
	private boolean finished;
	
	public DFS_Solver(Cells[][] map) {
		this.map = map;
		pred = new Cells[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		wasHere = new Cells[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		trail = new LinkedList<Cells>();
		
		startX = (int) (Math.random() * Constants.MAP_WIDTH);
		startY = (int) (Math.random() * Constants.MAP_HEIGHT);
		
		while(map[startX][startY].getName() == "W" || map[startX][startY].getName() == "P") {
			startX = (int) (Math.random() * Constants.MAP_WIDTH);
			startY = (int) (Math.random() * Constants.MAP_HEIGHT);
		}
		
		trail.add(new Cells("", startX, startY));
		
		endX = (int) (Math.random() * Constants.MAP_WIDTH);
		endY = (int) (Math.random() * Constants.MAP_HEIGHT);
		
		while((map[endX][endY].getName() == "W" || map[endX][endY].getName() == "P") &&
				calcDist(startX, startY, endX, endY) < MIN_DIST) {
			endX = (int) (Math.random() * Constants.MAP_WIDTH);
			endY = (int) (Math.random() * Constants.MAP_HEIGHT);
		}
		
		this.finished = false;
	}
	
	//GETTERS
	public Cells[][] wasHere() {
		return wasHere;
	}
	
	public Cells[][] getPred() {
		return pred;
	}
	
	public int startX() {
		return startX;
	}
	
	public int startY() {
		return startY;
	}
	
	public int endX() {
		return endX;
	}
	
	public int endY() {
		return endY;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	//METHODS
	public void run() {
		if (!trail.isEmpty()) {
			Cells nextCell;
			Cells currCell = trail.peek();
			currCell.setVisit(true);
			wasHere[currCell.x()][currCell.y()] = currCell;
			
			
			if (currCell.x() == endX && currCell.y() == endY) {
				finished = true;
				return;
			}
			
			if (!finished) {
				//NORTH
				nextCell = map[currCell.x()][currCell.y()-1];
				if (nextCell != null && nextCell.getName() == "R" && 
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = new Cells("", currCell.x(), currCell.y());
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
				
				//EAST
				nextCell = map[currCell.x()+1][currCell.y()];
				if (nextCell != null && nextCell.getName() == "R" &&
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = new Cells("", currCell.x(), currCell.y());
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
				
				//SOUTH
				nextCell = map[currCell.x()][currCell.y()+1];
				if (nextCell != null && nextCell.getName() == "R" && 
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = new Cells("", currCell.x(), currCell.y());
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
				
				//WEST
				nextCell = map[currCell.x()-1][currCell.y()];
				if (nextCell != null && nextCell.getName() == "R" && 
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = new Cells("", currCell.x(), currCell.y());
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
			}
			trail.remove();
		}
	}
	
	private double calcDist(int startX, int startY, int endX, int endY) {
		double result = Math.pow(endX-startX, 2) + Math.pow(endY-startY, 2);
		result = Math.sqrt(result);
		return result;
	}
}
