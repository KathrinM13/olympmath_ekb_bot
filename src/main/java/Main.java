package main.java;

import main.java.answer_generators.ChooseClassAnswerGenerator;
import main.java.answer_generators.ChooseTaskOrTheoryAnswerGenerator;
import main.java.answer_generators.ChooseThemeAnswerGenerator;
import main.java.answer_generators.HasAnswerAnswerGenerator;
import main.java.answer_generators.SolveTaskAnswerGenerator;
import main.java.handlers.ChooseClassHandler;
import main.java.handlers.ChooseThemeHandler;
import main.java.handlers.ChoseeTaskOrTheoryHandler;
import main.java.handlers.HasAnswerHandler;
import main.java.handlers.SolveTaskHandler;
import main.java.models.AnswerGenerator;
import main.java.models.Bot;
import main.java.models.Generator;
import main.java.models.Handler;
import main.java.models.TasksStorage;
import main.java.models.Theme;
import main.java.models.ThemesStorage;
import main.java.models.UpdateReceiver;
import main.java.models.User;
import main.java.wrappers.WrappedUpdate;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import com.google.gson.*;

public class Main {
	
	static public String THEMESDB_PATH = "src/main/java/models/Themes.json";
	static public String TASKSDB_PATH = "src/main/java/models/Tasks.json";
	static public String TOKEN = "***";

	public static void main(String args[]) {
		ThemesStorage themes = new ThemesStorage();
		TasksStorage tasks = new TasksStorage();
		AnswerGenerator answerGenerator;
		List<Handler> handlers;
		List<Generator> answerGenerators;
		UpdateReceiver updateReceiver;
		String jsonThemes = new String();
		String jsonTasks = new String();
		Gson g = new Gson();

		try {
			jsonThemes = new String(Files.readAllBytes(Paths.get(THEMESDB_PATH)));
			jsonTasks = new String(Files.readAllBytes(Paths.get(TASKSDB_PATH)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		themes = g.fromJson(jsonThemes, ThemesStorage.class);
		tasks = g.fromJson(jsonTasks, TasksStorage.class);
		answerGenerators = List.of(
				new ChooseClassAnswerGenerator(),
				new ChooseThemeAnswerGenerator(themes),
				new ChooseTaskOrTheoryAnswerGenerator(),
				new SolveTaskAnswerGenerator(),
				new HasAnswerAnswerGenerator()
		);
		answerGenerator = new AnswerGenerator(answerGenerators);
		handlers = List.of(
				new ChooseClassHandler(answerGenerator),
				new ChooseThemeHandler(themes, answerGenerator),
				new ChoseeTaskOrTheoryHandler(tasks, answerGenerator),
				new SolveTaskHandler(answerGenerator),
				new HasAnswerHandler(answerGenerator)
		);
		updateReceiver = new UpdateReceiver(handlers, answerGenerator);

        /*try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot(TOKEN, updateReceiver));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
		
		Scanner in = new Scanner(System.in);
		System.out.print(" ");
		while(true) {
			String question = in.nextLine();
			WrappedUpdate update = new WrappedUpdate("1", question);
			System.out.print(updateReceiver.handle(update));
		}
	}
	
}
