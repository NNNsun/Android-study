
fun main(){
    val hello1="hello world1"        //회색->아직 사용전
    val hello2:String="hello world2" //String의 경우 생략가능
    var hello3="hello world3"
    var hello4:String?=null;         //물음표가 없으면 null을 넣지 못함 스위프트도 동일, null 이외도 가능

    println(hello2)
    println(hello3)
    
    //여러줄의 문자열 표현
    val str="""오늘은 날씨가 좋습니다.
        빨래를 합시다."""
    
    //문자열 표현
    val str2="안녕"
    println(str2+"하세요")     //자바와 동일
    println("$str2 하세요")    //띄어쓸때
    println("${str2}하세요")   //붙여쓸때

    //if 제어문
    val a=10
    val b=20
        //일반적인 방법
    var max =a
    if(a<b)max=b
    if(a>b){
        max=a
    }else{
        max=b
    }
        //같은 if문
    val max2=if(a>b)a else b

    //when문 (swich)
    val x=8
    when (x){
        1->println("x==1")
        2,3->println("x==2 or x==3")
        in 4..7->println("4~7사이")
        !in 8..10->println("8부터 10사이가 아님")
        else->{                 //else 다음 (->) 필수
            print("x는 1이나 2가 아님")
        }
    }
        //when의 if문같은 사용
    val numberStr=1
    val numStr=when(numberStr % 2){
        0->"짝"
        else->"홀"
    }
    println(numStr)
    val number =1
    fun isEven(num:Int)=when(num%2){
        0->"짝"
        else->"홀"
    }
    println(isEven(number)) //홀

}
//함수사용
fun hello() : String{    //private String hello(){}
   return "hello fun"
}

