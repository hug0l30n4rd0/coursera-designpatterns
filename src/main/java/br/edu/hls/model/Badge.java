package br.edu.hls.model;

import java.util.List;

import br.edu.hls.exception.AchievementJaExisteRuntimeException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Achievement que eh apenas uma medalha
 * representa um objetivo. 
 * Não faz sentido adicionar dois Achievement desse tipo com o mesmo nome para um mesmo usuário.
 * @author Hugo Silva
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Badge extends Achievement{
	
	public Badge(String name) {
		super(name);
		
	}
	
	@Override
	public void adiciona(List<Achievement> achievementsAnteriores) {
		Achievement a = achievementsAnteriores.stream().filter((achv) -> achv.getName().equals(this.getName())).findFirst().orElse(null);
		if (a == null) {
			achievementsAnteriores.add(this);
		} else if (!(a instanceof Badge)){
			throw new AchievementJaExisteRuntimeException("Ops! Já existe um Achievement com mesmo nome ("+a.getName()+") que não é do tipo Badge!");
		}
		
	}
}
