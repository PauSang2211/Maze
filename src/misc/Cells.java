package misc;

public class Cells {
	private Cells[] neighbors;
	private String name;
	private int x, y, dir, initialDir;
	private boolean visited, added;
	
	//CONSTRUCTOR
	public Cells(String name, int x, int y) {
		this.name = name;
		this.x = x; 
		this.y = y;

		this.visited = false;
		this.added = false;
		this.initialDir = 4;
		
		neighbors = new Cells[4];
	}
	
	//GETTERS
	public String getName() {
		return name;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public boolean getVisit() {
		return visited;
	}
	
	public Cells[] getNeighbors() {
		return neighbors;
	}
	
	public Cells getNeighbors(int i) {
		return neighbors[i];
	}
	
	public int getDirection() {
		return dir;
	}
	
	public int getInitialDirection() {
		return initialDir;
	}
	
	public boolean hasAdded() {
		return added;
	}
	
	//SETTERS
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNeighbors(int i, Cells neighbor) {
		neighbors[i] = neighbor;
	}
	
	public void setVisit(boolean visited) {
		this.visited = visited;
	}
	
	public void setDirection(int dir) {
		this.dir = dir;
	}
	
	public void setInitialDir(int initialDir) {
		this.initialDir = initialDir;
	}
	
	public void changeAdded(boolean added) {
		this.added = added;
	}
	
	
	//METHODS
	public boolean areAllNeighborsVisited() {
		for(Cells cell : neighbors) {
			if (cell != null && cell.visited == false) {
				return false;
			}
		}
		
		return true;
	}
}
