package br.edu.hls.service.observer;

import br.edu.hls.model.Achievement;
import br.edu.hls.model.Badge;
import br.edu.hls.model.Points;
import br.edu.hls.persist.AchievementStorageFactory;
import br.edu.hls.util.Util;
import lombok.NoArgsConstructor;

/**
 * Observer de achievements Creation e que ao atingir 100 pts adiciona a Badge "Inventor"
 * @author Hugo Silva
 *
 */
@NoArgsConstructor
public class CreationObserver implements AchievementObserver {
	
	@Override
	public void achievementUpdate(String user, Achievement a) {
		if (a instanceof Points) {
			Points points = (Points) a;
			if (points.getName().equals(Util.CREATION) && points.getValor() >= 100) {
				AchievementStorageFactory.getAchievementStorage().addAchievement(user, new Badge(Util.INVENTOR));
			}
		}
	}
	

}
