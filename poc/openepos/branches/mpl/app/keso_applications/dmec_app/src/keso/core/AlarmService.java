/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

public final class AlarmService {
	/**
	 * Read the AlarmBase characteristics of an Alarm.
	 *
	 * The characteristics of the Alarm will be written to <info>,
	 * an object of class AlarmBase provided by the caller.
	 */
	public static int getAlarmBase(Alarm alarmID, AlarmBase info) { return 0; }

	
	/**
	 * Query relative value in ticks before the Alarm expires.
	 *
	 * TODO return status is not accessable by the application. Throw
	 * an OSEKException when an error occurs.
	 */
	public static int getAlarm(Alarm alarmID) { return 0; }

	/**
	 * Occupies the Alarm specified by <alarmID>.
	 * After <increment> ticks have elapsed the task assigned to this alarm
	 * is activated/the assigned event is set/the alarm callback routine is
	 * called.
	 */
	public static int setRelAlarm(Alarm alarmID, int increment, int cycle) { return 0; }
	
	/**
	 * Occupies the Alarm specified by <alarmID>.
	 * After <start> ticks are reached the task assigned to this alarm
	 * is activated/the assigned event is set/the alarm callback routine is
	 * called.
	 */
	public static int setAbsAlarm(Alarm alarmID, int start, int cycle) { return 0; }
	
	/**
	 * Cancels the Alarm specified by <alarmID>.
	 */
	public static int cancelAlarm(Alarm alarmID) { return 0; }

	/*					KESO extensions */

	/**
	 * Get a reference to an Alarm object by specifying its name as it was
	 * defined in kesorc.
	 *
	 * The String must be a constant as it will be internally resolved at compile
	 * time.
	 *
	 * null will be returned if the Alarm cannot be found.
	 *
	 * TODO throw Exception when Alarm cannot be found
	 */
	public static Alarm getAlarmByName(String name) {	return null; }

}
