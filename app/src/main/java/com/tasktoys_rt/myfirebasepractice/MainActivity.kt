package com.tasktoys_rt.myfirebasepractice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class User{
    var name:String =""
    var age:Int = 0
    var some_list: Map<String,Boolean> = HashMap<String,Boolean>()
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("[")
        for(key in some_list.keys){
            builder.append(key).append(",")
        }
        builder.append("]")
        return "name:"+name+" , age:"+age.toString()+"\n"+builder.toString()
    }
}


class MainActivity : AppCompatActivity() {
    companion object {
        val RC_SIGN_IN = 123
        val TAG = "MAIN"
    }
    lateinit var logView :TextView
    lateinit var mAuth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logView = findViewById<TextView>(R.id.log_view)
        mAuth = FirebaseAuth.getInstance()
    }

    fun login(view: View) {
        mAuth.signInWithEmailAndPassword("takumi1988okamoto@gmail.com","robot00") // ハードコードいくない
                .addOnCompleteListener(this, OnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("aaa","success!!")
                        logView.text = "successs!!!"
                        Toast.makeText(this, "Authentication success.",
                                Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                })

    }

    fun write(view :View){
        val database = FirebaseDatabase.getInstance()
        database.getReference("message").push()
    }

    fun writeUserData(view: View){
        val database = FirebaseDatabase.getInstance()
        val uid = mAuth.currentUser?.uid
        uid?.let{
            val databeseRef = database.getReference("users").child(it)
            databeseRef.child("name").setValue("Motoko Kusanagi")
            databeseRef.child("age").setValue(26)
        }
    }

    fun read(view: View){
        val databaseRef = FirebaseDatabase.getInstance().getReference("users")
        databaseRef.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot.children
                var builder = StringBuilder()
                for(userSnapshot in users){
                    val user = userSnapshot.getValue(User::class.java)
                    builder.append(user.toString()).append("\n\n")
                }
                logView.text=builder.toString()
            }
        })

    }

}
