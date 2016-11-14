# text-processor[![Build Status](https://travis-ci.org/arunsoman/text-processor.svg?branch=master)](https://travis-ci.org/arunsoman/text-processor) [![codecov](https://codecov.io/gh/arunsoman/text-processor/branch/master/graph/badge.svg)](https://codecov.io/gh/arunsoman/text-processor)

Primary intension is to see if there is any performance improvement when text is processed in byte[] format or the String version is good enough. The use case is to extract fields by providing regex and then the selected fields should be written to a source file

Initial report are promising, its worth to run these as unikernel

compiler>mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
