package json;

public class Questions {

	@Override
	public String toString() {
		return "Questions [questionText=" + questionText + ", realAnswer="
				+ realAnswer + "]";
	}
	String questionText;
	String realAnswer;
	
	public Questions(String questionText, String realAnswer) {
		this.questionText = questionText;
		this.realAnswer = realAnswer;
	}
	
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getRealAnswer() {
		return realAnswer;
	}
	public void setRealAnswer(String realAnswer) {
		this.realAnswer = realAnswer;
	}
}
