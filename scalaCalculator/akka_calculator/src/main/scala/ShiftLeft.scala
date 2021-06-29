class ShiftLeft {
  var size = 0
  var sizeOps = 0
  var dead = 0
  def shiftToLeft(array: Array[String], ind: Int, tempRes:Double): Array[String] ={
    array(ind) = tempRes.toString
    for(i <- ind+1 to array.length-2){
      array(i) = array(i+1)
    }
    if(size ==0){
      size = array.length-1
    }else{
      size = size-1
    }
    return array
  }
  def shiftOps(array: String, ind: Int):String ={
    val myString = new StringBuilder(array)
    for(i <- ind to array.length-1){
      if(i==array.length-1){
        myString.setCharAt(i, 'x')
      }else
      myString.setCharAt(i, array.charAt(i+1))
    }
    if(sizeOps ==0){
      dead = dead +1

      sizeOps = array.length-1
    }else{
      dead = dead +1

      sizeOps = sizeOps-1
    }
    return myString.toString()
  }
}
