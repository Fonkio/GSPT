package fr.fonkio.gspt.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import fr.fonkio.gspt.R
import fr.fonkio.gspt.adapter.IRecyclerViewItemRemoveButtonClickListener
import fr.fonkio.gspt.adapter.TractorAdapter
import fr.fonkio.gspt.dao.IPieceDao
import fr.fonkio.gspt.dao.IPieceWithTractorDao
import fr.fonkio.gspt.database.AppDatabase
import fr.fonkio.gspt.entity.Piece
import fr.fonkio.gspt.entity.PieceWithTractors
import fr.fonkio.gspt.entity.Tractor

class PieceDetailActivity : AppCompatActivity(),
    IRecyclerViewItemRemoveButtonClickListener {

    private lateinit var tietLibelle: TextInputEditText
    private lateinit var tietCase: TextInputEditText
    private lateinit var tietReference: TextInputEditText
    private lateinit var tietPrice: TextInputEditText
    private lateinit var tietAmount: TextInputEditText
    private lateinit var tietComment: TextInputEditText
    private lateinit var rvTractor: RecyclerView
    private lateinit var btAddTractor: Button
    private var tractorsBackup = listOf<Tractor>()
    private var tractors = mutableListOf<Tractor>()

    private lateinit var db : AppDatabase
    private lateinit var pieceDao : IPieceDao
    private lateinit var pieceWithTractorDao : IPieceWithTractorDao

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val tractor = data?.getParcelableExtra<Tractor>("tractorToAdd")
            if (tractor != null) {
                tractors.add(tractor)
            }
            with(rvTractor) {
                layoutManager = LinearLayoutManager(this@PieceDetailActivity)
                adapter = TractorAdapter(tractors, null, this@PieceDetailActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piece_detail)
        tietLibelle = findViewById(R.id.tietLibelle)
        tietReference = findViewById(R.id.tietReference)
        tietCase = findViewById(R.id.tietCase)
        tietPrice = findViewById(R.id.tietPrice)
        tietAmount = findViewById(R.id.tietAmount)
        tietComment = findViewById(R.id.tietComment)
        btAddTractor = findViewById(R.id.btAddTractor)
        rvTractor = findViewById(R.id.rvTractor)
        with(rvTractor) {
            layoutManager = LinearLayoutManager(this@PieceDetailActivity)
            adapter = TractorAdapter(tractors, null, this@PieceDetailActivity)
        }
        btAddTractor.setOnClickListener { onAddTractorClicked() }

        db = AppDatabase.getInstance(this)
        pieceDao = db.pieceDao()
        pieceWithTractorDao = db.pieceWithTractorDao()

        if (intent.hasExtra("piece")) {
            loadPiece(intent.getSerializableExtra("piece") as Piece)
        }

        supportActionBar?.title = getString(R.string.edit_piece)
    }

    private fun loadPiece(piece: Piece) {
        tietReference.setText(piece.reference)
        tietLibelle.setText(piece.libelle)
        tietCase.setText(piece.cases)
        tietPrice.setText(piece.price.toString())
        tietComment.setText(piece.comment)
        tietAmount.setText(piece.amount.toString())
        tractors = pieceWithTractorDao.findByReference(piece.reference).tractors.toMutableList()
        with(rvTractor) {
            layoutManager = LinearLayoutManager(this@PieceDetailActivity)
            adapter = TractorAdapter(tractors, null, this@PieceDetailActivity)
        }
        tractorsBackup = tractors.toList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itSave -> onSaveButtonClicked()
            R.id.itDelete -> onDeleteButtonClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onDeleteButtonClicked() {
        if (intent.hasExtra("piece")) {
            val oldPiece = (intent.getSerializableExtra("piece") as Piece)
            pieceDao.delete(oldPiece)
        }
        finish()
    }


    private fun onAddTractorClicked() {
        val intent = Intent(this, TractorActivity::class.java)
        intent.putParcelableArrayListExtra("alreadyAdd", ArrayList(tractors))
        activityResultLauncher.launch(intent)
    }

    private fun onSaveButtonClicked() {
        val reference = tietReference.text.toString()
        val libelle = tietLibelle.text.toString()
        val case = tietCase.text.toString()
        val price = tietPrice.text.toString()
        val comment = tietComment.text.toString()
        val amount = tietAmount.text.toString()

        val piece = Piece(
            reference,
            libelle,
            case,
            price.toDouble(),
            comment,
            amount.toInt()
        )
        if (intent.hasExtra("piece")) {
            val oldPiece = (intent.getSerializableExtra("piece") as Piece)
            //Si on trouve la ref en base et que ce n'est pas la même que l'ancienne
            if (oldPiece.reference != piece.reference && pieceDao.findByReference(piece.reference) != null) {
                Toast.makeText(this, R.string.ref_already_exist, Toast.LENGTH_LONG).show()
                return
            } else {
                pieceDao.delete(oldPiece)
                pieceWithTractorDao.deleteByReference(oldPiece.reference)
                pieceDao.insertAll(piece)
                for (tractor in tractors) {
                    pieceWithTractorDao.insert(PieceWithTractors(
                        piece.reference,
                        tractor.tractorId
                        ))
                }

            }
        } else {
            if (pieceDao.findByReference(piece.reference) != null) {
                Toast.makeText(this, R.string.ref_already_exist, Toast.LENGTH_LONG).show()
                return
            } else {
                pieceDao.insertAll(piece)
            }
        }
        finish()
    }

    override fun onClickItemRemove(position: Int) {
        tractors.removeAt(position)
        with(rvTractor) {
            layoutManager = LinearLayoutManager(this@PieceDetailActivity)
            adapter = TractorAdapter(tractors, null, this@PieceDetailActivity)
        }
    }
}