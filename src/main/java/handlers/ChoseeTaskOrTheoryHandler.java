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

	Storage<Task> tasks;
	AnswerGenerator answerGenerator;
	
	public ChoseeTaskOrTheoryHandler(Storage<Task> tasks, AnswerGenerator answerGenerator) {
		this.tasks = tasks;
		this.answerGenerator = answerGenerator;
	}
	
	@Override
	public State handledState() {
		return State.CHOOSE_TASK_OR_THEORY;
	}

	@Override
	public String handleMessage(User user, WrappedUpdate update) {
		if(isStantardCommand(update.getMessage())) {
			return handleStantardCommand(user, update, answerGenerator);
		}
		
		int option;

		try {
			option = Integer.parseInt(update.getMessage());
			if(option == 1) {
				return user.getTheme().getTheory().concat("\n").concat(answerGenerator.generateAnswerForUser(user));
			}
			else if(option == 2) {
				user.setState(State.SOLVE_TASK);
				ArrayList<Task> filteredTasks = getTasksFilteredByTheme(user.getTheme().getName());
				Task task = filteredTasks.get(ThreadLocalRandom.current().nextInt(0, filteredTasks.size()));
				user.setTask(task);
				return answerGenerator.generateAnswerForUser(user);
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

}
