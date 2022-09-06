package br.edu.hls.persist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.hls.model.Achievement;
import br.edu.hls.service.observer.AchievementObserver;


/**
 * implementação de um Storage em memoria
 * @author Hugo Silva
 *
 */
public class MemoryAchievementStorage implements AchievementStorage {
	
	/*
	 * Mapeia as conquistas de cada usuario [key] numa lista [value] 
	 */
	private Map<String, List<Achievement>> mapaAchievements;
	
	/*
	 * Lista de objetos que serão notificados 
	 */
	private List<AchievementObserver> listaObservers;
	
	public MemoryAchievementStorage() {
		mapaAchievements = new HashMap<String, List<Achievement>>();
		listaObservers = new ArrayList<AchievementObserver>();
	}
	
	public void addAchievement(String user, Achievement a) {
		List<Achievement> achievementsDoUsuario;
		if (mapaAchievements.containsKey(user)) {
			achievementsDoUsuario = mapaAchievements.get(user);
		} else {
			achievementsDoUsuario = new ArrayList<Achievement>();
		}
		
		a.adiciona(achievementsDoUsuario);
		mapaAchievements.put(user, achievementsDoUsuario);
		
		this.notify(a, user);

	}

	public List<Achievement> getAchievements(String user) {
		if (mapaAchievements.containsKey(user)) {
			return mapaAchievements.get(user);
		}
		return null;
	}

	public Achievement getAchievement(String user, String achievementName) {
		if (mapaAchievements.containsKey(user)) {
			return mapaAchievements.get(user).stream().filter((achv) -> achv.getName().equals(achievementName)).findFirst().get();
		}
		return null;
	}

	public void addObservable(AchievementObserver observer) {
		listaObservers.add(observer);
	}

	public void removeObservable(AchievementObserver observer) {
		listaObservers.remove(observer);
	}

	public void notify(Achievement achievement, String usuario) {
		listaObservers.forEach((o) -> o.achievementUpdate(usuario, achievement));
	}

}
