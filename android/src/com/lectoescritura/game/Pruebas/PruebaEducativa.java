package com.lectoescritura.game.Pruebas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lectoescritura.game.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Javier on 20/11/2016.
 */
public class PruebaEducativa extends Activity {
    private String tipo;
    private ArrayList valores;
    private String correcto, tiempo;
    private ArrayList<String> intentos;
    long miliseconds;
    int cont = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        tipo = null;
        if(b != null) {
            tipo = b.getString("tipo");
            valores = b.getStringArrayList("valores");
            correcto = b.getString("correcto");
            intentos = new ArrayList<>();
        }

        setContentView(R.layout.prueba_educativa);
        findViewById(R.id.fragmentContainer).setBackgroundColor(Color.BLACK);
        miliseconds = System.currentTimeMillis();
        elegirTipo(tipo);
    }



    void elegirTipo(String tipo) {

        View.OnClickListener buttonOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Button b = (Button) v;

                if (b.getText().equals(correcto) || cont == 3) {
                    Intent intent = getIntent();
                    intent.putStringArrayListExtra("intentos", intentos);

                    tiempo = String.valueOf(System.currentTimeMillis() - miliseconds);
                    intent.putExtra("tiempo", tiempo);
                    if (cont == 3)
                        intent.putExtra("respuesta_correcta", "No");
                    else
                        intent.putExtra("respuesta_correcta", "Si");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "¡Inténtalo de nuevo!", Toast.LENGTH_SHORT).show();
                    intentos.add(b.getText().toString());
                    cont++;
                }

            }
        };

        long seed = System.nanoTime();
        Collections.shuffle(valores, new Random(seed));

        if (tipo.equals("1")) {
            Button two = (Button)findViewById(R.id.two);
            two.setEnabled(false);
            two.setBackgroundColor(Color.BLACK);
            two.setText("");
            Button four = (Button)findViewById(R.id.four);
            four.setEnabled(false);
            four.setBackgroundColor(Color.BLACK);
            four.setText("");
            Button six = (Button)findViewById(R.id.six);
            six.setEnabled(false);
            six.setBackgroundColor(Color.BLACK);
            six.setText("");
            Button eight = (Button)findViewById(R.id.eight);
            eight.setEnabled(false);
            eight.setBackgroundColor(Color.BLACK);
            eight.setText("");

            if (valores.size() == 4) {
                Button one = (Button)findViewById(R.id.one);
                one.setText(valores.get(0).toString());
                one.setOnClickListener(buttonOnClickListener);
                Button three = (Button)findViewById(R.id.three);
                three.setText(valores.get(1).toString());
                three.setOnClickListener(buttonOnClickListener);
                Button seven = (Button)findViewById(R.id.seven);
                seven.setText(valores.get(2).toString());
                seven.setOnClickListener(buttonOnClickListener);
                Button nine = (Button)findViewById(R.id.nine);
                nine.setText(valores.get(3).toString());
                nine.setOnClickListener(buttonOnClickListener);
            }
        }else if (tipo.equals("2")) {
            Button one = (Button)findViewById(R.id.one);
            one.setEnabled(false);
            one.setBackgroundColor(Color.BLACK);
            one.setText("");
            Button three = (Button)findViewById(R.id.three);
            three.setEnabled(false);
            three.setBackgroundColor(Color.BLACK);
            three.setText("");
            Button seven = (Button)findViewById(R.id.seven);
            seven.setEnabled(false);
            seven.setBackgroundColor(Color.BLACK);
            seven.setText("");
            Button nine = (Button)findViewById(R.id.nine);
            nine.setEnabled(false);
            nine.setBackgroundColor(Color.BLACK);
            nine.setText("");

            if (valores.size() == 4) {
                Button two = (Button)findViewById(R.id.two);
                two.setText(valores.get(0).toString());
                two.setOnClickListener(buttonOnClickListener);
                Button four = (Button)findViewById(R.id.four);
                four.setText(valores.get(1).toString());
                four.setOnClickListener(buttonOnClickListener);
                Button six = (Button)findViewById(R.id.six);
                six.setText(valores.get(2).toString());
                six.setOnClickListener(buttonOnClickListener);
                Button eight = (Button)findViewById(R.id.eight);
                eight.setText(valores.get(3).toString());
                eight.setOnClickListener(buttonOnClickListener);
            }
        }

        Button five = (Button)findViewById(R.id.five);
        five.setEnabled(false);
        five.setText(correcto);
    }
}
