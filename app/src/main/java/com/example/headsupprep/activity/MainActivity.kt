package com.example.headsupprep.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupprep.*
import com.example.headsupprep.classes.rvCelebrity
import com.example.headsupprep.model.APIClinet
import com.example.headsupprep.model.APIInterface
import com.example.headsupprep.classes.CelebrityGame
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var rvCeleb: RecyclerView
    lateinit var addCeleBtn: Button
    lateinit var editCelebrity: EditText
    lateinit var btnSub: Button
    lateinit var arrayCeleb : ArrayList<CelebrityGame>

    var searcher = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init RCv
        rvCeleb = findViewById(R.id.rvCeleb)
        arrayCeleb = arrayListOf()
        rvCeleb.adapter = rvCelebrity(arrayCeleb)
        rvCeleb.layoutManager = LinearLayoutManager(this)


        // init UI
        editCelebrity = findViewById(R.id.edtCele)
        addCeleBtn = findViewById(R.id.addBtn)
        btnSub = findViewById(R.id.subBtn)

        val apiInterface = APIClinet().getClinet()?.create(APIInterface::class.java)
        val call: Call<List<CelebrityGame?>> = apiInterface!!.showInfo()

        call?.enqueue(object: Callback<List<CelebrityGame?>>{
            override fun onResponse(call: Call<List<CelebrityGame?>>, response: Response<List<CelebrityGame?>>)
            {
                val resource: List<CelebrityGame?>? = response.body()       // response.body() {information in API }
                if (resource != null){
                    for (i in resource){
                        arrayCeleb.add(CelebrityGame(i!!.pk, i.name, i.taboo1, i.taboo2, i.taboo3))
                        rvCeleb.adapter?.notifyDataSetChanged()
                        rvCeleb.scrollToPosition(arrayCeleb.size-1)
                                        }
                                    }
            }

            override fun onFailure(call: Call<List<CelebrityGame?>>, t: Throwable) {
                call.cancel()
                Log.d("MainActivity","${t.message}")
            }
        })

        addCeleBtn.setOnClickListener {
            val intent = Intent(this, New_Celebrity::class.java)
            startActivity(intent)
        }

        btnSub.setOnClickListener {
            for(i in arrayCeleb){
                if (i.name == editCelebrity.text.toString()){
                    searcher = i.pk!!.toInt()
                }
           }
            val intent = Intent(this, Update_Celebrity::class.java)
            intent.putExtra("id", searcher)
            startActivity(intent)
        }
    }
}