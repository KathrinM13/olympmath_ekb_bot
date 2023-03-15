package main.java.models;

public class Task {
	
	private String theme;
	private String statement;
	private String decision;
	private int answer;
	
	public Task(String theme, String statement, String decision, int answer) {
		this.setTheme(theme);
		this.setStatement(statement);
		this.setDecision(decision);
		this.setAnswer(answer);
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
}
