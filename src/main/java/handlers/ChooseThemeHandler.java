package main.java.handlers;
import main.java.models.AnswerGenerator;
import main.java.models.Handler;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.Storage;
import main.java.models.TasksStorage;
import main.java.models.Theme;
import main.java.models.ThemesStorage;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class ChooseThemeHandler implements Handler {
	
	Storage<Theme> themes;
	AnswerGenerator answerGenerator;
	
	public ChooseThemeHandler(Storage<Theme> themes, AnswerGenerator answerGenerator) {
		this.themes = themes;
		this.answerGenerator = answerGenerator;
	}

	@Override
	public boolean isHandled(State state) {
		return state == State.CHOOSE_THEME;
	}

	@Override
	public String handleMessage(User user, WrappedUpdate update) {
		Theme theme = themes.getAll().stream()
				.filter(th -> th.getName().equalsIgnoreCase(update.getMessage()))
				.findAny()
				.orElse(null);
		if(theme != null && theme.getGrade() == user.getGrade()) {
			user.setTheme(theme);
			user.setState(State.CHOOSE_TASK_OR_THEORY);
			return answerGenerator.generateAnswerForUser(user);
		}
		else {
			return answerGenerator.generateAnswerForMistake(Mistake.WRONG_THEME).concat(answerGenerator.generateAnswerForUser(user));
		}
	}

}
