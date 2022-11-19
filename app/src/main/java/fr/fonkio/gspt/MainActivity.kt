package fr.fonkio.gspt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btTractor : Button
    private lateinit var btPiece : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btTractor = findViewById(R.id.btTracteur)
        btPiece = findViewById(R.id.btPiece)
        btTractor.setOnClickListener { onBtTractorClicked() }
        btPiece.setOnClickListener { onBtPieceClicked() }
    }

    private fun onBtTractorClicked() {

    }

    private fun onBtPieceClicked() {

    }
}