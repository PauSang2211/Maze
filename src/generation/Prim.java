package generation;

import java.util.LinkedList;

import misc.Cells;
import misc.Constants;

public class Prim {
	private LinkedList<Cells> list;
	private static Cells[][] map;
	private int startX, startY;
	private boolean finished;
	
	public Prim() {
		list = new LinkedList<Cells>();
		map = new Cells[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		
		for(int i = 0; i < Constants.MAP_WIDTH; i++) {
			for(int j = 0; j < Constants.MAP_HEIGHT; j++) {
				if (i%2 == 0) {
					if (j%2 == 0) {
						map[i][j] = new Cells("P", i, j);
						
					} else {
						map[i][j] = new Cells("W", i, j);
						
					}
				} else {
					if (j%2 == 0) {
						map[i][j] = new Cells("W", i, j);
					
					} else {
						map[i][j] = new Cells("R", i, j);
						
					}
				}
				
				map[i][j].changeAdded(false);
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
		
		startX = (int) (Math.random() * Constants.MAP_WIDTH);
		startY = (int) (Math.random() * Constants.MAP_HEIGHT);
		
		while(map[startX][startY].getName() != "R") {
			startX = (int) (Math.random() * Constants.MAP_WIDTH);
			startY = (int) (Math.random() * Constants.MAP_HEIGHT);
		}
		
		map[startX][startY].setVisit(true);
		addWalls(map[startX][startY]);
		this.finished = false;
	}
	
	//GETTERS
	public Cells[][] getMap() {
		return map;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	//SETTERS
	
	
	
	//METHODS
	public void run() {
		if (!list.isEmpty()) {
			Cells wall = list.get((int) (Math.random() * list.size()));
			
			findRooms(wall);
			
			list.remove(wall);
		} else {
			finished = true;
			//System.out.println("Empty");
		}
	}
	
	private void findRooms(Cells wall) {
		Cells newRoom = null;
		int cnt = 0;
		for(Cells neighbor : wall.getNeighbors()) {
			if (neighbor != null && neighbor.getName() == "R" && neighbor.getVisit() == false) {
				newRoom = neighbor;
				cnt++;
			}
		}
		
		if (cnt == 1) {
			wall.setName("R");
			wall.setVisit(true);
			newRoom.setVisit(true);
			addWalls(newRoom);
		}
	}
	
	private void addWalls(Cells room) {
		for(Cells neighbor : room.getNeighbors()) {
			if (neighbor != null && neighbor.hasAdded() == false) {
				neighbor.changeAdded(true);
				list.add(neighbor);
			}
		}
		
	}
}
