package br.furb.space.config;

public final class Configuration {

	private static Configuration configuration;
	public EnNivelJogo nivelJogo;
	
	private Configuration(){
		
	}
	
	public static Configuration getInstace(){
		return configuration;
	}

	public static Configuration getInstace(EnNivelJogo nivel){
		if (configuration == null) {
			configuration = new Configuration();
			configuration.nivelJogo = nivel;
		}
		
		return configuration;
	}
}
