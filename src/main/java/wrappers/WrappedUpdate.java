package main.java.wrappers;

import org.telegram.telegrambots.meta.api.objects.Update;

public class WrappedUpdate {
	
	private String chatId;
	private String message;
	
	public WrappedUpdate(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			chatId = update.getMessage().getChatId().toString();
			message = update.getMessage().getText();
		}
	}
	
	public WrappedUpdate(String chatId, String message) {
		this.setChatId(chatId);
		this.setMessage(message);
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
