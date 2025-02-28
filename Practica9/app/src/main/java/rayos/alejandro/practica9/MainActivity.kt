package rayos.alejandro.practica9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance("https://practica9-8057d-default-rtdb.firebaseio.com").getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var btnSave: Button = findViewById(R.id.save_button) as Button
        btnSave.setOnClickListener { saveMarkFromForm() }

        userRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val name = dataSnapshot.child("firstName").getValue(String::class.java)
                val lastname = dataSnapshot.child("lastName").getValue(String::class.java)
                val age = dataSnapshot.child("age").getValue(String::class.java)

                val usuario = User(name, lastname, age)
                writeMark(usuario)
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveMarkFromForm(){
        var name: EditText = findViewById(R.id.et_name) as EditText
        var lastname: EditText = findViewById(R.id.et_lastName) as EditText
        var age: EditText = findViewById(R.id.et_age) as EditText

        val usuario = User(
            name.text.toString(),
            lastname.text.toString(),
            age.text.toString()
        )

        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User){
        var listV: TextView = findViewById(R.id.list_textView) as TextView
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text
    }

}