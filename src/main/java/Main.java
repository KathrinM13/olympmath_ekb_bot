package main.java;

import main.java.models.Bot;
import main.java.models.Theme;
import main.java.models.ThemesStorage;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.*;

public class Main {

	public static void main(String args[]) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot("6046806408:AAFe78dXZUz-YjZ8BvBDlsLZ3GbXcsvf9dg"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
	
}
