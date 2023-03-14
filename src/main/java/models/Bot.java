package main.java.models;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import main.java.handlers.ChooseClassHandler;
import main.java.handlers.ChooseThemeHandler;
import main.java.handlers.ChoseeTaskOrTheoryHandler;
import main.java.handlers.SolveTaskHandler;
import main.java.wrappers.WrappedUpdate;

import com.google.gson.*;

public class Bot extends TelegramLongPollingBot {
	
	List<Handler> handlers;
	ThemesStorage themes;
	TasksStorage tasks;
	AnswerGenerator answerGenerator;
	UpdateReceiver updateReceiver;
	
	private final String token;
	
	public Bot(String token) {
		try {
			String jsonThemes = new String(Files.readAllBytes(Paths.get("src/main/java/models/Themes.json")));
			String jsonTasks = new String(Files.readAllBytes(Paths.get("src/main/java/models/Tasks.json")));
			Gson g = new Gson();
			
			themes = g.fromJson(jsonThemes, ThemesStorage.class);
			tasks = g.fromJson(jsonTasks, TasksStorage.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		answerGenerator = new AnswerGenerator(themes, tasks);
		handlers = List.of(
				new ChooseClassHandler(answerGenerator),
				new ChooseThemeHandler(themes, answerGenerator),
				new ChoseeTaskOrTheoryHandler(tasks, answerGenerator),
				new SolveTaskHandler(answerGenerator)
		);
		
		updateReceiver = new UpdateReceiver(handlers);
		this.token = token;
	}

	@Override
	public void onUpdateReceived(Update update) {
		try {
			WrappedUpdate wrappedUpdate = new WrappedUpdate(update);
			String answer = updateReceiver.handle(wrappedUpdate);
			
			SendMessage outMess = new SendMessage();
			outMess.setChatId(wrappedUpdate.getChatId());
	        outMess.setText(answer);
	        execute(outMess);
		} catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

	@Override
	public String getBotUsername() {
		return "olympmath_ekb_bot";
	}

	@Override
	public String getBotToken() {
		return "6046806408:AAFe78dXZUz-YjZ8BvBDlsLZ3GbXcsvf9dg";
	}

}