package br.com.thiengo.pdfviwertest.domain

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import br.com.thiengo.pdfviwertest.data.Database


class Doc(
        val path: String,
        val imageRes: Int,
        val language: String,
        val pagesNumber: Int) : Parcelable {

    fun saveActualPage(context: Context, page: Int ){
        Database.saveActualPageSP(context, path, page)
    }

    fun getActualPage(context: Context, plusPage: Int = 0 )
        = Database.getActualPageSP(context, path) + plusPage

    companion object {
        @JvmField val DOC_KEY = "doc"

        @JvmField val CREATOR: Parcelable.Creator<Doc> = object : Parcelable.Creator<Doc> {
            override fun createFromParcel(source: Parcel): Doc = Doc(source)
            override fun newArray(size: Int): Array<Doc?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(path)
        dest.writeInt(imageRes)
        dest.writeString(language)
        dest.writeInt(pagesNumber)
    }
}