package br.com.thiengo.pdfviwertest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import br.com.thiengo.pdfviwertest.data.Database
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        initRecycler()
    }

    private fun initRecycler() {
        rv_todo.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(this)
        rv_todo.layoutManager = mLayoutManager

        val divider = DividerItemDecoration( this, mLayoutManager.orientation )
        rv_todo.addItemDecoration(divider)

        val adapter = DocAdapter(this, Database.getDocs())
        rv_todo.adapter = adapter
    }
}
