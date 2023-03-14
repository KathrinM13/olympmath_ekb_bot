package main.java.handlers;
import main.java.models.AnswerGenerator;
import main.java.models.Handler;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.Storage;
import main.java.models.Theme;
import main.java.models.ThemesStorage;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class ChooseClassHandler implements Handler {
	AnswerGenerator answerGenerator;
	
	public ChooseClassHandler(AnswerGenerator answerGenerator) {
		this.answerGenerator = answerGenerator;
	}

	@Override
	public State handledState() {
		return State.CHOOSE_CLASS;
	}

	@Override
	public String handleMessage(User user, WrappedUpdate update) {
		if(isStantardCommand(update.getMessage())) {
			return handleStantardCommand(user, update, answerGenerator);
		}
		
		int grade;

		try {
			grade = Integer.parseInt(update.getMessage());
			if (grade == 9 | grade == 8) {
				user.setGrade(grade);
				user.setState(State.CHOOSE_THEME);
				return answerGenerator.generateAnswerForUser(user);
			}
			else {
				return answerGenerator.generateAnswerForMistake(Mistake.WRONG_CLASS).concat(answerGenerator.generateAnswerForUser(user));
			}
		}
		catch (NumberFormatException e) {
			return answerGenerator.generateAnswerForMistake(Mistake.WRONG_DATA).concat(answerGenerator.generateAnswerForUser(user));
		}
	}
}
