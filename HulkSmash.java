/*Jeffrey Grant Zion Eig-Tassiello
 * A class that maintains an algorithm to find the best path
 * for the Hulk to take through a city
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class HulkSmash {

	private int [][] city;
	private Stack<Sector> currentPath;
	private Sector hulkLoc;

	public HulkSmash(String fname, int dim){

		Scanner inFile = null;
		try{
			inFile = new Scanner(new File(fname));
		}catch(FileNotFoundException e){

			System.out.println("File not found");
			System.exit(-1);
		}

		city = new int[dim][dim];

		for(int row = 0; row < dim; row++){
			for(int col = 0; col < dim; col++){
				city[row][col] = inFile.nextInt();
			}
		}

		hulkLoc = new Sector(0, 0, city[0][0]);
		currentPath = new Stack<Sector>();
	}

	//Moves the Hulk along one path throughout the city until he can't move
	public void move(){
		
		boolean stopSearch = false;
		boolean goDown;

		//Moves Hulk until he hits the end of the matrix or in a spot he cannot move
		while(!stopSearch){
			goDown = canMoveDown();
			
			//If he can move Right, it will move right
			if(canMoveRight()){
				//If he can move Right and Down then the sector is pushed into the stack, but still moves right
				if(goDown)
					currentPath.push(new Sector(hulkLoc.row, hulkLoc.col, hulkLoc.total));
				hulkLoc.col++;
				hulkLoc.total += city[hulkLoc.row][hulkLoc.col];
			}

			//If the Hulk can only move down then he moves down
			else if(goDown){
				hulkLoc.row++;
				hulkLoc.total += city[hulkLoc.row][hulkLoc.col];
			}
			
			//If the Hulk cannot move then the loop and the method are exited
			else
				stopSearch = true;
		}
	}

	public boolean canMoveRight(){

		if(hulkLoc.col + 1 == city.length || city[hulkLoc.row][hulkLoc.col+1] == -1)
			return false;

		return true;
	}

	public boolean canMoveDown(){

		if(hulkLoc.row + 1 == city.length || city[hulkLoc.row+1][hulkLoc.col] == -1)
			return false;

		return true;
	}

	//Runs the Hulk through all possible paths on the grid and returns the best path, if he cannot complete the grid returns -1
	public int runHulk(){
		
		int highest = -1;
		
		//Moves the Hulk through all possible paths
		do{
			
			//Does one path
			move();

			//Checks if at the end of the grid, if he is highest total is stored
			if(hulkLoc.row == city.length-1 && hulkLoc.col == city.length-1){
				if(hulkLoc.total > highest){
					highest = hulkLoc.total;
				}
			}

			//If the Stack isn't empty, Hulk's location is set to the top of the stack, and moves down instead of right
		    if(!currentPath.isEmpty()){
		    	hulkLoc = currentPath.pop();
		    	hulkLoc.row++;
		    	hulkLoc.total += city[hulkLoc.row][hulkLoc.col];
		    }

		}while(!currentPath.isEmpty());

		return highest;
	}

	public class Sector{

		private int row;
		private int col;
		private int total;

		public Sector(int r, int c, int t){

			row = r;
			col = c;
			total = t;
		}
	}

	public static void main(String [] args){

		HulkSmash smash = HulkSmash("city.txt", 12);

		System.out.println("Minions destroyed: " + smash.runHulk());
	}
}
