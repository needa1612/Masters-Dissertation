package project;

import java.util.*;

public class proposer extends person
{	
	private List<Integer> rankingList = new ArrayList<Integer>(GSAlgo.maxNoOfParticipants);
	
	//Constructor
	public proposer(String personName, List<String> preferences)
	{
		super(personName, preferences);
		for(int i = 0; i < GSAlgo.maxNoOfParticipants ; i++)
			rankingList.add(0);
	}

	//getter
	public List<Integer> getRankingList()
	{
		return rankingList;
	}
	
	//Method to prepare ranking list for proposers
	public void prepareRankingList()
	{
		int index = 1;
		List<String> proposerPrefList = this.getPrefList();
		
		for(String eachAcceptor : proposerPrefList)
		{
			rankingList.set((int)((eachAcceptor).charAt(0)) - 65, index);
			index++;
		}
	}
	
	//Method to return position/index of acceptor in proposer's preference list
	public int getAcceptorPosition(String acceptorName)
	{
		for(String eachAcceptor : prefList)
		{
			if(eachAcceptor.equalsIgnoreCase(acceptorName))
				return prefList.indexOf(eachAcceptor);
		}
		//if acceptor not found in proposers preference list, return -1
		return -1;
	}
}
