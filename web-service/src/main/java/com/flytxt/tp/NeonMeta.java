package com.flytxt.tp;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeonMeta  {


	private Long id;

	private String name;

	private List<EntityMetaData> data;

}
