
fun main(){
   //1.배열
    var arr= listOf("1","2")         //수정 불가능 Java의 ArrayList
    var arr2= mutableListOf("1","2") //수정 가능
    var arr3=arrayOf(1,2,3)

    //2.반복문(향상된 for문)
    for(i in arr){
        println(i)
    }
        //인덱스와 함께 출력
    for((index, item) in arr2.withIndex()){
        println("$index, $item")
        //arr2.removeAt(0) 에러발생
    }
        // *2씩 출력
    for(i in 0..10 step 2){
        println(i)
    }
        // /2씩 출력
    for(i in 10 downTo 0 step 2){
        println(i)
    }


   //3.캐스팅 Object=Any, auto casting 지원
    var hello:Any="hello"

    if(hello is String) {
        var str: String = hello
    }










}