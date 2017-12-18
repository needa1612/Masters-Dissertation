package project;

import java.util.*;

//A frame is constructed to record each event of the algorithm
//Events include - new proposal, deletion, proposer getting free, match being made
public class infoFrame 
{
	//Instance variables - Each frame consists of information to be displayed to the users
	String proposerUnderConsideration;
	String acceptorUnderConsideration;
	
	int posOfAcceptorInProposerPrefList;
	int posOfProposerInAcceptorPrefList;
	
	String[][] proposerDeletionsMatrix = new String[GSAlgo.maxNoOfParticipants][GSAlgo.maxNoOfParticipants];
	String[][] acceptorDeletionsMatrix = new String[GSAlgo.maxNoOfParticipants][GSAlgo.maxNoOfParticipants];
	
	String freeProposers;
	String executionTrace;
	String matchings;
	Map<String, String> matchesToHighlight;
	int pseudoCodeLineNo;

	//Default constructor
	public infoFrame()
	{	
		proposerUnderConsideration = "";
		acceptorUnderConsideration = "";
		
		posOfAcceptorInProposerPrefList = 0;
		posOfProposerInAcceptorPrefList = 0;
		
		for(int i = 0; i < GSAlgo.listOfProposers.size() ; i++)
			for(int j = 0; j < GSAlgo.proposerList.get(i).getPrefList().size() ; j++)
				proposerDeletionsMatrix[i][j] = GSAlgo.proposerList.get(i).getPrefList().get(j);
		
		for(int i = 0; i < GSAlgo.listOfAcceptors.size() ; i++)
			for(int j = 0; j < GSAlgo.acceptorList.get(i).getPrefList().size() ; j++)
				acceptorDeletionsMatrix[i][j] = GSAlgo.acceptorList.get(i).getPrefList().get(j);
		
		freeProposers = "";
		executionTrace = "";
		matchings = "";
		matchesToHighlight = new HashMap<String, String>();
		pseudoCodeLineNo = 0;
	}
	
	//Method to set details for a frame
	public void prepareFrame(String currentProposer, String currentAcceptor, int posInProposerPreferences, int posInAcceptorPreferences, String freeProposer, String trace , String matches, int lineNo, Map<String, String> matchingsForHighlight)
	{		
		this.proposerUnderConsideration = currentProposer;
		this.acceptorUnderConsideration = currentAcceptor;
		
		this.posOfAcceptorInProposerPrefList = posInProposerPreferences;
		this.posOfProposerInAcceptorPrefList = posInAcceptorPreferences;
	
		if( !(posInProposerPreferences == -1) )
			this.proposerDeletionsMatrix[GSAlgo.proposerList.indexOf(GSAlgo.getProposer(currentProposer))][posOfAcceptorInProposerPrefList] = "X";
			
		if( ! (posInAcceptorPreferences == -1) )	
			this.acceptorDeletionsMatrix[(int)(currentAcceptor).charAt(0) - 65][posOfProposerInAcceptorPrefList] = "X";
		
		this.freeProposers = freeProposer;
		this.executionTrace = trace;
		this.matchings = matches;
		this.pseudoCodeLineNo = lineNo;
		this.matchesToHighlight.putAll(matchingsForHighlight);		
	}
	
	//Method to clone data from previous frame
	public infoFrame cloneFrame()
	{
		infoFrame tempFrame = new infoFrame();
		
		tempFrame.proposerUnderConsideration = this.proposerUnderConsideration;
		tempFrame.acceptorUnderConsideration = this.acceptorUnderConsideration;
		
		tempFrame.posOfAcceptorInProposerPrefList = this.posOfAcceptorInProposerPrefList;
		tempFrame.posOfProposerInAcceptorPrefList = this.posOfProposerInAcceptorPrefList;

		for (int i = 0; i < this.proposerDeletionsMatrix.length; i++) 
			tempFrame.proposerDeletionsMatrix[i] = Arrays.copyOf(this.proposerDeletionsMatrix[i], this.proposerDeletionsMatrix[i].length);
		
		for (int i = 0; i < this.acceptorDeletionsMatrix.length; i++) 
			tempFrame.acceptorDeletionsMatrix[i] = Arrays.copyOf(this.acceptorDeletionsMatrix[i], this.acceptorDeletionsMatrix[i].length);
		
		return tempFrame;
	}
	
}
