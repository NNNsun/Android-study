
fun main(){
    var cls1=HelloClass()
    var cls2 =HelloClass(15) //class호출


    var person=Person(1,"sun")

    println(cls1.age)
    println(cls2.age)
    println(person.age)
    println(person)


}

class HelloClass{
    var age=0
    init{
        //가장먼저 실행하는 블록: 생성자와 같은 역할 가능
    }
    constructor()               //생성자
    constructor(age:Int) {       //보조 생성자 : 값을 넘길수 있음
        this.age=age
    }
}

data class Person(val age:Int, val name:String) //-set -get을 위함

//상속
open class Animal{

}
class Dog:Animal(){

}
open class kim(val name:String){

}
class jun(name:String):kim(name){

}
//내부클래스
class OuterClass{
    var a=10
    //내부클래스 활용
    inner class OuterClass2{
        fun something(){
            a=20    //접근가능
        }
    }
}

//추상클래스
abstract class A{
    abstract fun func()

    fun func2(){

    }
}
class B:A(){
    override fun func() {
        println("hello")
    }
}
//val a=A()   //에러: 추상클래스 선언 불가
val a=B()   //OK

//인터페이스







