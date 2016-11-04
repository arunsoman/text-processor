package com.flytxt.tp;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityMetaData {

	private Long entityId;

	private String entityName;
	
	private Long attributeId;

	private KeyValue eventType;

	private List<KeyValue> opType;

	private List<KeyValue> aggType;
	
	@AllArgsConstructor
	@Data
	public static class KeyValue {

		Long id;
		String text;
	}
	
	public static KeyValue getkeyValueObject(Long id, String value){
		return new KeyValue(id,value);
	}

}
