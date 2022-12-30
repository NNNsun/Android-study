
fun main() {


//list

//요소 변경x
    val foods: List<String> = listOf("라면", "갈비", "밥")
    val nyam = listOf("라면", "갈비", "밥")
//요소 변경o
    val babs = mutableListOf("초밥", "김밥", "볶음밥")
    babs.add("김치볶음밥")
    babs.removeAt(0)
    babs[2] = "참치비빔밥"
    for (s in babs) {
        print("$s ")
    }

//map
//읽기전용 map
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
//변경 가능한 map
    val citiesMap = mutableMapOf("한국" to "서울", "일본" to "동경", "캐나다" to "오타와")
//요소에 덮어쓰기
    citiesMap["한국"] = "서울특별시"
//추가
    citiesMap["미국"] = "워싱턴DC"

//집합
//읽기 전용 집합
    val citySet = setOf("서울", "인천", "대구")
//수정 가능한 집합
    val citySet2 = mutableSetOf("서울", "인천", "대구")
    citySet2.add("안양")
    citySet2.remove("인천")
//집합의 크기
    citySet2.size
//'서울'이 집합에 포함되었는지 확인
    citySet2.contains("서울")

    //람다식
    fun add(x: Int, y: Int): Int {
        return x + y
    }

    fun add2(x: Int, y: Int) = x + y

    var addlamda = { x: Int, y: Int -> x + y }

    //확장함수
    fun Int.isEven() = this % 2 == 0 //Int자료형에 isEven함수를 추가한 모습
    val a = 4
    val b = 3
    println(a.isEven())
    println(b.isEven())

    //형변환
    val a2=10L
    val b2=20

    val c2=a2.toInt()
    val d2=b2.toDouble()
    val e2=a.toString()

    //숫자형태 문자열->int형
    val intStr="10"
    val str=Integer.parseInt(intStr)

    //class 형변환
    open class Animal
    class Dog:Animal()
    val dog=Dog()
    val animal=dog as Animal //dog를 Animal형으로 변환

    //형체크
    val str2="hello"
    if(str2 is String){ //str이 String형이라면면
        println(str2.toUpperCase())
    }
    //고차함수
    //인수:숫자,숫자,하나의 숫자를 인수로 하는 바노한값이 없는 함수
    fun add(x:Int,y:Int,callback:(sum:Int)->Unit){
        callback(x+y)
    }
    //함수는 {}로 감싸고 내부에서는 반환값을 it으로 접근할수 있음
    add(5,3,{ println(it)})//8

//    //동반객체, static과 같은 기능
//    val fragment=Fragment.newInstance()

    //let(), 자기 자신을 인수로 전달하고 결과 반환
    //fun<T,R> T.let(block:(T)->R):R
//    val result = str2?.let{
//        Integer.parseInt(it)
//    }
    //fun<T,R> with(receiver: T, block t.()->R):R
    with(str2){
        println(toUpperCase())
    }

    //apply()
    //fun <T> T.apply(block:T.()->Unit):T
//    val result2=car?.apply{
//        car.setColor(Color.RED)
//        car.setPrice(1000)
//    }
    //run(), 익명함수처럼 사용+객체에서 호출하는 방법 모두 제공,
    //익명 함수처럼 사용할때는 블록의 결과를 반환, 블록안에 선언된 변수는 모두 임시로 사용되는 변수
    //임시변수가 많이 필요할때 유용함
    //fun<R>run(block:()->R):R
    val avg=run{
        val korean=100
        val english=80
        val math=50
    }
    str2?.run{
        println(toUpperCase())
    }
    //확장함수, 상속받지않고 원래 클래스에 기능 추가가능
    fun String.toInt():Int{
        return Integer.parseInt(this)
    }
    print("10".toInt())


}

////동반객체
//class Fragment{
//    companion object {
//        fun newInstance():Fragment{
//            println("생성됨")
//        }
//    }
//}
