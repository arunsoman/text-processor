#!/usr/bin/env bash
var1=`jar -tvf tp.jar | grep  'marker' | awk '{print $8}'`
jar -xvf tp.jar $var1
var1=`jar -tvf tp.jar | grep  'translator' | awk '{print $8}'`
jar -xvf tp.jar $var1
var1=`jar -tvf tp.jar | grep  'store-0' | awk '{print $8}'`
jar -xvf tp.jar $var1
var1=`jar -tvf tp.jar | grep  'processor' | awk '{print $8}'`
jar -xvf tp.jar $var1
