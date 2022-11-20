package fr.fonkio.gspt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.fonkio.gspt.R
import fr.fonkio.gspt.adapter.PieceAdapter
import fr.fonkio.gspt.dao.IPieceDao
import fr.fonkio.gspt.database.AppDatabase

class PieceActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var db : AppDatabase
    private lateinit var pieceDao : IPieceDao
    private lateinit var fabtAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piece)
        db = AppDatabase.getInstance(this)
        pieceDao = db.pieceDao()
        recyclerView = findViewById(R.id.rvPiece)
        recyclerView.adapter = PieceAdapter(pieceDao.getAll())
        fabtAdd = findViewById(R.id.fabtAdd)
        fabtAdd.setOnClickListener { onFabtAddClicked() }
    }

    private fun onFabtAddClicked() {
        val intent = Intent(this, PieceDetailActivity::class.java)
        startActivity(intent)
    }
}