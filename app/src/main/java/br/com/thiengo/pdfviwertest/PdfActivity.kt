package br.com.thiengo.pdfviwertest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import br.com.thiengo.pdfviwertest.domain.Doc
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnRenderListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_pdf.*


class PdfActivity :
        AppCompatActivity(),
        OnPageChangeListener,
        OnRenderListener {

    lateinit var doc: Doc
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        doc = intent.getParcelableExtra( Doc.DOC_KEY )

        //val file = File(Environment.getExternalStorageDirectory(), doc?.path)

        pdfView
            //.fromFile(file)
            .fromAsset( doc.path )

            /*
             * Permite a definição da página que será carregada inicialmente.
             * A contagem inicia em 0.
             * */
            .defaultPage( doc.getActualPage(this) )

            /*
             * Caso um ScrollHandle seja definido, a numeração da página estará
             * presente na tela para que o usuário saiba em qual página está,
             * isso sem necessidade de dar o zoom nela. É possível implementar
             * o seu próprio ScrollHandle, mas a API também já fornece uma
             * implementação que tem como parâmetro um objeto de contexto,
             * DefaultScrollHandle.
             * */
            .scrollHandle( DefaultScrollHandle(this) )

            /*
             * Se definido como false, o usuário não conseguirá mudar de página.
             * */
            .enableSwipe(true)

            /*
             * Por padrão o swipe é vertical, ou seja, as próximas páginas estão
             * abaixo no scroll. Com swipeHorizontal() recebendo true o swipe
             * passa a ser horizontal, onde a próxima página é a que está a direita.
             * */
            .swipeHorizontal(true)

            /*
             * Útil para PDFs que necessitam de senha para serem visualizados.
             * */
            .password(null)

            /*
             * Caso true, permite que os níveis de zoom (min, middle, max) também
             * seja acionados caso o usuário dê touchs na tela do device.
             * */
            .enableDoubletap(true)

            /*
             * Caso true, permite que anotações e comentários, extra PDF original,
             * sejam apresentados.
             * */
            .enableAnnotationRendering(true)

            /*
             * Caso true, permite que haja otimização de renderização em telas
             * menores.
             * */
            .enableAntialiasing(true)

            .onPageChange(this)
            .onRender(this)
            .load()

        /*pdfView.setMinZoom(1F)
        pdfView.setMidZoom(1.75F)
        pdfView.setMaxZoom(6F)*/
    }

    override fun onResume() {
        super.onResume()
        toolbar.title = doc.language
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        doc.saveActualPage(this, page)
    }

    override fun onInitiallyRendered(nbPages: Int, pageWidth: Float, pageHeight: Float) {
        pdfView.fitToWidth( doc.getActualPage(this) )
    }
}
