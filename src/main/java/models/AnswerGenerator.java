package main.java.models;

import java.util.HashMap;
import java.util.List;

public class AnswerGenerator {
	private static final String WRONG_CLASS_MESSAGE = "У меня нет задач для такого класса\n";
	private static final String WRONG_THEME_MESSAGE = "У меня нет задач по такой теме\n";
	private static final String WRONG_DATA_MESSAGE = "Я не умею обрабатывать такой запрос, попробуй еще раз\n";
	private static final String WRONG_OPTION_MESSAGE = "В списке нет такого варианта, попробуй еще раз\n";
	private static final String EMPTY_TASKS_LIST_MESSAGE = "У меня больше нет для тебя задач по этой теме\n";
	private static final String WRONG_ANSWER_MESSAGE = "Это неверный ответ, попробуй еще раз\n";

	private static final String START_MESSAGE = "Привет! Я olympmathbot.\nЯ помогу тебе подготовиться к олимпиадам по математике, подобрав для тебя разные задачи.\n" +
											   "ЧТобы получить подробную инструкцию по работе со мной введи /help.\n";
	private static final String HELP_MESSAGE = "Базовые команды:\n" +
											   "/help - вывод этой справки\n" +
											   "/reset - начать диалога с начала\n" +
											   "/start - начало работы.\n";
	private static final String RESET_MESSAGE = "";
	
	private HashMap<Mistake, String> mistakeCodeToAnswer; 
	private HashMap<String, String> standardCommandForAnswer;
	
	private List<Generator> answerGenerators;
	
	public AnswerGenerator(List<Generator> answerGenerators) {
		this.answerGenerators = answerGenerators;
		
		mistakeCodeToAnswer = new HashMap<Mistake, String>();
		mistakeCodeToAnswer.put(Mistake.WRONG_CLASS, WRONG_CLASS_MESSAGE);
		mistakeCodeToAnswer.put(Mistake.WRONG_THEME, WRONG_THEME_MESSAGE);
		mistakeCodeToAnswer.put(Mistake.WRONG_DATA, WRONG_DATA_MESSAGE);
		mistakeCodeToAnswer.put(Mistake.WRONG_OPTION, WRONG_OPTION_MESSAGE);
		mistakeCodeToAnswer.put(Mistake.EMPTY_TASKS_LIST, EMPTY_TASKS_LIST_MESSAGE);
		mistakeCodeToAnswer.put(Mistake.WRONG_ANSWER, WRONG_ANSWER_MESSAGE);
		
		standardCommandForAnswer = new HashMap<String, String>();
		standardCommandForAnswer.put(Handler.RESET, RESET_MESSAGE);
		standardCommandForAnswer.put(Handler.START, START_MESSAGE);
		standardCommandForAnswer.put(Handler.HELP, HELP_MESSAGE);
	}
	
	public String generateAnswerForUser(User user) {
		Generator generator = answerGenerators.stream()
				.filter(gen -> gen.isHandled(user.getState()))
				.findAny()
				.orElseThrow(UnsupportedOperationException::new);
		return generator.generateAnswerForUser(user);
	}
	
	public String generateAnswerForMistake(Mistake mistake) {
		return mistakeCodeToAnswer.get(mistake);
	}
	
	public String generateAnswerForStantardCommand(String command, User user) {
		return standardCommandForAnswer.get(command).concat(generateAnswerForUser(user));
	}
}
