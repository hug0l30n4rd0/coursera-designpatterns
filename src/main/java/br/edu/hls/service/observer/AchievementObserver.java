package br.edu.hls.service.observer;

import br.edu.hls.model.Achievement;

/**
 * Interface de um objeto observador
 * @author Hugo Silva
 *
 */
public interface AchievementObserver {
	void achievementUpdate(String user, Achievement a);
}
