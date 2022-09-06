package br.edu.hls.model;

import java.util.List;

import br.edu.hls.exception.AchievementJaExisteRuntimeException;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Achievement que possui uma pontuacao
 * Quando for adicionado um Achievement desse tipo com q quantidade de pontos ganhos, deve ser somado ao anterior. 
 * Não devem haver 2 Achievement do tipo Points com o mesmo nome para um usuário. 
 * @author Hugo Silva
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Points extends Achievement {
	private int valor;
	
	public Points(String name, int valor) {
		super(name);
		this.valor = valor;
	}

	@Override
	public void adiciona(List<Achievement> achievementsAnteriores) {
		Achievement a = achievementsAnteriores.stream().filter((achv) -> achv.getName().equals(this.getName())).findFirst().orElse(null);
		if (a == null) {
			achievementsAnteriores.add(this);
		} else {
			if (a instanceof Points) {
				Points achievementAnterior = (Points) a;
				achievementAnterior.setValor(achievementAnterior.getValor() + this.getValor());
				this.setValor(achievementAnterior.getValor() + this.getValor());
			} else {
				throw new AchievementJaExisteRuntimeException("Ops! Já existe um Achievement com mesmo nome ("+a.getName()+") que não é do tipo Points!");
			}
			
		}
	}
	
}
