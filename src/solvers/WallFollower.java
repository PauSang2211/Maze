package solvers;

import java.util.LinkedList;
import java.util.Queue;

import misc.Cells;
import misc.Constants;

public class WallFollower {
	private Cells[][] map, pred;
	private Queue<Cells> trail;
	private int startX, startY, endX, endY;
	private boolean finished;
	
	public WallFollower(Cells[][] map) {
		this.map = map;
		pred = new Cells[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
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
		
		while(map[endX][endY].getName() == "W" || map[endX][endY].getName() == "P") {
			endX = (int) (Math.random() * Constants.MAP_WIDTH);
			endY = (int) (Math.random() * Constants.MAP_HEIGHT);
		}
		
		this.finished = false;
	}
	
	//GETTERS
	public Cells[][] getPred() {
		return pred;
	}
	
	//METHODS
	public void run() {
		if (!trail.isEmpty()) {
			Cells nextCell;
			Cells currCell = trail.peek();
			currCell.setVisit(true);
			
			if (currCell.x() == startX && currCell.y() == startY) {
				finished = true;
				return;
			}
			
			if (!finished) {
				//NORTH
				nextCell = map[currCell.x()][currCell.y()-1];
				if (nextCell != null && nextCell.getName() == "R" && 
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = currCell;
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
				
				//EAST
				nextCell = map[currCell.x()+1][currCell.y()];
				if (nextCell != null && nextCell.getName() == "R" &&
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = currCell;
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
				
				//SOUTH
				nextCell = map[currCell.x()][currCell.y()+1];
				if (nextCell != null && nextCell.getName() == "R" && 
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = currCell;
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
				
				//WEST
				nextCell = map[currCell.x()-1][currCell.y()];
				if (nextCell != null && nextCell.getName() == "R" && 
						pred[nextCell.x()][nextCell.y()] == null) {
					pred[nextCell.x()][nextCell.y()] = currCell;
					trail.add(new Cells("", nextCell.x(), nextCell.y()));
				}
			}
			trail.remove();
		}
	}
}
