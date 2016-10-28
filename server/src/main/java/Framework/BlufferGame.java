package Framework;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Collections;

import json.JSON;
import json.Questions;

import com.google.gson.Gson;


public class BlufferGame {
	
	String jsonPath;
	ArrayList<Questions> questions;
	ArrayList<BlufferPlayer> roomPlayers;
	int currentQuestionIndex;
	ArrayList<String> shuffledAnswers;
	int answerNumber;
	int guessNumber;
	
	public BlufferGame(String jsonPath, ArrayList<BlufferPlayer> roomPlayers) throws FileNotFoundException {
		this.jsonPath = jsonPath;
		this.roomPlayers = roomPlayers;
		this.questions = new ArrayList<Questions>();
		load3RandQuestions();
		currentQuestionIndex = 0;
		answerNumber = 0;
		guessNumber = 0;
	}
	
	public boolean validGuessNumber(String number)
	{
		int guessNum;
		try
		{
			guessNum = Integer.parseInt(number);
		}
		catch (Exception ex)
		{
			return false;
		}	
		return guessNum < shuffledAnswers.size();
	}
	
	public String getShuffledAnswersString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<shuffledAnswers.size(); i++)
		{
			sb.append(i + ". " + shuffledAnswers.get(i) + " ");
		}
		return sb.toString();
	}
	
	public String getGameSummary() {
		StringBuilder sb = new StringBuilder();
		for(BlufferPlayer player : roomPlayers)
		{
			sb.append(player.getNickName() + ": " + player.getScore() +"pts, ");
		}
		return sb.toString();
	}
	
	public Questions getCurrentQuestion()
	{
		return this.questions.get(this.currentQuestionIndex);
	}
	
	public boolean moveToNextQuestion()
	{
		answerNumber = 0;
		guessNumber = 0;
		this.currentQuestionIndex++;
		if (this.currentQuestionIndex == 3)
			return false;
		return true;
	}
	
	public void PlayerAnswerToQuestion(String nickName, String answer)
	{
		for(BlufferPlayer player : roomPlayers)
		{
			if (player.nickName.equals(nickName))
			{
				player.setPlayerAns(answer.toLowerCase());
				break;
			}
		}
		answerNumber++;
		if (everyoneAnswerd())
		{
			shuffledAnswers = getShuffledAnswers();
		}
			
	}
	public boolean everyoneAnswerd(){
		return (this.answerNumber == this.roomPlayers.size());
	}
	

	
	public boolean PlayerGuessRealAnswerToQuestion(String nickName, String guessNum)
	{
		//if player guessed the correct answer add 10 points to his score
		if (shuffledAnswers.get(Integer.parseInt(guessNum)).toLowerCase().equals(getCurrentQuestion().getRealAnswer().toLowerCase()))
		{
			for(BlufferPlayer player : roomPlayers)
			{
				if (player.nickName.equals(nickName))
				{
					player.addToScore(10);
					break;
				}
			}
			guessNumber++;
			return true;
		}
		//if player didn't guess the current answer, add 5 points to (other player) which the answer he guessed belonged to
		else
		{
			for(BlufferPlayer player : roomPlayers)
			{
				if (!player.equals(nickName) && player.getPlayerAns().toLowerCase().equals(shuffledAnswers.get(Integer.parseInt(guessNum)).toLowerCase()))
				{
					player.addToScore(5);
				}
			}
			guessNumber++;
			return false;
		}
	}
	
	public boolean everyoneGuessed(){
		return (this.guessNumber == this.roomPlayers.size());
	}

	private void load3RandQuestions() throws FileNotFoundException
	{
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader(this.jsonPath));
		JSON json =  gson.fromJson(br, JSON.class);
		ArrayList<Questions> allQuestions = new ArrayList<Questions>(Arrays.asList(json.getQuestions()));
		Collections.shuffle(allQuestions, new Random());
		for (int i=0; i<3; i++)
		{
			this.questions.add(allQuestions.get(i));
		}
	}
	
	private ArrayList<String> getShuffledAnswers() {
		ArrayList<String> allAns = new ArrayList<String>();
		for(BlufferPlayer player : roomPlayers)
		{
			if (!allAns.contains(player.getPlayerAns()))
				allAns.add(player.getPlayerAns());
		}
		if (!allAns.contains(getCurrentQuestion().getRealAnswer()))
		{
			allAns.add(getCurrentQuestion().getRealAnswer());
		}
		Collections.shuffle(allAns, new Random());
		return allAns;
	}

}
