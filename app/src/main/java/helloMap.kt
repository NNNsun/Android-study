fun main(){
    //Map - Key,Value

    var map1= mapOf(Pair("name","sun"))         //수정 불가
    var map2= mutableMapOf<String,String>()     //수정 가능
    map2.put("name","kim")
    map2.put("age","1")
    for(map in map2){
        println(map.key)
        println(map)
    }
    print(map2.keys)
}