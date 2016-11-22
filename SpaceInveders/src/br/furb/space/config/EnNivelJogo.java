package br.furb.space.config;

public enum EnNivelJogo {

	EASY(1),
	MEDIUM(2),
	HARD(3);
	
	private int codigo;

	private EnNivelJogo(int codigo) {
		this.codigo = codigo;
	}
	
	public static EnNivelJogo getInstance(Integer codigo){
		for (EnNivelJogo nivelJogo : values()) {
			if (nivelJogo.getCodigo() == codigo) {
				return nivelJogo;
			}
		}
		
		return null;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
}
