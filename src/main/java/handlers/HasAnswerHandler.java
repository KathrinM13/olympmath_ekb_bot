package main.java.handlers;

import main.java.models.AnswerGenerator;
import main.java.models.Handler;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class HasAnswerHandler implements Handler {
	AnswerGenerator answerGenerator;

	public HasAnswerHandler(AnswerGenerator answerGenerator) {
		this.answerGenerator = answerGenerator;
	}

	@Override
	public boolean isHandled(State state) {
		return state == State.HAS_ANSWER;
	}

	@Override
	public String handleMessage(User user, WrappedUpdate update) {
		int answer;
		try {
			answer = Integer.parseInt(update.getMessage());
			if(answer == user.getTask().getAnswer()) {
				user.rememberTask();
				user.resetTheme();
				return "Это правильный ответ!\n".concat(answerGenerator.generateAnswerForUser(user));
			}
			else {
				return answerGenerator.generateAnswerForMistake(Mistake.WRONG_ANSWER).concat(answerGenerator.generateAnswerForUser(user));
			}
		}
		catch (NumberFormatException e) {
			return answerGenerator.generateAnswerForMistake(Mistake.WRONG_DATA).concat(answerGenerator.generateAnswerForUser(user));
		}
	}

}
