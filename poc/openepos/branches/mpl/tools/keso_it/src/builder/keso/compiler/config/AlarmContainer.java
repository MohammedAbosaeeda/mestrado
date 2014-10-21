/**
 * Marker interface for definitions that contain Alarms.
 */
package keso.compiler.config;

import java.util.Vector;
import java.lang.String;

public interface AlarmContainer {
	public void addAlarm(AlarmDefinition adef);
	public AlarmDefinition getAlarm(String name);
	public Vector getAlarms();
}

