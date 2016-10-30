package com.flytxt.parser.marker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class MarkerDefaultConfig {
	@Bean
	public FlyPool<Marker> markerPool(){
		return new FlyPool<Marker>();
	}
	
	@Bean
	public FlyPool<FlyList<Marker>> markerListPool(){
		return new FlyPool<FlyList<Marker>>(); 
	}
	
	@Bean
	public FlyPool<ImmutableMarker> markerImmutablePool(){
		return new FlyPool<ImmutableMarker>();
	}
	
	@Bean 
	public FlyPool<FlyList<ImmutableMarker>> markerImmutableListPool (){
		return new FlyPool<FlyList<ImmutableMarker>>();
	}
}
