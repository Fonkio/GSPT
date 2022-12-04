package fr.fonkio.gspt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import fr.fonkio.gspt.R
import fr.fonkio.gspt.dao.IPieceDao
import fr.fonkio.gspt.dao.ITractorDao
import fr.fonkio.gspt.database.AppDatabase
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btTractor : Button
    private lateinit var btPiece : Button
    private lateinit var tvDbStatus : TextView
    private lateinit var tvDbLocation : TextView
    private lateinit var db : AppDatabase
    private lateinit var pieceDao: IPieceDao
    private lateinit var tractorDao: ITractorDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDatabase.getInstance(this)

        pieceDao = db.pieceDao()
        tractorDao = db.tractorDao()
        btTractor = findViewById(R.id.btTracteur)
        btPiece = findViewById(R.id.btPiece)
        tvDbStatus = findViewById(R.id.tvDbStatus)
        tvDbLocation = findViewById(R.id.tvDbLocation)

        btTractor.setOnClickListener { onBtTractorClicked() }
        btPiece.setOnClickListener { onBtPieceClicked() }
        //tvDbLocation.setOnClickListener { onTvDbLocation() }
    }

    private fun onTvDbLocation() {
        val sdf = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss")
        val currentDate = sdf.format(Date())
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "save-$currentDate"
        )
        dir.mkdir()
        getDatabasePath("database-gspt").parentFile?.copyRecursively(dir)

    }

    override fun onResume() {
        super.onResume()
        tvDbStatus.text =
            getString(R.string.dbStatusOk).format(tractorDao.countTractor().blockingGet(), pieceDao.sumPieceNumber().blockingGet())
        tvDbLocation.text = getString(R.string.dbLocation).format(getDatabasePath("database-gspt").absolutePath)
    }

    private fun onBtTractorClicked() {
        val intent = Intent(this, TractorActivity::class.java)
        startActivity(intent)
    }

    private fun onBtPieceClicked() {
        val intent = Intent(this, PieceActivity::class.java)
        startActivity(intent)
    }
}