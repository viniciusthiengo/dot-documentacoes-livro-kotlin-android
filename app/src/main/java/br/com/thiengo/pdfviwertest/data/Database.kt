package br.com.thiengo.pdfviwertest.data

import android.content.Context
import br.com.thiengo.pdfviwertest.R
import br.com.thiengo.pdfviwertest.domain.Doc


class Database {
    companion object{
        fun getDocs() = listOf(
                Doc("kotlin-docs.pdf", R.drawable.kotlin_bg, "Kotlin", 194),
                Doc("java-docs.pdf", R.drawable.java_bg, "Java", 670),
                Doc("python-docs.pdf", R.drawable.python_bg, "Python", 1538),
                Doc("haskell-docs.pdf", R.drawable.haskell_bg, "Haskell", 503),
                Doc("scala-docs.pdf", R.drawable.scala_bg, "Scala", 547)
            )

        fun saveActualPageSP( context: Context, key: String, page: Int ){
            context
                .getSharedPreferences("PREF", Context.MODE_PRIVATE)
                .edit()
                .putInt("$key-page", page)
                .apply()
        }

        fun getActualPageSP( context: Context, key: String )
            = context
                    .getSharedPreferences("PREF", Context.MODE_PRIVATE)
                    .getInt("$key-page", 0)

    }
}

