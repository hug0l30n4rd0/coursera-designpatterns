package br.edu.hls.service.observer;

import br.edu.hls.model.Achievement;

/**
 * Interface observavel que contera a colecao de observadores
 * @author Hugo Silva
 *
 */
public interface AchievementObservable {
	void addObservable(AchievementObserver observer);
	void removeObservable(AchievementObserver observer);
	void notify(Achievement achievement, String usuario);
}
