package test.java;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import main.java.answer_generators.ChooseClassAnswerGenerator;
import main.java.answer_generators.ChooseTaskOrTheoryAnswerGenerator;
import main.java.answer_generators.ChooseThemeAnswerGenerator;
import main.java.answer_generators.SolveTaskAnswerGenerator;
import main.java.handlers.ChooseThemeHandler;
import main.java.models.AnswerGenerator;
import main.java.models.Generator;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.TasksStorage;
import main.java.models.Theme;
import main.java.models.ThemesStorage;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class ChooseThemeHadlerTest extends TestCase {

	private AnswerGenerator answerGenerator;
	private ChooseThemeHandler handler;
	private ThemesStorage themes;
	ArrayList<Theme> themesList;
	private List<Generator> answerGenerators;

	public void setUp() {
		themesList = new ArrayList<Theme>();
		themesList.add(new Theme("Тема 1", "Теория 1", 9));
		themesList.add(new Theme("Тема 2", "Теория 2", 8));

		themes = new ThemesStorage(themesList);
		answerGenerators = List.of(				
				new ChooseClassAnswerGenerator(),
				new ChooseThemeAnswerGenerator(themes),
				new ChooseTaskOrTheoryAnswerGenerator(),
				new SolveTaskAnswerGenerator());
		answerGenerator = new AnswerGenerator(answerGenerators);
		handler = new ChooseThemeHandler(themes, answerGenerator);
	}
	
	public void testWrongThemeName() {
		User user = new User("1");
		user.setGrade(9);
		user.setState(State.CHOOSE_THEME);

		String expected = answerGenerator.generateAnswerForMistake(Mistake.WRONG_THEME).concat(answerGenerator.generateAnswerForUser(user));
		WrappedUpdate update = new WrappedUpdate("1", "abc");
		String actual = handler.handleMessage(user, update);
		assertEquals(expected, actual);
		assertEquals(user.getState(), State.CHOOSE_THEME);
	}
	
	public void testWrongThemeClass() {
		User user = new User("1");
		user.setGrade(9);
		user.setState(State.CHOOSE_THEME);

		String expected = answerGenerator.generateAnswerForMistake(Mistake.WRONG_THEME).concat(answerGenerator.generateAnswerForUser(user));

		WrappedUpdate update = new WrappedUpdate("1", "Тема 2");
		String actual = handler.handleMessage(user, update);

		assertEquals(expected, actual);
		assertEquals(user.getState(), State.CHOOSE_THEME);
	}
	
	public void testCorrectTheme() {
		User user1 = new User("1"); // expected user
		user1.setGrade(9);
		user1.setState(State.CHOOSE_TASK_OR_THEORY);
		user1.setTheme(themesList.get(0));

		String expected = answerGenerator.generateAnswerForUser(user1);
		
		User user2 = new User("1"); // actual user
		user2.setGrade(9);
		user2.setState(State.CHOOSE_THEME);

		WrappedUpdate update = new WrappedUpdate("1", "Тема 1");
		String actual = handler.handleMessage(user2, update);

		assertEquals(expected, actual);
		assertEquals(user2.getState(), State.CHOOSE_TASK_OR_THEORY);
		assertEquals(user2.getTheme().getName(), "Тема 1");
	}
}
