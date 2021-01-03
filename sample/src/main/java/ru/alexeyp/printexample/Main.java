package ru.alexeyp.printexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import io.reactivex.functions.Consumer;
import ru.alexeyp.printerservice.PrintService;

public class Main extends Activity {
    private PrintService printService;
    private FloatingActionButton fabs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printService = new PrintService(this);
        initView();
    }
    private void initView() {
        fabs = findViewById(R.id.fab);
//        fabs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
    public void findPrinter(View view) {
    }
}
