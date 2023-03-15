package main.java.answer_generators;

import main.java.models.Generator;
import main.java.models.State;
import main.java.models.User;

public class ChooseClassAnswerGenerator implements Generator {
	
	public static final String CHOOSE_CLASS_MESSAGE = "У меня есть задачи для 8 и 9 классов. В каком классе ты учишься?\n";

	@Override
	public boolean isHandled(State state) {
		return state == State.CHOOSE_CLASS;
	}

	@Override
	public String generateAnswerForUser(User user) {
		return CHOOSE_CLASS_MESSAGE;
	}

}
