package br.edu.hls.persist;

import java.util.List;

import br.edu.hls.model.Achievement;
import br.edu.hls.service.observer.AchievementObservable;

/**
 * Interface de um storage
 * @author Hugo Silva
 *
 */
public interface AchievementStorage extends AchievementObservable {
	
	void addAchievement(String user, Achievement a);
	
	List<Achievement> getAchievements(String user);
	
	Achievement getAchievement(String user, String achievementName);
}
