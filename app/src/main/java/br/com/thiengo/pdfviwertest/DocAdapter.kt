package br.com.thiengo.pdfviwertest

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.thiengo.pdfviwertest.domain.Doc


class DocAdapter(
        private val context: Context,
        private val docList: List<Doc>) :
        RecyclerView.Adapter<DocAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int) : DocAdapter.ViewHolder {

        val v = LayoutInflater
                .from(context)
                .inflate(R.layout.iten_doc, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(docList[position])
    }

    override fun getItemCount(): Int {
        return docList.size
    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        val ivCover: ImageView
        val tvLanguage: TextView
        val tvTotalPages: TextView
        val tvPageStopped: TextView

        init {
            itemView.setOnClickListener(this)
            ivCover = itemView.findViewById(R.id.iv_cover)
            tvLanguage = itemView.findViewById(R.id.tv_language)
            tvTotalPages = itemView.findViewById(R.id.tv_total_pages)
            tvPageStopped = itemView.findViewById(R.id.tv_page_stopped)
        }

        fun setData(doc: Doc) {
            ivCover.setImageResource( doc.imageRes )
            tvLanguage.text = doc.language
            tvTotalPages.text = "${doc.pagesNumber} páginas"

            tvPageStopped.visibility = if( doc.getActualPage(context) > 0 ){
                    tvPageStopped.text = "Parou na página ${doc.getActualPage(context, 1)}"
                    View.VISIBLE
                }
                else{
                    View.GONE
                }
        }

        override fun onClick(view: View?) {
            val intent = Intent(context, PdfActivity::class.java)
            intent.putExtra( Doc.DOC_KEY, docList.get( adapterPosition ) )
            context.startActivity( intent )
        }
    }
}