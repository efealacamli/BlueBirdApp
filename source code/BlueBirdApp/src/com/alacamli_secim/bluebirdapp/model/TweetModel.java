package com.alacamli_secim.bluebirdapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import twitter4j.util.TimeSpanConverter;


public class TweetModel implements Serializable{

	private static final long serialVersionUID = -1521189789495864787L;
	String Username;
	String uName;
	String uDescription;
	ArrayList<String> userTimelineTweets = new ArrayList<String>();
	ArrayList<String> homeTimelineTweets = new ArrayList<String>();
	List<twitter4j.Status> JSONUserTimelineStatuses;

	
	public void setUsername(String user){
		Username = user;
	}
	
	public void setName(String name){
		uName = name;
	}
	
	public void setDescription(String desc){
		uDescription = desc;
	}
	
	public String string(){
		return Username;
	}
	
	public String getName(){
		return uName;
	}
	
	public String getUsername(){
		return Username;
	}
	
	public String getDescription(){
		return uDescription;
	}
	
	public ArrayList<String> getUserTimelineTweets(){
		return userTimelineTweets;
	}
	
	public ArrayList<String> getHomeTimelineTweets(){
		return homeTimelineTweets;
	}
	
	public void setUserStatus(List<twitter4j.Status> status){
		TimeSpanConverter converter = new TimeSpanConverter();
        for (twitter4j.Status status1 : status) {
        	//Parse the user timeline, get date and do time conversion
        	String createdAt = converter.toTimeSpanString(status1.getCreatedAt()); 
        	//Parse the user timeline and get screen name, tweet. Put it to the arraylist
        	userTimelineTweets.add("@" + status1.getUser().getScreenName() + " \n" + status1.getText() + "\n" + createdAt);
            //System.out.println("@" + status1.getUser().getScreenName() + " - " + status1.getText());
        }
	}

	public void setHomeStatus(List<twitter4j.Status> status){
		TimeSpanConverter converter = new TimeSpanConverter();
        for (twitter4j.Status status1 : status) {
        	//Parse the home timeline, get date and do time conversion
        	String createdAt = converter.toTimeSpanString(status1.getCreatedAt()); 
        	//Parse the home timeline and get screen name, tweet. Put it to the arraylist
        	homeTimelineTweets.add("@" + status1.getUser().getScreenName() + " \n" + status1.getText()+ "\n" + createdAt);
            //System.out.println("@" + status1.getUser().getScreenName() + " - " + status1.getText());
        }
	}
	
	
}
