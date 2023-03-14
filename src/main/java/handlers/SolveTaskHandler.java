package main.java.handlers;

import main.java.models.AnswerGenerator;
import main.java.models.Handler;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class SolveTaskHandler implements Handler {
	
	AnswerGenerator answerGenerator;
	
	public SolveTaskHandler(AnswerGenerator answerGenerator) {
		this.answerGenerator = answerGenerator;
	}

	@Override
	public State handledState() {
		return State.SOLVE_TASK;
	}

	@Override
	public String handleMessage(User user, WrappedUpdate update) {
		if(isStantardCommand(update.getMessage())) {
			return handleStantardCommand(user, update, answerGenerator);
		}
		
		if(update.getMessage().equalsIgnoreCase("решение")) {
			return user.getTask().getAnswer();
		}
		else {
			return answerGenerator.generateAnswerForMistake(Mistake.WRONG_OPTION).concat(answerGenerator.generateAnswerForUser(user));
		}
	}

}
