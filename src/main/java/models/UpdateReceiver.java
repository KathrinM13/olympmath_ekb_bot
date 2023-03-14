package main.java.models;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import main.java.wrappers.WrappedUpdate;

public class UpdateReceiver {
	
	private List<Handler> handlers;
	private ConcurrentHashMap<String, User> chatIdToUser;
	
	public UpdateReceiver(List<Handler> handlers) {
		this.handlers = handlers;
		chatIdToUser = new ConcurrentHashMap<>();
	}
	
	public String handle(WrappedUpdate update) {
		String chatId = update.getChatId();

		if (!chatIdToUser.containsKey(chatId)) {
			chatIdToUser.put(chatId, new User(chatId));
		}
		User user = chatIdToUser.get(chatId);

		return getHandlerByState(user.getState()).handleMessage(user, update);
	}
	
	private Handler getHandlerByState(State state) {
		return handlers.stream()
				.filter(handler -> handler.handledState() != null)
				.filter(handler -> handler.handledState().equals(state))
				.findAny()
				.orElseThrow(UnsupportedOperationException::new);
	}

}
