package fr.fonkio.gspt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import fr.fonkio.gspt.R

class PieceActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piece)
        recyclerView = findViewById(R.id.rvPiece)
    }
}