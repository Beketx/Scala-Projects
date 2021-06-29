package week4

object AverageSalaryExcludingMinimumAndMaximumSalary {
  def average(salary: Array[Int]): Double = {
    (salary.sum - (salary.min + salary.max)).toDouble / (salary.length - 2).toDouble
  }
}
