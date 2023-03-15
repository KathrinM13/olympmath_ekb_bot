package test.java;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import main.java.answer_generators.ChooseClassAnswerGenerator;
import main.java.answer_generators.ChooseTaskOrTheoryAnswerGenerator;
import main.java.answer_generators.ChooseThemeAnswerGenerator;
import main.java.answer_generators.SolveTaskAnswerGenerator;
import main.java.handlers.ChooseClassHandler;
import main.java.models.AnswerGenerator;
import main.java.models.Generator;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.TasksStorage;
import main.java.models.Theme;
import main.java.models.ThemesStorage;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class ChooseClassHandlerTest extends TestCase {
	
	private AnswerGenerator answerGenerator;
	private List<Generator> answerGenerators;
	private ChooseClassHandler handler;
	private ThemesStorage themes;
	ArrayList<Theme> themesList;
	
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
		handler = new ChooseClassHandler(answerGenerator);
	}
	
	public void testWrongData() {
		User user = new User("1");
		String expected = answerGenerator.generateAnswerForMistake(Mistake.WRONG_DATA).concat(answerGenerator.generateAnswerForUser(user));
		WrappedUpdate update = new WrappedUpdate("1", "abc");
		String actual = handler.handleMessage(user, update);
		assertEquals(expected, actual);
		assertEquals(user.getState(), State.CHOOSE_CLASS);
	}
	
	public void testWrongClass() {
		User user = new User("1");
		String expected = answerGenerator.generateAnswerForMistake(Mistake.WRONG_CLASS).concat(answerGenerator.generateAnswerForUser(user));
		WrappedUpdate update = new WrappedUpdate("1", "6");
		String actual = handler.handleMessage(user, update);
		assertEquals(expected, actual);
		assertEquals(user.getState(), State.CHOOSE_CLASS);
	}
	
	public void testCorrectClass() {
		User user1 = new User("1");
		user1.setState(State.CHOOSE_THEME);
		user1.setGrade(9);
		String expected = answerGenerator.generateAnswerForUser(user1);

		User user2 = new User("1");
		WrappedUpdate update = new WrappedUpdate("1", "9");
		String actual = handler.handleMessage(user2, update);

		assertEquals(expected, actual);
		assertEquals(user2.getState(), State.CHOOSE_THEME);
		assertEquals(user2.getGrade(), 9);
	}

}
