package week4

object DayOfTheWeek {
  def isLeap(year: Int): Boolean = {
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
      true
    else
      false
  }
  def dayOfTheWeek(day: Int, month: Int, year: Int): String = {
    val weekDays = Array("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
    val monthDay = Array(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    var cnt = 4;

    for (i <- 1971 until year) {
      cnt += (if (isLeap(i)) 366 else 365)
    }

    for (i <- 1 until month) {
      if (isLeap(year) && i == 2) {
        cnt += 1;
      }
      cnt += monthDay(i);
    }
    cnt += day;
    cnt %= 7;
    weekDays(cnt)
  }
}
