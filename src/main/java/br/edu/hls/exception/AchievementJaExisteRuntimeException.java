package br.edu.hls.exception;

/**
 * N�o deveria ser RuntimeException mas por quest�es de simplicidade vou manter assim
 * @author Hugo Silva
 *
 */
public class AchievementJaExisteRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8763735607456115675L;

	public AchievementJaExisteRuntimeException(String msg) {
		super(msg);
	}
}
