package main.java.models;
import main.java.wrappers.WrappedUpdate;

public interface Handler {

    public static final String RESET = "/reset";
    public static final String HELP = "/help";
    public static final String START = "/start";

	boolean isHandled(State state);
	String handleMessage(User user, WrappedUpdate update);
	
	default String handleStantardCommand(User user, WrappedUpdate update, AnswerGenerator answerGenerator) {
		String command = update.getMessage();
		switch (command) {
			case RESET:
				user.reset();
			case START:
				user.reset();
		}
		return answerGenerator.generateAnswerForStantardCommand(command, user);
	}
	
	default boolean isStantardCommand(String command) {
		return command.equals(RESET) | command.equals(HELP) | command.equals(START);
	}

}
