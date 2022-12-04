package fr.fonkio.gspt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import fr.fonkio.gspt.R
import fr.fonkio.gspt.dao.ITractorDao
import fr.fonkio.gspt.database.AppDatabase
import fr.fonkio.gspt.entity.Tractor

class TractorDetailActivity : AppCompatActivity() {
    private lateinit var tietModel: TextInputEditText
    private lateinit var actvBrand: AutoCompleteTextView
    private lateinit var tietVersion: TextInputEditText

    private lateinit var db : AppDatabase
    private lateinit var tractorDao : ITractorDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tractor_detail)
        tietModel = findViewById(R.id.tietModel)
        actvBrand = findViewById(R.id.actvBrand)
        tietVersion = findViewById(R.id.tietVersion)

        db = AppDatabase.getInstance(this)
        tractorDao = db.tractorDao()

        if (intent.hasExtra("tractor")) {
            val tractor = intent.getParcelableExtra<Tractor>("tractor")
            if(tractor != null) {
                loadPiece(tractor)
            }
        }
        val brandList = tractorDao.getAllBrand()
        val adapterBrand = ArrayAdapter(this, android.R.layout.select_dialog_item, brandList)
        actvBrand.setAdapter(adapterBrand)

        supportActionBar?.title = getString(R.string.edit_tractor)
    }

    private fun loadPiece(tractor: Tractor) {
        tietModel.setText(tractor.model)
        actvBrand.setText(tractor.brand)
        tietVersion.setText(tractor.version)
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
        if (intent.hasExtra("tractor")) {
            val oldTractor = (intent.getParcelableExtra<Tractor>("tractor"))
            if (oldTractor != null) {
                tractorDao.delete(oldTractor)
            }
        }
        finish()
    }

    private fun onSaveButtonClicked() {
        val model = tietModel.text.toString()
        val brand = actvBrand.text.toString()
        val version = tietVersion.text.toString()

        val tractor = Tractor(
            model = model,
            brand = brand,
            version = version
        )
        if (intent.hasExtra("tractor")) {
            val oldTractor = (intent.getParcelableExtra<Tractor>("tractor"))
            if (oldTractor!= null) {
                //Si on trouve la le tracteur en base et que ce n'est pas la même que l'ancien
                if ((oldTractor.model != tractor.model || oldTractor.brand != tractor.brand) && tractorDao.findByModelAndBrandAndVersion(tractor.model, tractor.brand, tractor.version) != null) {
                    Toast.makeText(this, R.string.tractor_already_exist, Toast.LENGTH_LONG).show()
                    return
                } else {
                    tractorDao.delete(oldTractor)
                    tractorDao.insertAll(tractor)
                }
            }
        } else {
            if (tractorDao.findByModelAndBrandAndVersion(tractor.model, tractor.brand, tractor.version) != null) {
                Toast.makeText(this, R.string.tractor_already_exist, Toast.LENGTH_LONG).show()
                return
            } else {
                tractorDao.insertAll(tractor)
            }
        }
        finish()
    }
}