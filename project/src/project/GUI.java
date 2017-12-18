package project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Highlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.Panel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JTextArea;

public class GUI implements ActionListener, ChangeListener
{
	private static GSAlgo algo;
	private static int maxNoOfParticipants;
	private static int frameNumber, frameNo, noOfDeletionsInProposerPrefList, noOfDeletionsInAcceptorPrefList;
	private static Timer timer;

	//GUI components
	private static JFrame frame;
	private static JSeparator lineSeparator;
	private static JPanel pseudoCodePanel,panel_2;
	private static JScrollPane scrollPane;
	private static JTextPane proposerPreferencesTextPane, freeProposersTextPane, acceptorPreferencesTextPane,executionTraceTextPane, pseudoCodeTextPane;
	private static JLabel freeProposersLabel, algoLabel, executionTraceLabel, menLabel, womenLabel, matchingsLabel, slowDownLabel, speedUpLabel, proposerLbl[], acceptorLbl[] ;
	private static JTextPane matchingsTextPane, txtpnAssignEach;
	private static JButton startButton, runAutomatically, runInSteps, randomInputButton, randomiseInputButton, saveToFileButton, fileInputButton, exitButton;
	private displayDataAutomatically displayObject;
	private static JLayeredPane layeredPane, layeredPane_1;

	private static boolean saved,algoExecuting;
	private static int algoSpeed;
	private static int[] pseudoLine = new int[13];
	private static JSlider slider;
	private JButton nextStep;
	private JButton previousStep;

	//Constructor - acts as View in MVC
	public GUI(GSAlgo algoObject) 
	{	
		//define each GUI component
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setForeground(UIManager.getColor("TabbedPane.foreground"));
		frame.getContentPane().setFont(new Font("Dialog", Font.PLAIN, 20));
		frame.setBounds(-7, 2, 2000, 1025);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		startButton = new JButton(">");
		startButton.setFont(new Font("Dialog", Font.BOLD, 17));
		startButton.setBounds(1376, 0, 57, 41);
		frame.getContentPane().add(startButton);
		startButton.addActionListener(this);
		startButton.setVisible(false);

		lineSeparator = new JSeparator();
		lineSeparator.setBounds(12, 65, 1888, 2);
		frame.getContentPane().add(lineSeparator);

		randomInputButton = new JButton("Generate input randomly");		
		randomInputButton.setFont(new Font("Dialog", Font.BOLD, 17));
		randomInputButton.setBounds(181, 12, 238, 41);
		frame.getContentPane().add(randomInputButton);
		randomInputButton.addActionListener(this);

		randomiseInputButton = new JButton("Randomise input");
		randomiseInputButton.setFont(new Font("Dialog", Font.BOLD, 17));
		randomiseInputButton.setBounds(431, 12, 177, 41);
		frame.getContentPane().add(randomiseInputButton);
		randomiseInputButton.addActionListener(this);
		randomiseInputButton.setEnabled(false);

		saveToFileButton = new JButton("Save to file");
		saveToFileButton.setFont(new Font("Dialog", Font.BOLD, 17));
		saveToFileButton.setBounds(620, 12, 200, 41);
		frame.getContentPane().add(saveToFileButton);
		saveToFileButton.setEnabled(false);
		saveToFileButton.addActionListener(this);

		freeProposersLabel = new JLabel("FREE MEN");
		freeProposersLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		freeProposersLabel.setBounds(123, 564, 192, 16);
		frame.getContentPane().add(freeProposersLabel);

		algoLabel = new JLabel("EXTENDED GALE-SHAPLEY ALGORITHM");
		algoLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		algoLabel.setBounds(52, 99, 404, 16);
		frame.getContentPane().add(algoLabel);

		menLabel = new JLabel("MEN");
		menLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		menLabel.setBounds(688, 99, 115, 16);
		frame.getContentPane().add(menLabel);

		womenLabel = new JLabel("WOMEN");
		womenLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		womenLabel.setBounds(1131, 99, 151, 16);
		frame.getContentPane().add(womenLabel);

		executionTraceLabel = new JLabel("EXECUTION TRACE");
		executionTraceLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		executionTraceLabel.setBounds(1571, 93, 192, 16);
		frame.getContentPane().add(executionTraceLabel);

		freeProposersTextPane = new JTextPane();
		freeProposersTextPane.setFont(new Font("Courier New", Font.PLAIN, 25));
		freeProposersTextPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		freeProposersTextPane.setBackground(UIManager.getColor("TabbedPane.selected"));
		freeProposersTextPane.setBounds(12, 597, 461, 119);
		frame.getContentPane().add(freeProposersTextPane);
		freeProposersTextPane.setEditable(false);

		matchingsLabel = new JLabel("MATCHINGS");
		matchingsLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		matchingsLabel.setBounds(162, 747, 126, 16);
		frame.getContentPane().add(matchingsLabel);

		matchingsTextPane = new JTextPane();
		matchingsTextPane.setFont(new Font("Courier New", Font.PLAIN, 25));
		matchingsTextPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		matchingsTextPane.setBackground(UIManager.getColor("TabbedPane.selected"));
		matchingsTextPane.setBounds(12, 785, 1374, 109);
		frame.getContentPane().add(matchingsTextPane);
		matchingsTextPane.setEditable(false);

		fileInputButton = new JButton("Input from file");
		fileInputButton.setActionCommand("Input from file");
		fileInputButton.setFont(new Font("Dialog", Font.BOLD, 17));
		fileInputButton.setBounds(12, 12, 157, 41);
		frame.getContentPane().add(fileInputButton);
		fileInputButton.addActionListener(this);


		txtpnAssignEach = new JTextPane();		
		txtpnAssignEach.setBounds(0, 0, 495, 406);
		txtpnAssignEach.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtpnAssignEach.setBackground(UIManager.getColor("TabbedPane.selected"));
		txtpnAssignEach.setFont(new Font("Courier New", Font.PLAIN, 20));
		txtpnAssignEach.setText("\r\nassign each proposer to be free ;\r\nfor: (each free proposer m)\r\n{\r\n  w = first woman on m\u2019s list ;\r\n  m proposes to w ;\r\n  If(some man m' is already engaged to w)\r\n      assign m' to be free ;\r\n  assign m and w to be engaged ;\r\n  for:(each successor n of m on w\u2019s list)\r\n  {\r\n     delete (n , w) ;\r\n  }\r\n}");
		txtpnAssignEach.setOpaque(false);
		txtpnAssignEach.setVisible(true);
		txtpnAssignEach.setEditable(false);

		pseudoCodePanel = new JPanel();
		pseudoCodePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		pseudoCodePanel.setBounds(18, 133, 458, 391);
		frame.getContentPane().add(pseudoCodePanel);
		pseudoCodePanel.setLayout(null);

		pseudoCodeTextPane = new JTextPane();
		pseudoCodeTextPane.setBounds(0, 0, 458, 391);
		pseudoCodePanel.add(pseudoCodeTextPane);
		pseudoCodeTextPane.setText("\r\nassign each proposer to be free ;\r\nfor: (each free proposer m)\r\n{\r\n  w = first woman on m\u2019s list ;\r\n  m proposes to w ;\r\n  If(some man m' is already engaged to w)\r\n      assign m' to be free ;\r\n  assign m and w to be engaged ;\r\n  for:(each successor n of m on w\u2019s list)\r\n  {\r\n     delete (n , w) ;\r\n  }\r\n}\r\n");
		pseudoCodeTextPane.setFont(new Font("Courier New", Font.PLAIN, 19));
		pseudoCodeTextPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frame.getContentPane().add(pseudoCodePanel);
		pseudoCodeTextPane.setEditable(false);

		JPanel tracePanel = new JPanel();
		tracePanel.setBounds(1409, 125, 491, 800);
		frame.getContentPane().add(tracePanel);
		tracePanel.setLayout(null);

		executionTraceTextPane = new JTextPane();
		executionTraceTextPane.setFont(new Font("Lucida Sans", Font.PLAIN, 21));
		executionTraceTextPane.setBounds(0, 0, 472, 610);
		executionTraceTextPane.setEditable(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 491, 800);
		scrollPane.setViewportView(executionTraceTextPane);
		tracePanel.add(scrollPane);

		slider = new JSlider(JSlider.HORIZONTAL, 0,10,5);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 16));
		slider.setBounds(909, 0, 436, 38);
		frame.getContentPane().add(slider);
		slider.addChangeListener(this);
		slider.setVisible(false);

		speedUpLabel = new JLabel("Speed up");
		speedUpLabel.setBounds(1277, 43, 86, 20);
		frame.getContentPane().add(speedUpLabel);
		speedUpLabel.setVisible(false);

		slowDownLabel = new JLabel("Slow down");
		slowDownLabel.setBounds(909, 43, 86, 20);
		frame.getContentPane().add(slowDownLabel);
		slowDownLabel.setVisible(false);

		runAutomatically = new JButton("Run automatically");
		runAutomatically.setFont(new Font("Dialog", Font.BOLD, 17));
		runAutomatically.setBounds(940, 8, 200, 41);
		frame.getContentPane().add(runAutomatically);
		runAutomatically.addActionListener(this);
		runAutomatically.setEnabled(false);
		runAutomatically.setVisible(true);

		runInSteps = new JButton("Run in steps");
		runInSteps.setFont(new Font("Dialog", Font.BOLD, 17));
		runInSteps.setBounds(1167, 8, 178, 41);
		frame.getContentPane().add(runInSteps);
		runInSteps.addActionListener(this);
		runInSteps.setEnabled(false);
		runInSteps.setVisible(true);

		exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Dialog", Font.BOLD, 17));
		exitButton.setBounds(1713, 14, 144, 37);
		frame.getContentPane().add(exitButton);
		exitButton.addActionListener(this);
		exitButton.setEnabled(true);

		nextStep = new JButton("Next Step");
		nextStep.setFont(new Font("Dialog", Font.BOLD, 17));
		nextStep.setBounds(1167, 0, 178, 49);
		frame.getContentPane().add(nextStep);
		nextStep.setEnabled(false);
		nextStep.addActionListener(this);
		nextStep.setVisible(false);

		previousStep = new JButton("Previous step");
		previousStep.setFont(new Font("Dialog", Font.BOLD, 17));
		previousStep.setBounds(940, 0, 192, 48);
		frame.getContentPane().add(previousStep);
		previousStep.setEnabled(false);
		previousStep.addActionListener(this);
		previousStep.setVisible(false);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(501, 131, 451, 614);
		frame.getContentPane().add(layeredPane);

		layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(967, 131, 425, 614);
		frame.getContentPane().add(layeredPane_1);

		proposerPreferencesTextPane = new JTextPane();
		proposerPreferencesTextPane.setBounds(0, 0, 451, 614);
		proposerPreferencesTextPane.setForeground(Color.BLACK);
		proposerPreferencesTextPane.setBackground(Color.WHITE);
		proposerPreferencesTextPane.setFont(new Font("Courier New", Font.BOLD, 26));
		proposerPreferencesTextPane.setEditable(false);
		proposerPreferencesTextPane.setBorder(BorderFactory.createLineBorder(Color.black));
		layeredPane.add(proposerPreferencesTextPane, new Integer(1));

		acceptorPreferencesTextPane = new JTextPane();
		acceptorPreferencesTextPane.setBounds(0,0, 425, 614);
		acceptorPreferencesTextPane.setForeground(Color.BLACK);
		acceptorPreferencesTextPane.setBackground(Color.WHITE);
		acceptorPreferencesTextPane.setFont(new Font("Courier New", Font.BOLD, 26));
		acceptorPreferencesTextPane.setEditable(false);
		acceptorPreferencesTextPane.setBorder(BorderFactory.createLineBorder(Color.black));
		layeredPane_1.add(acceptorPreferencesTextPane, new Integer(1));

		//Initialize timer and other variables 
		timer = new Timer();	
		maxNoOfParticipants = 10;
		noOfDeletionsInProposerPrefList = 0;
		noOfDeletionsInAcceptorPrefList = 0;
		saved = false;
		algoExecuting = false;
		frameNumber = 0;
		frameNo = -1;
		algoSpeed = 5;
		pseudoLine = new int[]{35,30,31,20,42,30,33,46,25,2};

		algo = algoObject;
		frame.setVisible(true);
	}

	//Method to handle user interactions with GUI - Controller in MVC
	public void actionPerformed(ActionEvent actionEvent)
	{
		//To take input from file
		if(actionEvent.getSource() == fileInputButton)
		{			
			String proposerInfo = "", acceptorInfo = "", freeProposers = "";

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setPreferredSize(new Dimension(800,600));

			//Allow only text files for selection
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			fileChooser.setFileFilter(filter);

			if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{				
				//Get the file
				java.io.File file = fileChooser.getSelectedFile();

				try 
				{
					BufferedReader br = new BufferedReader(new FileReader(file));

					if (!(br.readLine().isEmpty()))
						algo.takeInputFromFile(new FileReader(file));
					else 
						throw new Exception();
				} 
				catch (Exception e) 
				{
					//Exception if selected file is empty
					JOptionPane.showMessageDialog(null, "Blank file! Choose another file!", "Input error", JOptionPane.ERROR_MESSAGE);		
					//Flush all values previously stored
					new GSAlgo();

					return;
				}

				//If input is consistent, load it into data structures to display to users
				if(algo.consistentInputCheck())
				{
					//Take data to be displayed for proposers
					for(int i = 0 ; i < algo.proposerList.size() ; i++)
					{
						proposerInfo = proposerInfo + algo.proposerList.get(i).getName() + " : ";

						for(int j = 0 ; j < algo.proposerList.get(i).prefList.size() ; j++)
							proposerInfo = proposerInfo + algo.proposerList.get(i).prefList.get(j) + " ";

						proposerInfo = proposerInfo + "\n\n";
					}

					//Take data to be displayed for acceptors
					for(int i = 0 ; i < algo.acceptorList.size() ; i++)
					{
						acceptorInfo = acceptorInfo + algo.acceptorList.get(i).getName() + " : ";

						for(int j = 0 ; j < algo.acceptorList.get(i).prefList.size() ; j++)
							acceptorInfo = acceptorInfo + algo.acceptorList.get(i).prefList.get(j) + " ";

						acceptorInfo = acceptorInfo + "\n\n";
					}

					//Take data to display free proposers
					for(int k = 0 ; k < algo.freeProposers.size() ; k++)
						freeProposers  = freeProposers + algo.freeProposers.get(k).getName() + "  ";
				}

				//Display error message if input is inconsistent
				if(!algo.consistentInputCheck())
				{	
					JOptionPane.showMessageDialog(null, "Inconsistent input! Input again or choose another file!", "Input error", JOptionPane.ERROR_MESSAGE);		
					//Flush all values previously stored
					new GSAlgo();

					return;
				}
				//Otherwise display on GUI
				else
				{					
					fileInputButton.setEnabled(false);
					randomInputButton.setEnabled(false);
					randomiseInputButton.setEnabled(true);	
					runAutomatically.setEnabled(true);
					runInSteps.setEnabled(true);

					proposerPreferencesTextPane.setText(proposerInfo);
					acceptorPreferencesTextPane.setText(acceptorInfo);
					freeProposersTextPane.setText(freeProposers);

				}
			}
		}

		//To generate input randomly
		else if(actionEvent.getSource() == randomInputButton)
		{				
			String proposerInfo = "", acceptorInfo = "", freeProposers = "";

			int noOfProposers = 0, noOfacceptors = 0;
			JOptionPane pane1 = new JOptionPane();
			pane1.setPreferredSize(new Dimension(480, pane1.getPreferredSize().height));
			JOptionPane pane2 = new JOptionPane();
			JDialog dialog = pane1.createDialog(null, "");

			try
			{
				do
				{
					//Input the number of proposers and acceptors
					noOfProposers = Integer.parseInt(pane1.showInputDialog(null, "Enter number of men")); 
					noOfacceptors = Integer.parseInt(pane2.showInputDialog(null, "Enter number of women")); 

					if(!((noOfProposers <= maxNoOfParticipants) && (noOfacceptors <= maxNoOfParticipants)))
						JOptionPane.showMessageDialog(null, "Number of proposers or acceptors cannot be more than 10!", "Improper input", JOptionPane.ERROR_MESSAGE);

					if(!((noOfProposers > 0) && (noOfacceptors > 0)))
						JOptionPane.showMessageDialog(null, "Number of proposers or acceptors should be greater than 0!", "Improper input", JOptionPane.ERROR_MESSAGE);

				}while( (!((noOfProposers <= maxNoOfParticipants) && (noOfacceptors <= maxNoOfParticipants))) || (!((noOfProposers > 0) && (noOfacceptors > 0))));
			}
			catch(Exception e)
			{
				//Catching invalid inputs
				JOptionPane.showMessageDialog(null, "Enter a number between 1 and 10", "Improper input", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try 
			{
				algo.randomInputGeneration(noOfProposers, noOfacceptors);
			} catch (Exception e) {	}

			//Take data to be displayed for proposers
			for(int i = 0 ; i < algo.proposerList.size() ; i++)
			{
				proposerInfo = proposerInfo + algo.proposerList.get(i).getName() + " : ";

				for(int j = 0 ; j < algo.proposerList.get(i).prefList.size() ; j++)
					proposerInfo = proposerInfo + algo.proposerList.get(i).prefList.get(j) + " ";

				proposerInfo = proposerInfo + "\n\n";
			}

			//Take data to be displayed for acceptors
			for(int i = 0 ; i < algo.acceptorList.size() ; i++) 
			{
				acceptorInfo = acceptorInfo + algo.acceptorList.get(i).getName() + " : ";

				for(int j = 0 ; j < algo.acceptorList.get(i).prefList.size() ; j++)
					acceptorInfo = acceptorInfo + algo.acceptorList.get(i).prefList.get(j) + " ";

				acceptorInfo = acceptorInfo + "\n\n";
			}

			//Take data to display for free proposers
			for(int k = 0 ; k < algo.freeProposers.size() ; k++)
				freeProposers  = freeProposers + algo.freeProposers.get(k).getName() + "  ";	

			proposerPreferencesTextPane.setText(proposerInfo);
			acceptorPreferencesTextPane.setText(acceptorInfo);
			freeProposersTextPane.setText(freeProposers);

			fileInputButton.setEnabled(false);
			randomInputButton.setEnabled(false);
			randomiseInputButton.setEnabled(true);
			runAutomatically.setEnabled(true);
			runInSteps.setEnabled(true);
		}

		//To randomize the current input
		else if(actionEvent.getSource() == randomiseInputButton)
		{
			String proposerInfo = "", freeProposers = "";

			algo.randomizeCurrentInput();

			//Take data to be displayed for proposers
			for(int i = 0 ; i < algo.proposerList.size() ; i++)
			{
				proposerInfo = proposerInfo + algo.proposerList.get(i).getName() + " : ";

				for(int j = 0 ; j < algo.proposerList.get(i).prefList.size() ; j++)
					proposerInfo = proposerInfo + algo.proposerList.get(i).prefList.get(j) + " ";

				proposerInfo = proposerInfo + "\n\n";
			}

			//Take data to display for free proposers
			for(int k = 0 ; k < algo.freeProposers.size() ; k++)
				freeProposers  = freeProposers + algo.freeProposers.get(k).getName() + " ";

			proposerPreferencesTextPane.setText(proposerInfo);
			freeProposersTextPane.setText(freeProposers);
		}

		//To start the algorithm execution
		else if(actionEvent.getSource() == startButton && startButton.getText().toString().equals(">") && !algoExecuting)
		{
			startButton.setText("| |");
			algoExecuting = true;
			slider.setEnabled(true);
			fileInputButton.setEnabled(false);
			randomInputButton.setEnabled(false);
			randomiseInputButton.setEnabled(false);

			Map<String, String> matches = algo.doMatching();

			//Preparing ranking list for proposers
			for(proposer eachProposer : algo.proposerList)
				eachProposer.prepareRankingList();

			//Displaying on GUI	if matchings obtained are stable
			try {
				if(algo.isStable())
				{
					if(slider.getValue() == 10)   
						algoSpeed = 250;   //Fastest algorithm execution speed
					else
						algoSpeed = ( 10 - slider.getValue()) * 500;

					timer.schedule(displayObject = new displayDataAutomatically(), 0, algoSpeed);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Unstable matchings generated!", "Stability check", JOptionPane.ERROR_MESSAGE);

					proposerPreferencesTextPane.setText("");
					acceptorPreferencesTextPane.setText("");
					freeProposersTextPane.setText("");
					matchingsTextPane.setText("");				
					executionTraceTextPane.setText("");
				}
			} catch (Exception e) {}	

		}

		//To resume algorithm execution
		else if(actionEvent.getSource() == startButton && startButton.getText().equalsIgnoreCase("| |"))
		{
			startButton.setText(">");
			timer.cancel();
			timer = new Timer();
			try
			{
				timer.schedule( displayObject = new displayDataAutomatically(), 0, 0);
			}catch(Exception e)
			{}
		}

		//To pause algorithm execution
		else if(actionEvent.getSource() == startButton && startButton.getText().equalsIgnoreCase(">") && algoExecuting)
		{
			startButton.setText("| |"); 
			timer.cancel();
			timer = new Timer();
			timer.schedule( displayObject = new displayDataAutomatically(), 0, algoSpeed);
		}

		//Saving algorithm execution data generated
		else if(actionEvent.getSource() == saveToFileButton)
		{
			try {
				saveDataToFile();
			} catch (Exception e) {}
		}

		//To exit the application
		else if(actionEvent.getSource() == exitButton)
		{
			//If data hasn't been saved, ask before exiting
			if(!saved)
			{
				int reply = JOptionPane.showConfirmDialog(null, "Exit without saving?", "EXIT", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.NO_OPTION) 
				{
					try {
						saveDataToFile();
					} catch (Exception e) {}
				} 
			}
			System.exit(0);
		}

		//Setting buttons when user wants to run automatically
		else if(actionEvent.getSource() == runAutomatically)
		{
			randomiseInputButton.setEnabled(false);
			runAutomatically.setVisible(false);
			runInSteps.setVisible(false);
			slider.setVisible(true);
			slider.setEnabled(true);
			startButton.setVisible(true);
			startButton.setEnabled(true);
			slowDownLabel.setVisible(true);
			speedUpLabel.setVisible(true);
		}

		//When user wants to run in steps
		else if(actionEvent.getSource() == runInSteps)
		{		
			//Run the algorithm
			Map<String, String> matches = algo.doMatching();
			System.out.println(" global frame incrementer = " + algo.globalFrameIncrementer);

			//Preparing ranking list for proposers
			for(proposer eachProposer : algo.proposerList)
				eachProposer.prepareRankingList();

			try {
				//Set buttons only if generated matchings are stable
				if(algo.isStable())
				{
					randomiseInputButton.setEnabled(false);
					runAutomatically.setVisible(false);
					runInSteps.setVisible(false);
					nextStep.setEnabled(true);
					nextStep.setVisible(true);
					previousStep.setEnabled(false);
					previousStep.setVisible(true);
				}
				//Show error message if matchings are not stable
				else
				{
					JOptionPane.showMessageDialog(null, "Unstable matchings generated!", "Stability check", JOptionPane.ERROR_MESSAGE);

					proposerPreferencesTextPane.setText("");
					acceptorPreferencesTextPane.setText("");
					freeProposersTextPane.setText("");
					matchingsTextPane.setText("");				
					executionTraceTextPane.setText("");
				}
			} catch (Exception e) {}
		}

		//Display next frame when user clicks on 'Next Step' button
		else if(actionEvent.getSource() == nextStep)
		{
			previousStep.setEnabled(true);	
			if(frameNo == (algo.globalFrameIncrementer - 1))
			{
				nextStep.setEnabled(false);
				freeProposersTextPane.setText("");			
				saveToFileButton.setEnabled(true);
			}
			else
			{
				frameNo++;
				display(frameNo);
			}	
		}

		//Display previous frame when user clicks on 'Previous Step' button
		else if(actionEvent.getSource() == previousStep)
		{
			nextStep.setEnabled(true);
			if(frameNo == 0)
				previousStep.setEnabled(false);
			else
			{
				frameNo--;
				display(frameNo);
			}
		}
	}	

	public void display(int frameNo)
	{
		int start = 0, end = 0, startPos = 0, startPos1 = 0;
		DefaultHighlighter.DefaultHighlightPainter paintGray = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
		DefaultHighlighter.DefaultHighlightPainter highlightYellow = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		Color[] colors = { 
				new Color(0xb1, 0xd1, 0xfc),  //  powder blue 
				new Color(0x96, 0xae, 0x8d),  //  greenish grey
				new Color(0xdf, 0xc5, 0xfe),  //  light lavender
				new Color(0x7e, 0xf4, 0xcc),  //  light turquoise
				new Color(0xa2, 0xbf, 0xfe),  //  pastel blue  
				new Color(0x96, 0xf9, 0x7b),  //  light green
				new Color(0x64, 0x88, 0xea),  //  soft blue 
				new Color(0x95, 0xd0, 0xfc),  //  light blue  
				new Color(0x00, 0xff, 0xff),  //  cyan
				new Color(0x04, 0xd8, 0xb2),  //  aquamarine 
				new Color(0x8f, 0xff, 0x9f),  //  mint green 
		};

		//Remove all previous labels
		removeAllLabels(frameNo);

		//Prepare proposers and acceptor lists
		String proposerInfo = "", acceptorInfo = "", freeProposers = "", matchings = "", trace = "";

		try
		{
			//Display proposer preference lists
			for(int i = 0; i < GSAlgo.listOfProposers.size() ; i++)
			{
				proposerInfo = proposerInfo + algo.proposerList.get(i).getName() + " : ";
				for(int j = 0; j < GSAlgo.proposerList.get(i).getPrefList().size() ; j++)
					proposerInfo = proposerInfo + algo.proposerList.get(i).getPrefList().get(j) + " ";
				proposerInfo = proposerInfo + "\n\n";
			}

			//Display acceptor preference lists
			for(int i = 0; i < GSAlgo.listOfAcceptors.size() ; i++)
			{
				acceptorInfo = acceptorInfo + algo.acceptorList.get(i).getName() + " : ";
				for(int j = 0; j < GSAlgo.acceptorList.get(i).getPrefList().size() ; j++)
					acceptorInfo = acceptorInfo + algo.acceptorList.get(i).getPrefList().get(j) + " ";
				acceptorInfo = acceptorInfo + "\n\n";
			}

			freeProposers  = algo.frames.get(frameNo).freeProposers;
			matchings = algo.frames.get(frameNo).matchings;

			for(int i = 0; i <= frameNo ; i++)
				trace = trace + algo.frames.get(i).executionTrace + "\n";

			///////////////////////////////////////////////////////////////////////////////////			
			//Pseudocode highlighting

			//First remove all other highlights
			pseudoCodeTextPane.getHighlighter().removeAllHighlights();

			//Calculate position and then highlight
			for(int i = 0 ; i < algo.frames.get(frameNo).pseudoCodeLineNo - 1 ; i++)
				start = start + pseudoLine[i];

			end = start + pseudoLine[algo.frames.get(frameNo).pseudoCodeLineNo - 1];
			try {
				pseudoCodeTextPane.getHighlighter().addHighlight(start, end, paintGray);
				start = end;
			} catch (Exception e) {}

			//Displaying preference lists
			proposerPreferencesTextPane.setText(proposerInfo);
			acceptorPreferencesTextPane.setText(acceptorInfo);
			freeProposersTextPane.setText(freeProposers);

			matchingsTextPane.setText(matchings);
			executionTraceTextPane.setText(trace);

			//////////////////////////////////////////////////////////////////////////////////////
			//Proposer/acceptor lists highlighting

			//Calculate position to highlight proposer preference list 
			for(int i = 0 ; i < algo.proposerList.size(); i++)
			{
				if(algo.proposerList.get(i).getName().equalsIgnoreCase(algo.frames.get(frameNo).proposerUnderConsideration))
				{
					int pos = i;
					for(int j = 0; j < pos ; j++)
						startPos = startPos + algo.proposerList.get(j).getName().length() + 3 + (2 * algo.proposerList.get(j).getPrefList().size() + 2);
				}
			}
			proposerPreferencesTextPane.getHighlighter().removeAllHighlights();
			proposerPreferencesTextPane.getHighlighter().addHighlight(startPos, startPos + algo.frames.get(frameNo).proposerUnderConsideration.length(), highlightYellow);

			//Calculate position to highlight receiver preference list
			for(int i = 0 ; i < algo.acceptorList.size(); i++)
			{
				if(algo.acceptorList.get(i).getName().equalsIgnoreCase(algo.frames.get(frameNo).acceptorUnderConsideration))
				{
					int pos = i;
					for(int j = 0; j < pos ; j++)
					{
						if(algo.acceptorList.get(j).getPrefList().contains("10"))
							startPos1 = startPos1 + 1 + algo.acceptorList.get(j).getName().length() + 3 + (2 * algo.acceptorList.get(j).getPrefList().size() + 2);	

						else
							startPos1 = startPos1 + algo.acceptorList.get(j).getName().length() + 3 + (2 * algo.acceptorList.get(j).getPrefList().size() + 2);	
					}
				}
			}
			acceptorPreferencesTextPane.getHighlighter().removeAllHighlights();
			acceptorPreferencesTextPane.getHighlighter().addHighlight(startPos1, startPos1 + algo.frames.get(frameNo).acceptorUnderConsideration.length(), highlightYellow);

			///////////////////////////////////////////////////////////////////////////////////////////
			//Highlighting matches

			for(Map.Entry<String, String> entry: algo.frames.get(frameNo).matchesToHighlight.entrySet()) 
			{
				DefaultHighlighter.DefaultHighlightPainter highlightColor = new DefaultHighlighter.DefaultHighlightPainter(colors[(entry.hashCode() % colors.length)]);
				proposer proposerInMatch = null;
				acceptor acceptorInMatch = null;

				//Calculate position of matched proposer to highlight
				int proposerPos = 0;
				for(int i = 0 ; i < algo.proposerList.size(); i++)
				{
					if(algo.proposerList.get(i).getName().equalsIgnoreCase(entry.getValue()))
					{
						int proposerLocation = i;
						proposerInMatch = algo.proposerList.get(i);
						for(int j = 0; j < proposerLocation ; j++)
							proposerPos = proposerPos + algo.proposerList.get(j).getName().length() + 3 + (2 * algo.proposerList.get(j).getPrefList().size() + 2);
					}
				}
				proposerPos = proposerPos + entry.getValue().length() + 3;

				for(int k = 0; k < proposerInMatch.getPrefList().size(); k++)
				{
					if((proposerInMatch.getPrefList().get(k)).equalsIgnoreCase(entry.getKey()))
						proposerPreferencesTextPane.getHighlighter().addHighlight(proposerPos, proposerPos + (proposerInMatch.getPrefList().get(k)).length(), highlightColor);
					else
						proposerPos = proposerPos + 1 + (proposerInMatch.getPrefList().get(k)).length();
				}

				proposerPreferencesTextPane.getHighlighter().addHighlight(startPos, startPos + algo.frames.get(frameNo).proposerUnderConsideration.length(), highlightYellow);


				//Calculate position of matched acceptor to highlight
				int acceptorPos = 0;
				for(int i = 0 ; i < algo.acceptorList.size(); i++)
				{
					if(algo.acceptorList.get(i).getName().equalsIgnoreCase(entry.getKey()))
					{
						int acceptorLocation = i;
						acceptorInMatch = algo.acceptorList.get(i);
						for(int j = 0; j < acceptorLocation ; j++)
						{
							if(algo.acceptorList.get(j).getPrefList().contains("10"))
								acceptorPos = acceptorPos + 1 + algo.acceptorList.get(j).getName().length() + 3 + (2 * algo.acceptorList.get(j).getPrefList().size() + 2);	
							else
								acceptorPos = acceptorPos + algo.acceptorList.get(j).getName().length() + 3 + (2 * algo.acceptorList.get(j).getPrefList().size() + 2);	
						}
					}
				}
				acceptorPos = acceptorPos + entry.getKey().length() + 3;

				for(int k = 0; k < acceptorInMatch.getPrefList().size(); k++)
				{
					if((acceptorInMatch.getPrefList().get(k)).equalsIgnoreCase(entry.getValue()))
						acceptorPreferencesTextPane.getHighlighter().addHighlight(acceptorPos, acceptorPos + (acceptorInMatch.getPrefList().get(k)).length(), highlightColor);
					else
						acceptorPos = acceptorPos + 1 + (acceptorInMatch.getPrefList().get(k)).length();
				}

			}

			//Indicate deletions from preference lists on GUI
			indicateDeletionsFromProposerPrefList(frameNo);
			indicateDeletionsFromAcceptorPrefList(frameNo);

		}catch(Exception e){}
	}

	//Function to save algorithm data generated into a file
	public void saveDataToFile() throws Exception
	{
		slider.setEnabled(false);
		startButton.setEnabled(false);
		nextStep.setEnabled(false);
		previousStep.setEnabled(false);

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("Output.txt"));
		} catch (IOException e) {}

		//Display checkboxes to allow users to select what to save
		JCheckBox saveInstance = new JCheckBox("Save men and women preferences");
		JCheckBox saveMatchings = new JCheckBox("Save matchings");
		JCheckBox saveTrace = new JCheckBox("Save the execution trace");

		String msg = "What would you like to save?"; 

		Object[] msgContent = {msg, saveInstance, saveMatchings, saveTrace}; 

		JOptionPane.showMessageDialog (null, msgContent, "Save to file", JOptionPane.QUESTION_MESSAGE); 

		//Call respective functions according to the checkboxes selected by user
		if(saveInstance.isSelected())
		{
			saveInstances(bw);
			saved = true;
		}

		if(saveMatchings.isSelected())
		{
			saveMatches(bw);
			saved = true;
		}
		if(saveTrace.isSelected())
		{
			saveExecutionTrace(bw);
			saved = true;
		}

		try {
			bw.close();
		} catch (IOException e) {}
		bw.close();
		System.exit(0);
	}

	//Method that gets called when position of slider's knob changes
	public void stateChanged(ChangeEvent e) 
	{
		JSlider source = (JSlider)e.getSource();

		if (!source.getValueIsAdjusting() && startButton.getText().equalsIgnoreCase("| |")) 
		{
			if((int)source.getValue() == 10)   
				algoSpeed = 250;   //fastest algorithm execution speed
			else
				algoSpeed = ( 10 - (int)source.getValue()) * 500;

			timer.cancel();
			timer = new Timer();
			timer.schedule( displayObject = new displayDataAutomatically(), 0, algoSpeed);

		}    

		//Detect changes to slider when program is paused
		else if(startButton.getText().equalsIgnoreCase(">") && algoExecuting)  
		{
			if((int)source.getValue() == 10)   
				algoSpeed = 250;    //fastest algorithm execution speed
			else
				algoSpeed = ( 10 - (int)source.getValue()) * 500;
		}
	}	

	//Method called when user wants to run algorithm automatically
	class displayDataAutomatically extends TimerTask
	{
		public void run()
		{
			try{
				display(frameNumber);
				JScrollBar vertical = scrollPane.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				frameNumber++;

			}catch(Exception e)
			{
				freeProposersTextPane.setText("");			
				saveToFileButton.setEnabled(true);	
				slider.setEnabled(false);
				startButton.setEnabled(false);
				timer.cancel();
			}
		}
	}

	//Method to remove all previous labels before the new ones are displayed
	public void removeAllLabels(int frameNo)
	{
		if(frameNo > 0)
		{
			//First remove all existing proposer JLabels
			if(noOfDeletionsInProposerPrefList > 0)
				for(int i = 0; i < noOfDeletionsInProposerPrefList; i++)
				{
					Container parent = proposerLbl[i].getParent();
					parent.remove(proposerLbl[i]);
					parent.validate();
					parent.repaint();
				}

			//Remove all existing acceptor JLabels
			if(noOfDeletionsInAcceptorPrefList > 0)
				for(int i = 0; i < noOfDeletionsInAcceptorPrefList; i++)
				{
					Container parent = acceptorLbl[i].getParent();
					parent.remove(acceptorLbl[i]);
					parent.validate();
					parent.repaint();
				}
		}
		else return;
	}

	//Method to place JLabels on GUI to indicate deletions from proposer preference lists
	public void indicateDeletionsFromProposerPrefList(int frameNo)
	{		
		//Create  new labels
		proposerLbl = new JLabel[100];
		int lblNo = 0;

		//Find position to place them
		for(int i = 0; i < GSAlgo.proposerList.size() ; i++)
		{
			for(int j = 0; j < GSAlgo.proposerList.get(i).getPrefList().size() ; j++)
			{		
				int xCordinate, yCordinate;

				if(GSAlgo.proposerList.get(i).getName().equalsIgnoreCase("10"))
					xCordinate = 78 + (32 * j);
				else
					xCordinate = 62 + (32 * j);

				if((algo.frames.get(frameNo).proposerDeletionsMatrix[i][j]).equalsIgnoreCase("X"))
				{
					//Calculate position		
					yCordinate = (60 * i) - 5;

					//Place label on calculated position
					proposerLbl[lblNo] = new JLabel("/");
					proposerLbl[lblNo].setForeground(Color.RED);
					proposerLbl[lblNo].setFont(new Font("Courier New", Font.BOLD, 26));
					proposerLbl[lblNo].setBackground(Color.WHITE);
					proposerLbl[lblNo].setBounds(xCordinate,yCordinate, 16, 40);
					proposerLbl[lblNo].setOpaque(false);
					layeredPane.add(proposerLbl[lblNo],new Integer(2));

					lblNo++;					
				}
			}
		}
		noOfDeletionsInProposerPrefList = lblNo;
	}

	//Method to place JLabels on GUI to indicate deletions from acceptor preference lists
	public void indicateDeletionsFromAcceptorPrefList(int frameNo)
	{	
		//Create  new labels
		acceptorLbl = new JLabel[100];
		int lblNo = 0;

		//Find position to place them
		for(int i = 0; i < GSAlgo.acceptorList.size() ; i++)
		{
			for(int j = 0; j < GSAlgo.acceptorList.get(i).getPrefList().size() ; j++)
			{
				int xCordinate, yCordinate;

				if((algo.frames.get(frameNo).acceptorDeletionsMatrix[i][j]).equalsIgnoreCase("X"))
				{
					//Calculate position	
					if(GSAlgo.acceptorList.get(i).getPrefList().contains("10") && (GSAlgo.acceptorList.get(i).getPrefList().indexOf("10") <= j))
						xCordinate = 80 + (32 * j);
					else
						xCordinate = 62 + (32 * j);

					yCordinate = (60 * i) - 5;

					//Place label on calculated position
					acceptorLbl[lblNo] = new JLabel("/");
					acceptorLbl[lblNo].setForeground(Color.RED);
					acceptorLbl[lblNo].setFont(new Font("Courier New", Font.BOLD, 26));
					acceptorLbl[lblNo].setBackground(Color.WHITE);
					acceptorLbl[lblNo].setBounds(xCordinate,yCordinate, 16, 40);
					acceptorLbl[lblNo].setOpaque(false);
					layeredPane_1.add(acceptorLbl[lblNo],new Integer(2));

					lblNo++;

				}
			}
		}
		noOfDeletionsInAcceptorPrefList = lblNo;
	}

	//Method to save the current instance to an output file
	public void saveInstances(BufferedWriter bw)
	{		
		try{
			bw.newLine();       
			bw.write(" --------------------Preferences-------------------- ");
			bw.newLine();

			//First write men preferences
			bw.write("     Men preferences");
			bw.newLine();

			for(proposer eachProposer : algo.proposerList)
			{
				bw.append("     " + eachProposer.getName() + " : [");
				for(String eachacceptor : eachProposer.getPrefList())
					bw.append("  " + eachacceptor);
				bw.append(" ]");
				bw.newLine();
			}
			bw.newLine();
			bw.newLine();

			//Then write women preferences
			bw.write("     Women preferences");
			bw.newLine();

			for(acceptor eachacceptor : algo.acceptorList)
			{
				bw.append("     " + eachacceptor.getName() + " : [");
				for(String eachProposer : eachacceptor.getPrefList())
					bw.append("  " + eachProposer);
				bw.append(" ]");
				bw.newLine();
			}
			bw.newLine();
		}
		catch (IOException e) {}		
	}

	//Method to save matchings to an output file
	public void saveMatches(BufferedWriter bw)
	{		
		try {
			bw.newLine();
			bw.write(" --------------------Matchings-------------------- ");
			bw.newLine();
			bw.write("     Men  Women");
			bw.newLine();

			for(Map.Entry<String, String> entry: algo.matches.entrySet()) 
			{
				bw.append("      " + entry.getKey() + "    " + entry.getValue());
				bw.newLine();
			}
			bw.newLine();

		} catch (IOException e) {} 
	}

	//Method to save execution trace to an output file
	public void saveExecutionTrace(BufferedWriter bw)
	{		
		String trace = "";
		try {
			bw.newLine();
			bw.write(" --------------------Execution trace-------------------- ");
			bw.newLine();
			bw.newLine();

			for(int k = 0 ; k < algo.globalFrameIncrementer ; k++)
			{
				bw.append("      " + algo.frames.get(k).executionTrace);
				bw.newLine();
			}
			bw.newLine();

		} catch (IOException e) {} 
	}
}



