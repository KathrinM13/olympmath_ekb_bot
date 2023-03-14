package main.java.models;

import java.util.ArrayList;

public class TasksStorage implements Storage<Task> {

	ArrayList<Task> tasks;
	
	public TasksStorage() {
		tasks = new ArrayList<Task>();
	}
	
	@Override
	public Task getById(int id) {
		try {
			return tasks.get(id);
		}
		catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public int getSize() {
		return tasks.size();
	}

	@Override
	public ArrayList<Task> getAll() {
		return tasks;
	}

}
