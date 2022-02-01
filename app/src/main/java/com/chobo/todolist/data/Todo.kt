
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Todo(
    val title:String,
    val data:Long=Calendar.getInstance().timeInMillis,
){
    //autoGenerate: 추가로 기본키를 직접 지정하지 안항도 자동으로 증가
    @PrimaryKey(autoGenerate = true)
    val id:Long=0
}