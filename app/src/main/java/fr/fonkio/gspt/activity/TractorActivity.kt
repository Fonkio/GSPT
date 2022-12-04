package fr.fonkio.gspt.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.fonkio.gspt.R
import fr.fonkio.gspt.adapter.IRecyclerViewItemClickListener
import fr.fonkio.gspt.adapter.TractorAdapter
import fr.fonkio.gspt.dao.ITractorDao
import fr.fonkio.gspt.database.AppDatabase
import fr.fonkio.gspt.entity.Tractor


class TractorActivity : AppCompatActivity(), IRecyclerViewItemClickListener {
    private lateinit var recyclerView : RecyclerView
    private lateinit var db : AppDatabase
    private lateinit var tractorDao : ITractorDao
    private lateinit var fabtAdd: FloatingActionButton
    private lateinit var tractorList: MutableList<Tractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tractor)
        db = AppDatabase.getInstance(this)
        tractorDao = db.tractorDao()
        recyclerView = findViewById(R.id.rvTractor)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TractorActivity)
        }
        fabtAdd = findViewById(R.id.fabtAdd)
        fabtAdd.setOnClickListener { onFabtAddClicked() }
        supportActionBar?.title = getString(R.string.tractor_list)
    }

    override fun onResume() {
        super.onResume()
        tractorList =
            if (intent.hasExtra("alreadyAdd")) {
                val alreadyAddedTractor = intent.getParcelableArrayListExtra<Tractor>("alreadyAdd")
                if (alreadyAddedTractor != null) {
                    tractorDao.getAll().filter { t ->
                        !alreadyAddedTractor.contains(t)
                    }.toMutableList()
                } else {
                    tractorDao.getAll()
                }
            } else {
                tractorDao.getAll()
            }

        recyclerView.adapter = TractorAdapter(tractorList, this, null)
    }

    private fun onFabtAddClicked() {
        val intent = Intent(this, TractorDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onClickItem(position: Int) {
        if (callingActivity == null) {
            val intent = Intent(this, TractorDetailActivity::class.java)
            intent.putExtra("tractor", tractorList[position])
            startActivity(intent)
        } else {
            val returnIntent = Intent()
            returnIntent.putExtra("tractorToAdd", tractorList[position])
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }
}