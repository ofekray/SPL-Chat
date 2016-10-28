package json;

import java.util.Arrays;

public class JSON {
	Questions[] questions;
	public JSON(Questions[] questions) {
		this.questions = questions;
	}
	public Questions[] getQuestions() {
		return questions;
	}
	public void setQuestions(Questions[] questions) {
		this.questions = questions;
	}
	
	@Override
	public String toString() {
		return "JSON [questions=" + Arrays.toString(questions) + "]";
	}
	
	
	
	
	
	

	
	
}