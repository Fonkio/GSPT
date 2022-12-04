package fr.fonkio.gspt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.fonkio.gspt.R
import fr.fonkio.gspt.adapter.IRecyclerViewItemClickListener
import fr.fonkio.gspt.adapter.PieceAdapter
import fr.fonkio.gspt.dao.IPieceDao
import fr.fonkio.gspt.database.AppDatabase
import fr.fonkio.gspt.entity.Piece

class PieceActivity : AppCompatActivity(), IRecyclerViewItemClickListener{

    private lateinit var recyclerView : RecyclerView
    private lateinit var db : AppDatabase
    private lateinit var pieceDao : IPieceDao
    private lateinit var fabtAdd: FloatingActionButton
    private lateinit var pieceList: MutableList<Piece>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piece)
        db = AppDatabase.getInstance(this)
        pieceDao = db.pieceDao()
        recyclerView = findViewById(R.id.rvPiece)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PieceActivity)
        }
        fabtAdd = findViewById(R.id.fabtAdd)
        fabtAdd.setOnClickListener { onFabtAddClicked() }
        supportActionBar?.title = getString(R.string.piece_list)
    }

    override fun onResume() {
        super.onResume()
        pieceList = pieceDao.getAll()
        recyclerView.adapter = PieceAdapter(pieceList, this)
    }

    private fun onFabtAddClicked() {
        val intent = Intent(this, PieceDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onClickItem(position: Int) {
        val intent = Intent(this, PieceDetailActivity::class.java)
        intent.putExtra("piece", pieceList[position])
        startActivity(intent)
    }
}