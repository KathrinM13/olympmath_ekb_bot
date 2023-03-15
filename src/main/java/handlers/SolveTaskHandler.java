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
	public boolean isHandled(State state) {
		return state == State.SOLVE_TASK;
	}

	@Override
	public String handleMessage(User user, WrappedUpdate update) {
		
		if(update.getMessage().equalsIgnoreCase("решение")) {
			String answer = user.getTask().getDecision().concat("\n");
			user.rememberTask();
			user.resetTheme();
			return answer.concat(answerGenerator.generateAnswerForUser(user));
		}
		else if(update.getMessage().equalsIgnoreCase("ответ")) {
			user.setState(State.HAS_ANSWER);
			return answerGenerator.generateAnswerForUser(user);
		}
		else {
			return answerGenerator.generateAnswerForMistake(Mistake.WRONG_OPTION).concat(answerGenerator.generateAnswerForUser(user));
		}
	}

}
