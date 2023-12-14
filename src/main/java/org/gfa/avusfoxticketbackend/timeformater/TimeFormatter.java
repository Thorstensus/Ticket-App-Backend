package org.gfa.avusfoxticketbackend.timeformater;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

  public static String getFormattedTimestamp(ZonedDateTime time) {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm");
    return time.format(dateFormat);
  }
}