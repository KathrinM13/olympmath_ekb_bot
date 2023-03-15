package main.java.answer_generators;

import main.java.models.Generator;
import main.java.models.State;
import main.java.models.User;

public class HasAnswerAnswerGenerator implements Generator {

	@Override
	public boolean isHandled(State state) {
		return state == State.HAS_ANSWER;
	}

	@Override
	public String generateAnswerForUser(User user) {
		return "Теперь вводи ответ\n";
	}

}
