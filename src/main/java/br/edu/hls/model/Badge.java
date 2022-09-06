package br.edu.hls.model;

import java.util.List;

import br.edu.hls.exception.AchievementJaExisteRuntimeException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Achievement que eh apenas uma medalha
 * representa um objetivo. 
 * N�o faz sentido adicionar dois Achievement desse tipo com o mesmo nome para um mesmo usu�rio.
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
			throw new AchievementJaExisteRuntimeException("Ops! J� existe um Achievement com mesmo nome ("+a.getName()+") que n�o � do tipo Badge!");
		}
		
	}
}
