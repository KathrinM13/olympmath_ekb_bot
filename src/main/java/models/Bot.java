package main.java.models;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import main.java.wrappers.WrappedUpdate;

public class Bot extends TelegramLongPollingBot {
	UpdateReceiver updateReceiver;
	
	private final String token;
	
	public Bot(String token, UpdateReceiver updateReceiver) {		
		this.updateReceiver = updateReceiver;
		this.token = token;
	}

	@Override
	public void onUpdateReceived(Update update) {
		try {
			WrappedUpdate wrappedUpdate = new WrappedUpdate(update);
			String TextOfanswer = updateReceiver.handle(wrappedUpdate);
			
			SendMessage answer = new SendMessage();
			answer.setChatId(wrappedUpdate.getChatId());
			answer.setText(TextOfanswer);
	        execute(answer);
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
		return token;
	}

}