package test.java;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import main.java.answer_generators.ChooseClassAnswerGenerator;
import main.java.answer_generators.ChooseTaskOrTheoryAnswerGenerator;
import main.java.answer_generators.ChooseThemeAnswerGenerator;
import main.java.answer_generators.SolveTaskAnswerGenerator;
import main.java.handlers.ChooseThemeHandler;
import main.java.handlers.ChoseeTaskOrTheoryHandler;
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

public class ChooseTaskOrTheoryHandlerTest extends TestCase {
	private AnswerGenerator answerGenerator;
	private ChoseeTaskOrTheoryHandler handler;
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
				new SolveTaskAnswerGenerator());
		answerGenerator = new AnswerGenerator(answerGenerators);
		handler = new ChoseeTaskOrTheoryHandler(tasks, answerGenerator);
	}
	
	public void testWrongData() {
		User user = new User("1");
		user.setState(State.CHOOSE_TASK_OR_THEORY);

		String expected = answerGenerator.generateAnswerForMistake(Mistake.WRONG_DATA).concat(answerGenerator.generateAnswerForUser(user));
		WrappedUpdate update = new WrappedUpdate("1", "abc");
		String actual = handler.handleMessage(user, update);
		assertEquals(expected, actual);
		assertEquals(user.getState(), State.CHOOSE_TASK_OR_THEORY);
	}
	public void testWrongOption() {
		User user = new User("1");
		user.setState(State.CHOOSE_TASK_OR_THEORY);

		String expected = answerGenerator.generateAnswerForMistake(Mistake.WRONG_OPTION).concat(answerGenerator.generateAnswerForUser(user));
		WrappedUpdate update = new WrappedUpdate("1", "1234");
		String actual = handler.handleMessage(user, update);
		assertEquals(expected, actual);
		assertEquals(user.getState(), State.CHOOSE_TASK_OR_THEORY);
	}
	public void testTheoryOption() {
		User user = new User("1");
		user.setGrade(9);
		user.setTheme(themes.getById(0));
		user.setState(State.CHOOSE_TASK_OR_THEORY);

		String expected = user.getTheme().getTheory().concat("\n").concat(answerGenerator.generateAnswerForUser(user));
		WrappedUpdate update = new WrappedUpdate("1", "1");
		String actual = handler.handleMessage(user, update);
		assertEquals(expected, actual);
		assertEquals(user.getState(), State.CHOOSE_TASK_OR_THEORY);
	}
	public void testTaskOptionNonEmptyTasksList() {
		User user1 = new User("1"); //expected user
		user1.setGrade(9);
		user1.setTheme(themes.getById(0));
		user1.setTask(tasks.getById(0));
		user1.setState(State.SOLVE_TASK);
		
		User user2 = new User("1"); //actual user
		user2.setGrade(9);
		user2.setTheme(themes.getById(0));
		user2.setState(State.CHOOSE_TASK_OR_THEORY);

		String expected = answerGenerator.generateAnswerForUser(user1);
		WrappedUpdate update = new WrappedUpdate("1", "2");
		String actual = handler.handleMessage(user2, update);
		assertEquals(expected, actual);
		assertEquals(user2.getState(), State.SOLVE_TASK);
		assertEquals(user2.getTask().getStatement(), "Задача 1");
	}
	public void testTaskOptionEmptyTasksList() {
		User user1 = new User("1"); //expected user
		user1.setGrade(9);
		user1.setState(State.CHOOSE_THEME);
		
		User user2 = new User("1"); //actual user
		user2.setGrade(9);
		user2.setTheme(themes.getById(0));
		user2.setState(State.CHOOSE_TASK_OR_THEORY);
		user2.setTask(tasks.getById(0)); // имитируем то, что пользователь до этого уже решил задачу
		user2.rememberTask();
		user2.setTask(null);

		String expected = answerGenerator.generateAnswerForMistake(Mistake.EMPTY_TASKS_LIST).concat(answerGenerator.generateAnswerForUser(user1));
		WrappedUpdate update = new WrappedUpdate("1", "2");
		String actual = handler.handleMessage(user2, update);
		assertEquals(expected, actual);
		assertEquals(user2.getState(), State.CHOOSE_THEME);
	}
}
