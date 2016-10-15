package com.flytxt.compiler.domain;

import lombok.Data;

@Data
public class CompileNTest {

    final String name;

    final String init;

    String absProcessor;

    final String extract;

    final String transformation;

    final String store;

    final String type; // single,hybrid

    final String sample;

}
