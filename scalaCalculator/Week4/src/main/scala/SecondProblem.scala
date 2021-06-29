object SecondProblem {
  def average(salary: Array[Int]): Double = {
    (salary.sum -salary.min - salary.max) / (salary.length - 2)
  }
}
