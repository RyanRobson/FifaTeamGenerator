import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class FifaGui extends JFrame implements Serializable{
	static JFrame mainFrame;
	static JPanel mainPanel;
	static JPanel playerDisplayPanel;
	static JPanel addPlayerPanel;
	
	static ArrayList<Player> playerList = new ArrayList<Player>(); 
	
	static JTextArea playerTextArea = new JTextArea();
	static JButton addPlayerButton = new JButton("Add Player");
	static JButton clearPlayerList = new JButton("Clear Players");
	static JButton savePlayerList = new JButton("Save Player List");
	static JButton loadPlayerList = new JButton("Load Player List");
	
	static JLabel noOfPlayers;
	static JLabel enterNameLabel;
	
	 static File[] filesList;
	public static void createAndShowGUI(){
		mainFrame = new JFrame("Fifa League Team Generator");
		
		playerDisplayPanel = new JPanel();
		mainFrame.setLayout(new GridLayout(0,1));
		
		addComponentsToPane();
		
		playerTextArea.setLineWrap(true);
		playerTextArea.setEditable(false);
		playerTextArea.setWrapStyleWord(true);
		mainPanel.setVisible(true);
		mainFrame.add(mainPanel);
		mainFrame.add(playerDisplayPanel); 
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	    mainFrame.pack();
	    mainFrame.setVisible(true);
	}
	
	public static void addComponentsToPane(){
		mainPanel = new JPanel();
		noOfPlayers = new JLabel();
		noOfPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
		addPlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		clearPlayerList.setAlignmentX(Component.CENTER_ALIGNMENT);
		savePlayerList.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadPlayerList.setAlignmentX(Component.CENTER_ALIGNMENT);
	

		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize( new Dimension(400,350));
		playerDisplayPanel.setLayout(new BoxLayout(playerDisplayPanel, BoxLayout.Y_AXIS));
		playerDisplayPanel.setPreferredSize( new Dimension(200,150));
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    mainPanel.add(addPlayerButton);
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    mainPanel.add(clearPlayerList);
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    mainPanel.add(savePlayerList);
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    mainPanel.add(loadPlayerList);
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    
		
		
		playerDisplayPanel.add(noOfPlayers);
		playerDisplayPanel.setOpaque(false);
		playerDisplayPanel.setVisible(true);
		playerDisplayPanel.add(playerTextArea);
	
		
		
		
		addPlayerButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				final JDialog playerDialog = new JDialog();
				final JPanel dialogPanel = new JPanel();
				enterNameLabel = new JLabel();
				enterNameLabel.setText("Enter Player Name:");
				JButton addPlayer= new JButton("Add Player");
				final JTextField playerName = new JTextField();
				playerName.setMaximumSize(new Dimension(200,40));
				dialogPanel.setPreferredSize( new Dimension(400,150));
				dialogPanel.setLayout(new BoxLayout(dialogPanel,BoxLayout.Y_AXIS));
				addPlayer.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String plName = playerName.getText();
						if (plName.length()>0){
							Player newPlayer;
							try {
								newPlayer = new Player(plName);
								playerList.add(newPlayer);
								playerTextArea.append(newPlayer.toString()+ "\n");
								//System.out.println(newPlayer.toString());
								updateGUI();
									
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
								}						
							playerDialog.dispose();
						}
				});				
				playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
				addPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
				enterNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				
				playerDialog.add(dialogPanel);
				dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				dialogPanel.add(enterNameLabel);
				dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				dialogPanel.add(playerName);
				dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				dialogPanel.add(addPlayer);
				playerDialog.pack();
				playerDialog.setVisible(true);
				
			}
			
		});
		
		clearPlayerList.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				playerList.clear();
				updateGUI();
				playerTextArea.setText("");
				try {
					Player.readTeamFile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		savePlayerList.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				final JDialog saveDialog = new JDialog();
				JPanel savePanel = new JPanel();
				final JTextField saveText = new JTextField();
				JLabel saveLabel = new JLabel();
				
				saveText.setMaximumSize(new Dimension(200,40));
				saveLabel.setText("Enter a name for the save file:");
				JButton saveButton = new JButton("Save Player List");
				savePanel.setPreferredSize( new Dimension(400,150));
				savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
				saveText.setAlignmentX(Component.CENTER_ALIGNMENT);
				saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				saveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				savePanel.add(saveLabel);
				savePanel.add(Box.createRigidArea(new Dimension(0, 10)));
				savePanel.add(saveText);
				savePanel.add(Box.createRigidArea(new Dimension(0, 10)));
				savePanel.add(saveButton);
				
				
				
				saveDialog.add(savePanel);
				saveDialog.pack();
				saveDialog.setVisible(true);
				saveButton.addActionListener(new ActionListener(){
				
					@Override
					public void actionPerformed(ActionEvent e) {
						 String saveFileName = saveText.getText();
						
						try {
							PrintWriter pw = new PrintWriter(new FileOutputStream(saveFileName + ".txt"));
							for (int i=0; i<playerList.size();i++){
								pw.println(playerList.get(i).toString());
							}
							pw.flush();
							pw.close();
							saveDialog.dispose();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
		});
	
		loadPlayerList.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				final JDialog loadDialog = new JDialog();
				JPanel loadPanel = new JPanel();
				JButton loadButton = new JButton("Load this file");
				JLabel loadLabel = new JLabel("Choose a .txt file to load");
				File curDir = new File(".");
				 filesList = curDir.listFiles();
				final JComboBox curFiles = new JComboBox(filesList);
				
				loadButton.addActionListener(new ActionListener(){

				
					public void actionPerformed(ActionEvent e) {
						
						try {
							//File fnm =  (File) curFiles.getSelectedItem();
							LoadSaveFile(curFiles.getSelectedItem().toString());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//System.out.println(fnm.toString());
						
					}
					
				});
				curFiles.setMaximumSize(new Dimension(200,40));
				loadPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				loadPanel.add(loadLabel);
				loadPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				loadPanel.add(curFiles);
				loadPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				loadPanel.add(loadButton);
				loadPanel.setPreferredSize( new Dimension(400,150));
				loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));
				loadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				curFiles.setAlignmentX(Component.CENTER_ALIGNMENT);
				loadDialog.add(loadPanel);
				loadDialog.pack();
				loadDialog.setVisible(true);
			}
			
		});
	}
	public static void updateGUI(){
		noOfPlayers.setText(String.valueOf("Number of players: " + playerList.size()));
		
	}
	 public static void main(String[] args) throws InvocationTargetException, InterruptedException {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	                updateGUI();
	                try {
						Player.readTeamFile();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
	    }
	 
	 private static void LoadSaveFile(String fileToLoad) throws FileNotFoundException{
		 Scanner sc = new Scanner(new File(fileToLoad));
		// playerList.clear();
			
			while(sc.hasNext()){
				new Player(sc.nextLine());

	 }
			updateGUI();
	 }
}
