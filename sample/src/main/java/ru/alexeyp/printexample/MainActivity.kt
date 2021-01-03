package ru.alexeyp.printexample

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.alexeyp.printerservice.PrintService
import ru.alexeyp.printerservice.connectors.PaperSize
import ru.alexeyp.printerservice.model.PrinterInfo
import java.io.File


class MainActivity : AppCompatActivity() {

    private val printService = PrintService(this)
    private lateinit var listView: ListView;
    var listPrinter: List<PrinterInfo> = listOf<PrinterInfo>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.listPrinter)

        fab.setOnClickListener {
            printService.findPrinters()
                    .subscribe( {
                        listPrinter = it;
                        Log.d("TAG", "onCreate: = $it")
                        log("Printers = $it")
                        print(it.first { !it.name.contains("HP") })
                    }, {})
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listPrinter)
        listView.adapter = adapter
    }

    private fun print(printInfo: PrinterInfo) {
        log("Printer = $printInfo}")
        val file = File("${Environment.getExternalStorageDirectory()}/teste.pdf")
        printService.print(printInfo.ip, printInfo.port, file, "dsa", PaperSize.A4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Toast.makeText(this, it, Toast.LENGTH_LONG).show() },
                        { it.printStackTrace()
                            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show() } )
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//            R.id.action_print -> {
//                Toast.makeText(this, "!", Toast.LENGTH_SHORT).show()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//    }

}