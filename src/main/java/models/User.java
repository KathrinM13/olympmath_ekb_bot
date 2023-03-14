package main.java.models;

public class User {
	
	private State state;
	private Theme theme;
	private String chatId;
	private int grade;
	private Task task;
	
	public User(String chatId) {
		this.setChatId(chatId);
		this.setState(State.CHOOSE_CLASS);
		this.setTheme(null);
		this.setTask(null);
	}
	
	public void reset() {
		this.setState(State.CHOOSE_CLASS);
		this.setTheme(null);
		this.setTask(null);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
