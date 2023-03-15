package main.java.models;

public interface Generator {
	boolean isHandled(State state);
	String generateAnswerForUser(User user);

}
