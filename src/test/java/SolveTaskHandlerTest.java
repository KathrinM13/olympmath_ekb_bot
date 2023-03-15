package test.java;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import main.java.answer_generators.ChooseClassAnswerGenerator;
import main.java.answer_generators.ChooseTaskOrTheoryAnswerGenerator;
import main.java.answer_generators.ChooseThemeAnswerGenerator;
import main.java.answer_generators.HasAnswerAnswerGenerator;
import main.java.answer_generators.SolveTaskAnswerGenerator;
import main.java.handlers.ChoseeTaskOrTheoryHandler;
import main.java.handlers.SolveTaskHandler;
import main.java.models.AnswerGenerator;
import main.java.models.Generator;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.Task;
import main.java.models.TasksStorage;
import main.java.models.Theme;
import main.java.models.ThemesStorage;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class SolveTaskHandlerTest extends TestCase {
	private AnswerGenerator answerGenerator;
	private SolveTaskHandler handler;
	private ThemesStorage themes;
	private TasksStorage tasks;
	ArrayList<Theme> themesList;
	ArrayList<Task> tasksList;
	private List<Generator> answerGenerators;

	public void setUp() {
		themesList = new ArrayList<Theme>();
		themesList.add(new Theme("Тема 1", "Теория 1", 9));
		themesList.add(new Theme("Тема 2", "Теория 2", 8));
		themes = new ThemesStorage(themesList);
		
		tasksList = new ArrayList<Task>();
		tasksList.add(new Task("Тема 1", "Задача 1", "Решение 1", 1));
		tasks = new TasksStorage(tasksList);

		answerGenerators = List.of(				
				new ChooseClassAnswerGenerator(),
				new ChooseThemeAnswerGenerator(themes),
				new ChooseTaskOrTheoryAnswerGenerator(),
				new SolveTaskAnswerGenerator(),
				new HasAnswerAnswerGenerator());
		answerGenerator = new AnswerGenerator(answerGenerators);
		handler = new SolveTaskHandler(answerGenerator);
	}
	
	public void testWrongOption() {
		User user = new User("1");
		user.setGrade(9);
		user.setTheme(themes.getById(0));
		user.setTask(tasks.getById(0));
		user.setState(State.SOLVE_TASK);

		String expected = answerGenerator.generateAnswerForMistake(Mistake.WRONG_OPTION).concat(answerGenerator.generateAnswerForUser(user));
		WrappedUpdate update = new WrappedUpdate("1", "1234");
		String actual = handler.handleMessage(user, update);
		assertEquals(expected, actual);
		assertEquals(user.getState(), State.SOLVE_TASK);
	}
	
	public void testDecisionOption() {
		User user1 = new User("1"); //expected user
		user1.setGrade(9);
		user1.setTheme(themes.getById(0));
		user1.setTask(tasks.getById(0));
		user1.setState(State.SOLVE_TASK);
		String expected = user1.getTask().getDecision().concat("\n");
		user1.rememberTask();
		user1.resetTheme();
		expected = expected.concat(answerGenerator.generateAnswerForUser(user1));
		
		User user2 = new User("1"); //actual user
		user2.setGrade(9);
		user2.setTheme(themes.getById(0));
		user2.setTask(tasks.getById(0));
		user2.setState(State.SOLVE_TASK);
		WrappedUpdate update = new WrappedUpdate("1", "решение");
		String actual = handler.handleMessage(user2, update);
		
		assertEquals(expected, actual);
		assertEquals(user2.getState(), State.CHOOSE_THEME);
		assertEquals(user2.getSolvedTasks().get(0).getStatement(), "Задача 1");
	}
	
	public void testAnswerOption() {
		User user1 = new User("1"); //expected user
		user1.setGrade(9);
		user1.setTheme(themes.getById(0));
		user1.setTask(tasks.getById(0));
		user1.setState(State.HAS_ANSWER);
		String expected = answerGenerator.generateAnswerForUser(user1);
		
		User user2 = new User("1"); //actual user
		user2.setGrade(9);
		user2.setTheme(themes.getById(0));
		user2.setTask(tasks.getById(0));
		user2.setState(State.SOLVE_TASK);
		WrappedUpdate update = new WrappedUpdate("1", "ответ");
		String actual = handler.handleMessage(user2, update);
		
		assertEquals(expected, actual);
		assertEquals(user2.getState(), State.HAS_ANSWER);
		assertEquals(user2.getSolvedTasks().size(), 0);
	}
}
