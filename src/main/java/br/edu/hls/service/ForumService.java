package br.edu.hls.service;

/**
 * O exercicio dizia que nao precisa criar a interface mas para a criacao do Proxy sera necessario
 * 
 * @author Hugo Silva
 *
 */
public interface ForumService {
	void addTopic(String user, String topic);
	void addComment(String user, String topic, String comment);
	void likeTopic(String user, String topic, String topicUser);
	void likeComment(String user, String topic, String comment, String commentUser);
}
