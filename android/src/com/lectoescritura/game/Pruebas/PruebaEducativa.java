package com.lectoescritura.game.Pruebas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lectoescritura.game.Interfaces.AndroidUtils;
import com.lectoescritura.game.Interfaces.Conector;
import com.lectoescritura.game.R;

import java.util.ArrayList;

/**
 * Created by Javier on 20/11/2016.
 */
public class PruebaEducativa extends Activity {
    private String tipo;
    private ArrayList valores;
    private String correcto;
    private ArrayList<String> intentos;  // TODO: Guardar el segundo al que lo pulsa
    private Conector conector;

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
            //conector = (Conector) getIntent().getSerializableExtra("interface");
        }

        setContentView(R.layout.prueba_educativa);
        findViewById(R.id.fragmentContainer).setBackgroundColor(Color.BLACK);
        elegirTipo(tipo);
    }



    void elegirTipo(String tipo) {

        View.OnClickListener buttonOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                intentos.add(b.getText().toString());

                if (b.getText().equals(correcto)) {
//                    Intent intent = getIntent();
//                    intent.putExtra("resultado", intentos);
//                    setResult(RESULT_OK, intent);
                    //conector.setIntentos(intentos);
                    // TODO: guardar los intentos
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "¡Inténtalo de nuevo!", Toast.LENGTH_SHORT).show();
                }

            }
        };

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
