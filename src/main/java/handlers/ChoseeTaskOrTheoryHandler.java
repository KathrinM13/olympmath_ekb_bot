package main.java.handlers;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import main.java.models.AnswerGenerator;
import main.java.models.Handler;
import main.java.models.Mistake;
import main.java.models.State;
import main.java.models.Storage;
import main.java.models.Task;
import main.java.models.TasksStorage;
import main.java.models.Theme;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

public class ChoseeTaskOrTheoryHandler implements Handler {
	
	public static final int THEORY_OPTION = 1;
	public static final int TASK_OPTION = 2;

	Storage<Task> tasks;
	AnswerGenerator answerGenerator;
	
	public ChoseeTaskOrTheoryHandler(Storage<Task> tasks, AnswerGenerator answerGenerator) {
		this.tasks = tasks;
		this.answerGenerator = answerGenerator;
	}
	
	@Override
	public boolean isHandled(State state) {
		return state == State.CHOOSE_TASK_OR_THEORY;
	}

	@Override
	public String handleMessage(User user, WrappedUpdate update) {
		int option;

		try {
			option = Integer.parseInt(update.getMessage());
			if(option == THEORY_OPTION) {
				return user.getTheme().getTheory().concat("\n").concat(answerGenerator.generateAnswerForUser(user));
			}
			else if(option == TASK_OPTION) {
				ArrayList<Task> filteredTasks = getTasksFilteredByTheme(user.getTheme().getName());
				for(int i = 0; i < user.getSolvedTasks().size(); i++) {
					filteredTasks = this.getTasksFilteredByStatement(user.getSolvedTasks().get(i).getStatement(), filteredTasks);
				}
				if(filteredTasks.size() > 0) {
					user.setState(State.SOLVE_TASK);
					Task task = filteredTasks.get(ThreadLocalRandom.current().nextInt(0, filteredTasks.size()));
					user.setTask(task);
					return answerGenerator.generateAnswerForUser(user);
				}
				else {
					user.setState(State.CHOOSE_THEME);
					user.setTheme(null);
					return answerGenerator.generateAnswerForMistake(Mistake.EMPTY_TASKS_LIST).concat(answerGenerator.generateAnswerForUser(user));
				}
			}
			else {
				return answerGenerator.generateAnswerForMistake(Mistake.WRONG_OPTION).concat(answerGenerator.generateAnswerForUser(user));
			}
		}
		catch (NumberFormatException e) {
			return answerGenerator.generateAnswerForMistake(Mistake.WRONG_DATA).concat(answerGenerator.generateAnswerForUser(user));
		}
	}
	
	private ArrayList<Task> getTasksFilteredByTheme(String themeName) {
		ArrayList<Task> filteredTasks = new ArrayList<>();
		for(int i = 0; i < tasks.getSize(); i++) {
			Task t = tasks.getById(i);
			if(t.getTheme().equalsIgnoreCase(themeName))
				filteredTasks.add(t);
		}
		return filteredTasks;
	}
	
	private ArrayList<Task> getTasksFilteredByStatement(String statement, ArrayList<Task> tasksForFiltration) {
		ArrayList<Task> filteredTasks = new ArrayList<>();
		for(int i = 0; i < tasksForFiltration.size(); i++) {
			Task t = tasksForFiltration.get(i);
			if(!t.getStatement().equalsIgnoreCase(statement))
				filteredTasks.add(t);
		}
		return filteredTasks;
	}

}
