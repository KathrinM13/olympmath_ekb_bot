package main.java.models;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import main.java.wrappers.WrappedUpdate;

public class UpdateReceiver {
	
	private List<Handler> handlers;
	private ConcurrentHashMap<String, User> chatIdToUser;
	private AnswerGenerator answerGenerator;
	
	public UpdateReceiver(List<Handler> handlers, AnswerGenerator answerGenerator) {
		this.handlers = handlers;
		this.answerGenerator = answerGenerator;
		chatIdToUser = new ConcurrentHashMap<>();
	}
	
	public String handle(WrappedUpdate update) {
		String chatId = update.getChatId();

		if (!chatIdToUser.containsKey(chatId)) {
			chatIdToUser.put(chatId, new User(chatId));
		}
		User user = chatIdToUser.get(chatId);
		
		Handler handler = getHandlerByState(user.getState());
		if(handler.isStantardCommand(update.getMessage())) {
			return handler.handleStantardCommand(user, update, answerGenerator);
		}

		return getHandlerByState(user.getState()).handleMessage(user, update);
	}
	
	private Handler getHandlerByState(State state) {
		return handlers.stream()
				.filter(handler -> handler.isHandled(state))
				.findAny()
				.orElseThrow(UnsupportedOperationException::new);
	}

}
