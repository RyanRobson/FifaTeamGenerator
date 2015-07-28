import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Player implements Serializable {
	String playerName;
	String teamChoice;
	static ArrayList<String> teamsList = new ArrayList<String>();
	public Player(String name) throws FileNotFoundException{
		playerName  =name;
		teamGeneration();
		
	}
	public  void teamGeneration() throws FileNotFoundException{

		int randTeamInt = randInt(0,teamsList.size());
		teamChoice = teamsList.get(randTeamInt);
		teamsList.remove(randTeamInt);
	}
	
	public String toString(){
		String playerInfo = null;
		playerInfo = playerName + " - " + teamChoice;
		return playerInfo;
	}
	
	 public static int randInt(int min, int max) {
	        Random rand = new Random();
	        int randomNum = rand.nextInt((max - min) + 1) + min;
	        return randomNum;
	    }
	 
	 public static void readTeamFile() throws FileNotFoundException{
		 String fnm = "teams.txt";
			Scanner sc = new Scanner(new File(fnm));
		
			while(sc.hasNext()){
				teamsList.add(sc.nextLine());
			}
	 }
}
