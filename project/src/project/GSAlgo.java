package project;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class GSAlgo 
{
	//Instance variables consisting of the list of proposers and acceptors with their preferences,
	//the list of free proposers, matchings, and frames
	static ArrayList<proposer> proposerList;
	static ArrayList<acceptor> acceptorList;
	static ArrayList<String> listOfProposers;
	static ArrayList<String> listOfAcceptors;
	static List<proposer> freeProposers;
	static Map<String, String> matches;

    static int globalFrameIncrementer;
	static int maxNoOfParticipants;
	static ArrayList<infoFrame> frames;

	//Default constructor
	public GSAlgo()
	{		
		proposerList =  new ArrayList<proposer>();
		acceptorList = new ArrayList<acceptor>();
		freeProposers  = new LinkedList<proposer>();
		listOfProposers = new ArrayList<String>();
		listOfAcceptors = new ArrayList<String>();
		matches = new HashMap<String, String>();

		maxNoOfParticipants = 10;
		globalFrameIncrementer = 0;
		frames = new ArrayList<infoFrame>();
	}


	//Method to take input instance from file
	public static void takeInputFromFile(FileReader file)
	{		
		BufferedReader fileReader = null;
		try {
			String currentLineString = null;
			String[] currentLineArray = null;
			fileReader = new BufferedReader(file);

			//Read preferences from file and store them into data structures

			//Reading proposer preferences
			while(!((currentLineString = fileReader.readLine()).equals(""))) 
			{
				currentLineArray = currentLineString.split(" ");
				List<String> prefList = Arrays.asList(Arrays.copyOfRange(currentLineArray, 1, currentLineArray.length));
				proposerList.add(new proposer(currentLineArray[0],prefList));
				listOfProposers.add(currentLineArray[0]);
			}

			//Reading acceptor preferences
			while(!((currentLineString = fileReader.readLine()).equals("")))
			{
				currentLineArray = currentLineString.split(" ");
				List<String> prefList = Arrays.asList(Arrays.copyOfRange(currentLineArray, 1, currentLineArray.length));
				acceptorList.add(new acceptor(currentLineArray[0],prefList));
				listOfAcceptors.add(currentLineArray[0]);
			}

			fileReader.close();
		}	

		catch (Exception e) 
		{} 

		//Initially all proposers will be free - all are added to the list of free proposers
		for(int i = 0 ; i < proposerList.size() ; i++)
			freeProposers.add(proposerList.get(i));
	}


	//Method to generate instance randomly
	//ASSUMPTION : this function always generates consistent input	
	public static void randomInputGeneration(int noOfProposers, int noOfAcceptors) throws Exception
	{
		Random rand = new Random();

		//Preparing list of proposers
		for(int i = 0 ; i < noOfProposers ; i++)
			listOfProposers.add("" + (i+1));

		//Preparing proposer preference list
		for(int i = 0; i < noOfProposers; i++)
		{
			int noOfPreferences = rand.nextInt(noOfAcceptors -1);
			List<String> preferences = new ArrayList<String>();

			noOfPreferences++;
			for(int j = 0 ; j < noOfPreferences ; j++)
			{
				String generatedacceptor = Character.toString((char)(rand.nextInt(noOfAcceptors) + 65));
				if( ! (preferences.contains(generatedacceptor)))
					preferences.add(generatedacceptor);	
			}

			proposerList.add(new proposer(("" + (i+1)), preferences));
		}

		//Preparing list of acceptors
		for(int i = 0 ; i < noOfAcceptors ; i++)
			listOfAcceptors.add("" + (char)(i+65));

		//Preparing acceptor preference list
		for(String eachacceptor : listOfAcceptors)
		{
			List<String> preferences = new ArrayList<String>();

			for(proposer eachProposer: proposerList)
			{
				if(eachProposer.getPrefList().contains(eachacceptor))
					preferences.add(eachProposer.getName());
			}

			acceptorList.add(new acceptor(eachacceptor, preferences));
		}

		//Initially all proposers will be free - all are added to the list of free proposers
		for(int i = 0 ; i < proposerList.size() ; i++)
			freeProposers.add(proposerList.get(i));
	}


	//Method to check if the instance imported from file is consistent
	//Return true if consistent, otherwise false
	public static boolean consistentInputCheck()
	{
		int proposerPrefCount = 0 , acceptorPrefCount = 0;

		try
		{
			for(proposer eachProposer : proposerList)
			{
				List<String> currentProposerPrefList = eachProposer.getPrefList();
				
				//Return false if proposers are not numbers or are blanks
				if((!((1 <= ((int)(eachProposer.getName().charAt(0))) && ((int)(eachProposer.getName().charAt(0))) >=10))) || eachProposer.getName().equalsIgnoreCase(""))
					return false;

				for(String acceptor : currentProposerPrefList)
				{
					//Return false if acceptors in the preference list of this proposer are either numbers or blanks
					if(!((int)((acceptor).charAt(0)) >= 65) || acceptor.equalsIgnoreCase(""))
						return false;

					proposerPrefCount++;

					//Return false if this acceptor does not contain this proposer in its preference list
					if((getAcceptor(acceptor).getPrefList()).contains(eachProposer.getName())) ;
					else 
						return false;
				}

			}

			for(acceptor eachacceptor: acceptorList)
			{
				List<String> currentacceptorPrefList = eachacceptor.getPrefList();
				acceptorPrefCount += currentacceptorPrefList.size();
			}
		}

		catch(Exception e)
		{
            //Any number format exceptions caught will return false - invalid input
			return false;
		}

		//return false, if the sum of the size of preference lists for proposers is not equal to that of acceptors
		if(proposerPrefCount == acceptorPrefCount)
			return true;
		else
			return false;
	}

	//Method to randomize the current instance
	//Proves that stable matchings obtained are independent of the order of proposals
	public static void randomizeCurrentInput()
	{
		Random rand = new Random();
		freeProposers  = new LinkedList<proposer>();

		for( int i = 0; i < proposerList.size(); i++ )
		{
			int position = rand.nextInt(proposerList.size());

			//Swap the ith proposer with the randomly generated proposer
			proposer tempProposer = proposerList.get(position);
			proposerList.set(position, proposerList.get(i));
			proposerList.set(i, tempProposer);
		}

		//Order of proposers in the list of free proposers will also change
		for(int i = 0 ; i < proposerList.size() ; i++)
			freeProposers.add(proposerList.get(i));
	}


	//Method to obtain acceptor object from its name
	//Returns the object if found in the list of acceptors, otherwise return null
	public static acceptor getAcceptor(String acceptorName)
	{
		for(acceptor eachAcceptor : acceptorList)
		{
			if(eachAcceptor.getName().equalsIgnoreCase(acceptorName))
				return eachAcceptor;
		}
		//Else no acceptor with this name - return null
		return null;

	}


	//Similarly, this method is used to obtain proposer object from its name
	//Returns the object if found in the list of proposers, otherwise return null
	public static proposer getProposer(String proposerName)
	{
		for(proposer eachProposer : proposerList)
		{
			if(eachProposer.getName().equalsIgnoreCase(proposerName))
				return eachProposer;
		}
		//Else no proposer with this name - return null
		return null;

	}


	//Method that runs the algorithm to produce matchings
	//It also directs frame creation for every event in the algorithm
	public static Map<String, String> doMatching()
	{	
		//Initially all proposers are free - create frame
		createFrame("", "", -1, -1 , "", 1, matches);

		//Loop until no more free proposers
		while(!freeProposers.isEmpty()) 
		{							
			//Stack functioning - remove the topmost proposer
			proposer currentProposer = freeProposers.remove(0); 
			List<String> currentProposerPrefers = currentProposer.getPrefList();

			//Loop runs for each undeleted acceptor in proposer's preference list
			for(String acceptor : currentProposerPrefers) 
			{				
				acceptor currentAcceptor = getAcceptor(acceptor);

				//Make proposal only if acceptor in preference list is still available (not deleted)
				if(currentProposer.getPointer().get(currentProposer.getPrefList().indexOf(currentAcceptor.getName())))
				{	

					//Create frame to indicate the proposer under consideration
					createFrame(currentProposer.getName(), "", -1, -1 , "\n MAN : " + currentProposer.getName() + "\n", 2, matches);

					//Create frame to indicate that currentAcceptor is the first undeleted acceptor on currentProposer's list
					createFrame(currentProposer.getName(), acceptor, -1,-1," " + acceptor + " is the first undeleted woman on " + currentProposer.getName() + "'s list\n", 3,matches);

					//Proposal made - create new frame
					createFrame(currentProposer.getName(), acceptor, -1, -1 , " " + currentProposer.getName() + " proposes " + acceptor + "\n", 4,matches);

					//If this acceptor is not already paired, make a pair
					if(!matches.containsKey(acceptor))
					{
						//Add it to the list of matchings
						matches.put(acceptor, currentProposer.getName());

						//New match made - create a frame here
						createFrame(currentProposer.getName(), acceptor, -1, -1, " " + currentProposer.getName() + " gets matched to " + acceptor + "\n", 7,matches);
					}

					//If this acceptor is already matched
					else
					{
						String currentlyMatchedProposer = matches.get(acceptor);

						//Create a frame to indicate that it is already matched to a proposer
						createFrame(currentlyMatchedProposer, acceptor, -1,-1, " But " + acceptor + " is already matched to " + currentlyMatchedProposer + "\n", 5, matches);

						//Create a frame to indicate that the currently matched proposer becomes free
						createFrame(currentlyMatchedProposer, acceptor, -1 , -1, " Man " + currentlyMatchedProposer + " becomes free now\n", 6, matches);	

						//Match acceptor to the new proposer and update matchings
						//'put' replaces the already existing partner of acceptor
						matches.put(acceptor, currentProposer.getName());

						//Create frame to indicate new match - change of partner
						createFrame(currentProposer.getName(), acceptor, -1, -1, " " + currentProposer.getName() + " gets matched to " + acceptor + "\n", 7, matches);

						//Add the freed proposer to the set of 'free proposers'
						freeProposers.add(getProposer(currentlyMatchedProposer));

					}

					//Perform deletions only if there are proposers to delete
					if(currentAcceptor.getPrefList().indexOf(currentProposer.getName()) == (currentAcceptor.getPrefList().size()-1))
					{
						//deletions not needed!
					}
					else
					{
						//Now perform deletions from acceptor and proposer preference lists

						//perform deletions only if there are undeleted entries after the current proposer
						if(! undeletedEntriesPresent(currentAcceptor.getPrefList().indexOf(currentProposer.getName()) + 1, currentAcceptor))
						{
							//no deletions needed		
						}
						else
						{
							List<String> currentAcceptorPrefList = currentAcceptor.getPrefList();						

							//For each successor n of m on w's list - create a frame to indicate deletions
							createFrame(currentProposer.getName(), currentAcceptor.getName(), -1,-1, " Deleting all men after " + currentProposer.getName() + " in " + currentAcceptor.getName() + "'s list\n", 8, matches );

							int first = 0;
							//Loop to perform deletions from the acceptor's preference list - from the current proposer to the end of acceptor's preference list
							for(int i = currentAcceptorPrefList.indexOf(currentProposer.getName()) + 1 ; i <= currentAcceptorPrefList.size() ; i++)
							{			
								//first check if you have any entries to delete
								if(undeletedEntriesPresent(i, currentAcceptor))
								{
									//Create frame to indicate deletions from the acceptor's preference list
									//currentAcceptor.getPointer().set(currentAcceptor.getProposerPosition(currentProposer.getName()), false);
									currentAcceptor.getPointer().set(i, false);

									//createFrame(currentProposer.getName(), currentAcceptor.getName(), -1 , i, " " + currentAcceptor.getPrefList().get(i) + " deleted from " + currentAcceptor.getName() + "'s preference list \n",9);
									if(first == 0)
									{
										createFrame(currentProposer.getName(), currentAcceptor.getName(), -1 , i, " " + currentAcceptor.getPrefList().get(i) + " deleted from " + currentAcceptor.getName() + "'s preference list \n",9, matches);
										first++;
									}
									else
										createFrame(currentAcceptor.getPrefList().get(i-1), currentAcceptor.getName(), -1 , i, " " + currentAcceptor.getPrefList().get(i) + " deleted from " + currentAcceptor.getName() + "'s preference list \n",9, matches);


									//Perform deletions from proposer preference list as well
									proposer proposerForDeletion = getProposer(currentAcceptor.getPrefList().get(i));
									proposerForDeletion.getPointer().set((getProposer(currentAcceptor.getPrefList().get(i)).getPrefList()).indexOf(acceptor),false);

									//Create frame to indicate deletions from proposer's preference list
									int proposerPosition = getProposerPosition(currentAcceptor.getPrefList().get(i));
									//createFrame("" + proposerPosition, currentAcceptor.getName(), (getProposer(currentAcceptor.getPrefList().get(i)).getPrefList()).indexOf(acceptor), -1, " " + currentAcceptor.getName() + " deleted from " + currentAcceptor.getPrefList().get(i) + "'s preference list \n",9);	
									createFrame(currentAcceptor.getPrefList().get(i), currentAcceptor.getName(), (getProposer(currentAcceptor.getPrefList().get(i)).getPrefList()).indexOf(acceptor), -1, " " + currentAcceptor.getName() + " deleted from " + currentAcceptor.getPrefList().get(i) + "'s preference list \n",9, matches);	
								}
								else
									break;
							}
						}
					}

					break;
				}
				else;
			}
		}


		//Indicate end of algorithm - return the stable matchings generated
		createFrame("", "", -1, -1 , " Stable matchings generated\n", 10, matches);
		freeProposers.clear();
		return matches;
	}

	//Method to find out if there are undeleted entries in acceptor's preference list
	public static boolean undeletedEntriesPresent(int pos, acceptor currentAcceptor)
	{
		for(int i = pos; i < currentAcceptor.getPrefList().size(); i++)
		{
			if(currentAcceptor.getPointer().get(i))
				return true;
		}
		return false;
	}

	//Method to find the proposer to which an acceptor is matched to 
	public static String findKey(String acceptor)
	{
		for(Entry<String, String> entry : matches.entrySet())
		{
			if (Objects.equals(acceptor, entry.getValue())) 
				return entry.getKey();
		}
		return null;

	}

	//Method to find out the proposer number using proposer name
	public static int getProposerPosition(String proposerUnderConsideration)
	{
		for(int i = 0; i < proposerList.size() ; i++)
		{
			if(Integer.parseInt(proposerUnderConsideration) == Integer.parseInt(proposerList.get(i).getName()))
				return (i+1);
		}
		return -1;
	}

	//Method to check if the matchings generated are stable or not
	//If stable - return true, otherwise false
	public static boolean isStable() throws Exception
	{
		for(acceptor eachacceptor : acceptorList)
		{
			int i = 0;

			List<String> currentacceptorPreferences = eachacceptor.getPrefList();
			String matchedProposer = matches.get(eachacceptor.getName());

			if(matchedProposer == null)
				continue;

			while( ! (currentacceptorPreferences.get(i).equalsIgnoreCase(matchedProposer)))
			{
				List<Integer> rankingListForCurrentProposer = getProposer(currentacceptorPreferences.get(i)).getRankingList();
				String currentProposerMatchedTo = findKey(currentacceptorPreferences.get(i));

				if(currentProposerMatchedTo == null)
				{
					i++;
					continue;
				}

				int indexOfEachacceptor = (int)(eachacceptor.getName()).charAt(0) - 65;
				int indexOfMatchedacceptor = (int)(currentProposerMatchedTo).charAt(0) - 65;
				List<Integer> currentProposerRankingList = getProposer(currentacceptorPreferences.get(i)).getRankingList();

				//If index of matched acceptor in proposer's ranking list 
				//is less than index of any other proposer on the acceptor's list - continue
				//Otherwise current acceptor prefers a different proposer over the one it is matched to - return false
				if(currentProposerRankingList.get(indexOfMatchedacceptor) <= currentProposerRankingList.get(indexOfEachacceptor));
				else
					return false;

				i++;
			}
		}
		return true;
	}

	//Method to create a frame - set all details of a frame by calling 'prepareFrame' method for each frame
	public static void createFrame(String currentProposer, String currentacceptor, int posInProposerPreferences, int posInacceptorPreferences, String trace, int pseudoCodeLineNo, Map<String, String> matchingsForHighlight)
	{
		infoFrame newFrame = new infoFrame();
		String freeProposer = "" , matchList = "", executionTrace = "";

		//Cloning data from previous frame 
		if(globalFrameIncrementer > 0)
		{
			infoFrame oldFrame = frames.get(globalFrameIncrementer - 1);
			newFrame  = oldFrame.cloneFrame();
		}

		//Construct new sets of 'freeProposer' , 'matches' and 'executionTrace' for each frame
		for(int i = 0 ; i < freeProposers.size() ; i++)
			freeProposer = freeProposer + "  " + freeProposers.get(i).getName();

		for(Map.Entry<String, String> entry: matches.entrySet()) 
			matchList = matchList + "  (" + entry.getKey() + "," + entry.getValue() + ")";

		executionTrace = trace;

		//Calling 'prepareFrame' method to finally set all frame details
		newFrame.prepareFrame(currentProposer, currentacceptor, posInProposerPreferences, posInacceptorPreferences , freeProposer, executionTrace ,matchList, pseudoCodeLineNo, matchingsForHighlight);
		frames.add(newFrame) ;
		globalFrameIncrementer++;
	}
}
