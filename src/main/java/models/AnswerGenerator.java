package main.java.models;

public class AnswerGenerator {

	public static final String CHOOSE_CLASS_MESSAGE = "У меня есть задачи для 8 и 9 классов. В каком классе ты учишься?\n";
	public static final String CHOOSE_TASK_OR_THEORY_MESSAGE = "Выбери, чем ты хочешь заняться:\n 1 вспомнить теорию \n 2 решать задачу\n";
	public static final String SOLVE_TASK_MESSAGE = "\n Введи \"решение\", если хочешь получить ссылку на решение\n";
	public static final String WRONG_CLASS_MESSAGE = "У меня нет задач для такого класса\n";
	public static final String WRONG_THEME_MESSAGE = "У меня нет задач по такой теме\n";
	public static final String WRONG_DATA_MESSAGE = "Я не умею обрабатывать такой запрос, попробуй еще раз\n";
	public static final String WRONG_OPTION_MESSAGE = "В списке нет такого варианта, попробуй еще раз\n";
	public static final String START_MESSAGE = "Привет! Я olympmathbot.\nЯ помогу тебе подготовиться к олимпиадам по математике, подобрав для тебя разные задачи.\n" +
											   "ЧТобы получить подробную инструкцию по работе со мной введи /help.\n";
	public static final String HELP_MESSAGE = "Базовые команды:\n" +
											   "/help - вывод этой справки\n" +
											   "/reset - начать диалога с начала\n" +
											   "/start - начало работы.\n";
	
    public static final String RESET = "/reset";
    public static final String HELP = "/help";
    public static final String START = "/start";
	
	private Storage<Theme> themes;
	private Storage<Task> tasks;
	
	public AnswerGenerator(Storage<Theme> themes, Storage<Task> tasks) {
		this.themes = themes;
		this.tasks = tasks;
	}
	
	public String generateAnswerForUser(User user) {
		switch(user.getState()) {
			case CHOOSE_CLASS:
				return CHOOSE_CLASS_MESSAGE;
			case CHOOSE_THEME:
				return generateThemesMenu(user.getGrade());
			case CHOOSE_TASK_OR_THEORY:
				return CHOOSE_TASK_OR_THEORY_MESSAGE;
			case SOLVE_TASK:
				return user.getTask().getStatement().concat(SOLVE_TASK_MESSAGE);
		}
		return "";
	}
	
	private String generateThemesMenu(int grade) {
		String listOfThemes = "";
		for(Theme theme : themes.getAll().stream().filter(theme -> theme.getGrade() == grade).toList()) {
			listOfThemes = listOfThemes.concat(theme.getName()).concat("\n");
		}
		return "Теперь выбери тему:\n".concat(listOfThemes);
	}
	
	public String generateAnswerForMistake(Mistake mistake) {
		switch (mistake) {
			case WRONG_CLASS:
				return WRONG_CLASS_MESSAGE;
			case WRONG_THEME:
				return WRONG_THEME_MESSAGE;
			case WRONG_DATA:
				return WRONG_DATA_MESSAGE;
			case WRONG_OPTION:
				return WRONG_OPTION_MESSAGE;
		}
		
		return null;
	}
	
	public String generateAnswerForStantardCommand(String command, User user) {
		switch (command) {
			case RESET:
				return generateAnswerForUser(user);
			case START:
				return START_MESSAGE.concat(generateAnswerForUser(user));
			case HELP:
				return HELP_MESSAGE.concat(generateAnswerForUser(user));
		}
		
		return "";
	}
}
