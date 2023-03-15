package test.java;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import main.java.answer_generators.ChooseClassAnswerGenerator;
import main.java.answer_generators.ChooseTaskOrTheoryAnswerGenerator;
import main.java.answer_generators.ChooseThemeAnswerGenerator;
import main.java.answer_generators.HasAnswerAnswerGenerator;
import main.java.answer_generators.SolveTaskAnswerGenerator;
import main.java.handlers.HasAnswerHandler;
import main.java.handlers.SolveTaskHandler;
import main.java.models.AnswerGenerator;
import main.java.models.Generator;
import main.java.models.Task;
import main.java.models.TasksStorage;
import main.java.models.Theme;
import main.java.models.ThemesStorage;

public class HasAnswerHandlerTest extends TestCase {
	private AnswerGenerator answerGenerator;
	private HasAnswerHandler handler;
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
		handler = new HasAnswerHandler(answerGenerator);
	}
}
