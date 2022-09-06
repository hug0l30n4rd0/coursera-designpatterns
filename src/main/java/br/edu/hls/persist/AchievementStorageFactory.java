package br.edu.hls.persist;

/**
 * Factory para criacao de storages que utiliza singleton
 * @author Hugo Silva
 *
 */
public class AchievementStorageFactory {
	
	private static AchievementStorage achievementStorage;
	
	private AchievementStorageFactory() {
		super();
	}
	
	public static AchievementStorage getAchievementStorage() {
		if (achievementStorage == null) {
			achievementStorage = new MemoryAchievementStorage();
		}
			
		return achievementStorage;
	}
	
	public static void setAchievementStorage(AchievementStorage a) {
		AchievementStorageFactory.achievementStorage = a;
	}
}
