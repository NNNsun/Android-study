
fun main(){
    var cls1=HelloClass()
    var cls2 =HelloClass(15) //class호출


    var person=Person(1,"sun")

    println(cls1.age)
    println(cls2.age)
    println(person.age)
    println(person)

    val eagle = Eagle()
    eagle.move()
    eagle.eat()

    //늦은 초기화:lateinit(var)
    lateinit var k: String
    k="Hello"
    println(k)

    //늦은 초기화:lazy(val)
    val str:String by lazy{
        println("초기화")
        "hello"
    }
    println(str) //초기화, hello
    println(str) //hello

    //null값이 아님을 보증(!!)
    val name:String?="키다리"
    //val name2:String=name -에러
    val name3:String?=name
    val name4:String=name!!

    //안전한 호출(?.) null값이 아닐때만 호출, null이면 null반환
    val sc:String?=null
    var upperCase=if(sc!=null) sc else null //null
    upperCase=sc?.toUpperCase()//null

    //엘비스 연산자?:안전한 호출시 null이아닌 기본값을 반환 할 때 사용
    val str2:String?=null
    var upperCase2=if(str2!=null)str2 else null
    upperCase2=str2?.toUpperCase()?:"초기화 하시오"//null이면 문구 반환

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
interface Runnable{
    fun run()
    fun fastRun()=println("빨리 달린다.") //자바의 default처럼 구현된 메서드를 포함할수있다.


}
class Human:Runnable{
    override fun run(){
        println("달린다.")
    }
}
//상속과 인터페이스를 함께 구현하기

open class Beast{

}
interface movable{
    fun move()
    fun fastmove()=println("빨리 달리다")
}
interface Eatable{
    fun eat()
}
class Eagle:Beast(),movable,Eatable{
    override fun eat(){
        println("야미")
    }
    override fun move(){
        println("달리자")
    }
}


























