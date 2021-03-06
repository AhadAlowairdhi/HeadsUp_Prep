package com.example.headsupprep.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.headsupprep.*
import com.example.headsupprep.model.APIClinet
import com.example.headsupprep.model.APIInterface
import com.example.headsupprep.classes.CelebrityGame
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Update_Celebrity : AppCompatActivity() {

    lateinit var edtupdateName : EditText
    lateinit var edtupdateTabo1 : EditText
    lateinit var edtupdateTabo2 : EditText
    lateinit var edtupdateTabo3 : EditText
    lateinit var updtBtn : Button
    lateinit var deletBtn : Button
    lateinit var bakBtn : Button

    var id = 0

    // Connect to APIInterface
    val apiInterface = APIClinet().getClinet()?.create(APIInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_celebrity)

        // init UI
        initUD()

        id = intent.getIntExtra("id",0)

        deletBtn.setOnClickListener {
                 deleCelebrity()
        }

        bakBtn.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }

        updtBtn.setOnClickListener {

          var upNam = edtupdateName.text.toString()
          var upT1 = edtupdateTabo1.text.toString()
          var upT2 = edtupdateTabo2.text.toString()
          var upT3 = edtupdateTabo3.text.toString()

            if (upNam.isNotEmpty() && upT1.isNotEmpty() && upT2.isNotEmpty() && upT2.isNotEmpty() && upT3.isNotEmpty()){
                updateCelebrity()
            }

        }
    }


    fun initUD(){
        edtupdateName=findViewById(R.id.edUpdname)
        edtupdateTabo1=findViewById(R.id.edT1updt)
        edtupdateTabo2=findViewById(R.id.edT2updt)
        edtupdateTabo3=findViewById(R.id.edT3updt)

        updtBtn=findViewById(R.id.btnUpdt)
        deletBtn=findViewById(R.id.delBtn)
        bakBtn=findViewById(R.id.turnBtn)
    }

    fun deleCelebrity(){
        apiInterface?.deleteInfo(id)?.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@Update_Celebrity, "Celebrity deleted!", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@Update_Celebrity, "No Celebrity deleted", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun updateCelebrity(){
        apiInterface?.updateInfo(id, CelebrityGame(
            id,
            edtupdateName.text.toString(),
            edtupdateTabo1.text.toString(),
            edtupdateTabo2.text.toString(),
            edtupdateTabo3.text.toString()
        )
        )?.enqueue(object : Callback<CelebrityGame>{
            override fun onResponse(
                call: Call<CelebrityGame>,
                response: Response<CelebrityGame>
            ) {
                Toast.makeText(this@Update_Celebrity, "Celebrity Updated!", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<CelebrityGame>, t: Throwable) {
                Toast.makeText(this@Update_Celebrity, "Celebrity Not Updated!", Toast.LENGTH_LONG).show()
            }
        })
    }
}