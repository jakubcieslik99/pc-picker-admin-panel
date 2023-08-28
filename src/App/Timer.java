package App;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Timer extends Thread {
  protected boolean isRunning;
  protected JLabel dateLabel, timeLabel;
  protected SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.forLanguageTag("pl"));
  protected SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");

  public Timer(JLabel dateLabel, JLabel timeLabel) {
    this.dateLabel = dateLabel;
    this.timeLabel = timeLabel;
    this.isRunning = true;
  }

  public void run() {
    while (isRunning) {
      SwingUtilities.invokeLater(() -> {
        Calendar currentCalendar = Calendar.getInstance();
        Date currentTime = currentCalendar.getTime();
        dateLabel.setText(dateFormat.format(currentTime));
        timeLabel.setText(timeFormat.format(currentTime));
      });

      try {
        Thread.sleep(5000L);
      } catch (InterruptedException e) {
      }
    }
  }

  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }
}
