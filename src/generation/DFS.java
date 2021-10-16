package generation;

import java.util.LinkedList;
import java.util.Queue;

import misc.Cells;
import misc.Constants;

public class DFS {
	private static Cells[][] map;
	private static Cells[][] pred;
	private Queue<Cells> q, used;
	private int startX, startY;
	private boolean finished;
	
	public DFS() {
		map = new Cells[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		for(int i = 0; i < Constants.MAP_WIDTH; i++) {
			for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
				map[i][j] = new Cells("E", i, j);
			}
		}
		
		for(int i = 0; i < Constants.MAP_WIDTH; i++) {
			for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
				Cells currCell = map[i][j];
				//North
				if (j-1 >= 0) {
					currCell.setNeighbors(0, map[i][j-1]);
				} else {
					currCell.setNeighbors(0, null);
				}
				
				//East
				if (i+1 < Constants.MAP_WIDTH) {
					currCell.setNeighbors(1, map[i+1][j]);
				} else {
					currCell.setNeighbors(1, null);
				}
				
				//South
				if (j+1 < Constants.MAP_HEIGHT) {
					currCell.setNeighbors(2, map[i][j+1]);
				} else {
					currCell.setNeighbors(2, null);
				}
				
				//West
				if (i-1 >= 0) {
					currCell.setNeighbors(3, map[i-1][j]);
				} else {
					currCell.setNeighbors(3, null);
				}
			}
		}
		
		q = new LinkedList<Cells>();
		used = new LinkedList<Cells>();
		
		startX = (int) (Math.random() * Constants.MAP_WIDTH);
		startY = (int) (Math.random() * Constants.MAP_HEIGHT);
		
		q.add(map[startX][startY]);
		
		pred = new Cells[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		
		finished = false;
	}
	
	//GETTERS
	public Cells[][] getMap() {
		return map;
	}
	
	public Cells[][] getPred() {
		return pred;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	//METHODS
	public void run() {
		if (!q.isEmpty()) {
			Cells currCell = q.peek();
			currCell.setVisit(true);
			int dir = (int) (Math.random() * 4);
			currCell.setDirection(dir);
			
			
			if (currCell.areAllNeighborsVisited()) {
				Cells temp = pred[currCell.x()][currCell.y()];
				
				while(temp.areAllNeighborsVisited()) {
					if (temp.x() == startX && temp.y() == startY) {
						finished = true;
						return;
					}
					temp = pred[temp.x()][temp.y()];
				}
				
				temp.setInitialDir(temp.getDirection());
				q.add(temp);
				currCell.setDirection(4);
				q.remove();
				return;
			}
			
			
			if (currCell.getNeighbors(dir) == null || currCell.getNeighbors(dir).getVisit() == true) {
				run();
				return;
			} else {
				Cells addedCell = currCell.getNeighbors(dir);
				q.add(addedCell);
				pred[addedCell.x()][addedCell.y()] = currCell;
			}
			
			q.remove();
			
			/*
			if (currCell.areAllNeighborsVisited()) {
				Cells temp = pred[currCell.x()][currCell.y()];
				
				while(temp.areAllNeighborsVisited()) {
					if (temp.x() == startX && temp.y() == startY) {
						finished = true;
						return;
					}
					temp = pred[temp.x()][temp.y()];
					System.out.println("Here");
				}
				
				temp.setVisit(true);
				q.add(temp);
				currCell.setDirection(4);
				System.out.println("Added from pred");
			} else if (currCell.getNeighbors(dir) != null && currCell.getNeighbors(dir).getVisit() == false) {
				Cells addedCell = currCell.getNeighbors(dir);
				
				addedCell.setVisit(true);
				q.add(addedCell);
				pred[addedCell.x()][addedCell.y()] = currCell;
				System.out.println("Added");
			} else if (currCell.getNeighbors(dir) == null || currCell.getNeighbors(dir).getVisit() == true) {
				System.out.println("Rerun");
				run();
			}
			
			if (q.isEmpty()) {
				System.out.println("Empty");
			}
			used.add(q.remove());
			*/
		} else {
			while(!used.isEmpty()) {
				Cells currCell = used.remove();
			}
			return;
		}
		
	}
}
