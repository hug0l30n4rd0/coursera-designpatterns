package br.edu.hls.service;

import br.edu.hls.model.Badge;
import br.edu.hls.model.Points;
import br.edu.hls.persist.AchievementStorageFactory;
import br.edu.hls.util.Util;

/**
 * Proxy para objetos ForumService
 * @author Hugo Silva
 *
 */
public class ForumServiceGamificationProxy implements ForumService {
	
	//O real objeto ForumService
	private ForumService realForumService;
	
	public ForumServiceGamificationProxy(ForumService service) {
		this.realForumService = service;
	}
	
	public void addTopic(String user, String topic) {
		realForumService.addTopic(user, topic);
		AchievementStorageFactory.getAchievementStorage().addAchievement(user, new Points(Util.CREATION, 5));
		AchievementStorageFactory.getAchievementStorage().addAchievement(user, new Badge(Util.I_CAN_TALK));
	}
	public void addComment(String user, String topic, String comment) {
		realForumService.addComment(user, topic, comment);
		AchievementStorageFactory.getAchievementStorage().addAchievement(user, new Points(Util.PARTICIPATION, 3));
		AchievementStorageFactory.getAchievementStorage().addAchievement(user, new Badge(Util.LET_ME_ADD));
	}
	public void likeTopic(String user, String topic, String topicUser) {
		realForumService.likeTopic(user, topic, topicUser);
		AchievementStorageFactory.getAchievementStorage().addAchievement(user, new Points(Util.CREATION, 1));
	}
	public void likeComment(String user, String topic, String comment, String commentUser) {
		realForumService.likeComment(user, topic, comment, commentUser );
		AchievementStorageFactory.getAchievementStorage().addAchievement(user, new Points(Util.PARTICIPATION, 1));
	}
}
