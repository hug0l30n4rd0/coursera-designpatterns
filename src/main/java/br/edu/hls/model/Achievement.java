package br.edu.hls.model;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class Achievement {
	private String name;
	
	public Achievement(String name) {
		this.name = name;
	}
	
	public abstract void adiciona(List<Achievement> achievementsAnteriores);
}
