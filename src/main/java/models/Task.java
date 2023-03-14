package main.java.models;

public class Task {
	
	private String theme;
	private String statement;
	private String answer;
	
	public Task(String theme, String statement, String Answer) {
		this.setTheme(theme);
		this.setStatement(statement);
		this.setAnswer(Answer);
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
