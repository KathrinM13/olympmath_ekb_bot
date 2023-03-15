package main.java.answer_generators;

import main.java.models.Generator;
import main.java.models.State;
import main.java.models.User;

public class ChooseTaskOrTheoryAnswerGenerator implements Generator {
	
	public static final String CHOOSE_TASK_OR_THEORY_MESSAGE = "Выбери, чем ты хочешь заняться:\n 1 вспомнить теорию \n 2 решать задачу\n";

	@Override
	public boolean isHandled(State state) {
		return state == State.CHOOSE_TASK_OR_THEORY;
	}

	@Override
	public String generateAnswerForUser(User user) {
		return CHOOSE_TASK_OR_THEORY_MESSAGE;
	}

}
