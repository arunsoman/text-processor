package com.flytxt.tp.rte.events;

public enum EventAggType {
	DOUBLE_AGG_TIME((short) 1, "AggregateFloatEventIfValid"), DOUBLE_AGG((short) 2, "AggregateFloatEvent"), DOUBLE_TIME((short) 3, "UpdateFloatEventIfValid"), DOUBLE_DEFAULT((short) 4,
            "UpdateFloatEvent"), PASSTHROUGH((short) 5, "PASSTHROUGH"), LONG_AGG_TIME((short) 6, "AggregateLongEventIfValid"), LONG_AGG((short) 7, "AggregateLongEvent"), LONG_TIME((short) 8,
                    "UpdateLongEventIfValid"), LONG_DEFAULT((short) 9,
                            "UpdateLongEvent"), STRING_TIME((short) 10, "UpdateStringEventIfValid"), STRING_DEFAULT((short) 11, "UpdateStringEvent"), STATELESS((short) 12, "STATELESS");

    public short value;

    public String procedureName;

    EventAggType(short value, String procedureName) {
        this.value = value;
        this.procedureName = procedureName;
    }
}
