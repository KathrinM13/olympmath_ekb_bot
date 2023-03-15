package main.java.answer_generators;

import main.java.models.Generator;
import main.java.models.State;
import main.java.models.Storage;
import main.java.models.Theme;
import main.java.models.User;

public class ChooseThemeAnswerGenerator implements Generator {
	
	private Storage<Theme> themes;
	
	public  ChooseThemeAnswerGenerator(Storage<Theme> themes) {
		this.themes = themes;
	}

	@Override
	public boolean isHandled(State state) {
		return state == State.CHOOSE_THEME;
	}

	@Override
	public String generateAnswerForUser(User user) {
		return generateThemesMenu(user.getGrade());
	}
	
	private String generateThemesMenu(int grade) {
		String listOfThemes = "";
		for(Theme theme : themes.getAll().stream().filter(theme -> theme.getGrade() == grade).toList()) {
			listOfThemes = listOfThemes.concat(theme.getName()).concat("\n");
		}
		return "Теперь выбери тему:\n".concat(listOfThemes);
	}

}
