import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*


//AndroidViewModel은 액티비티와 수명을 같이한다.
class MainViewModel (application: Application):AndroidViewModel(application){
    var selectedTodo:Todo?=null
    //Room 데이터 베이스
    private val db= Room.databaseBuilder(
        application,
        TodoDatabase::class.java,"todo"
    ).build()

    //db의 결과를 관찰할 수 있도록 하는 방법
    private val _items = MutableStateFlow<List<Todo>>(emptyList())
    val items:StateFlow<List<Todo>> = _items

    init{
        //ViewModel과 AndroidViewModel 클래스는 viewModelScope 코루틴 스포크를 제공
        //launch 함수 내에서 suspend 메서드를 실행할 수 있고 이는 비동기로 동작함
        viewModelScope.launch {
            //Flow 객체는 collect로 현재 값을 가져올 수 있음
            db.todoDao().getAll().collect{todos->
                //StateFlow 객체는 value 프로포티로 현재 상태값을 읽거나 쓸 수 있음
                _items.value=todos
            }
        }
    }
    fun addTodo(text:String,data: Long){
        viewModelScope.launch{
            db.todoDao().insert(Todo(text,date))
        }
    }
    fun updateTodo(text:String,date:Long){
        selectedTodo?.let { todo->
                todo.apply {
                    this.title = text
                    this.date = data
                }
                viewModelScope.launch{
                    db.todoDao().update(todo)
                }
                selectedTodo=null
            }
    }
    fun deleteTodo(id:Long){
        _items.value
            .find { todo->todo.id == id }
            ?.let { todo->
                viewModelScope.launch {
                    db.todoDao().delete(todo)
                }
                selectedTodo=null
            }
    }
}


