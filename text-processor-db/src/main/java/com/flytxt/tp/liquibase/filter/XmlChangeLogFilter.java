package com.flytxt.tp.liquibase.filter;

import liquibase.changelog.IncludeAllFilter;

public class XmlChangeLogFilter  implements IncludeAllFilter{
	@Override
    public boolean include(final String changeLogPath) {
        return changeLogPath.toLowerCase().endsWith(".xml");
    }
}
