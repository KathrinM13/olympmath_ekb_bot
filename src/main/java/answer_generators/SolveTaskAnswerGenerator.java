package main.java.answer_generators;

import main.java.models.Generator;
import main.java.models.State;
import main.java.models.User;

public class SolveTaskAnswerGenerator implements Generator {
	
	public static final String SOLVE_TASK_MESSAGE = "\n Введи \"решение\", если хочешь получить ссылку на решение "+
													"или введи \"ответ\", если хочешь ввести ответ (ответ должен быть натуральным числом)";

	@Override
	public boolean isHandled(State state) {
		return state == State.SOLVE_TASK;
	}

	@Override
	public String generateAnswerForUser(User user) {
		return user.getTask().getStatement().concat(SOLVE_TASK_MESSAGE);
	}

}
