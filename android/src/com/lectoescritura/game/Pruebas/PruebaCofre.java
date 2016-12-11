package com.lectoescritura.game.Pruebas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.lectoescritura.game.R;

import java.util.ArrayList;

/**
 * Created by Javier on 11/12/2016.
 */
public class PruebaCofre extends Activity {
    private String tipo;
    private ArrayList<String> valores;
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

        setContentView(R.layout.prueba_cofre);

        final TextView correctoButton = (TextView) findViewById(R.id.correcto);
        correcto = "   " + correcto;
        correctoButton.setText(correcto);

        final NumberPicker picker = (NumberPicker) findViewById(R.id.numberPicker);
        picker.setMinValue(0);
        picker.setMaxValue(2);
        picker.setDisplayedValues( new String[] {valores.get(0), valores.get(1), valores.get(2) } );
        picker.setValue(1);
        picker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        final NumberPicker picker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        picker2.setMinValue(0);
        picker2.setMaxValue(2);
        picker2.setValue(1);
        picker2.setDisplayedValues( new String[] { valores.get(3), valores.get(4), valores.get(5) } );
        picker2.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        final NumberPicker picker3 = (NumberPicker) findViewById(R.id.numberPicker3);
        picker3.setMinValue(0);
        picker3.setMaxValue(2);
        picker3.setValue(1);
        picker3.setDisplayedValues( new String[] { valores.get(6), valores.get(7), valores.get(8) } );
        picker3.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        final NumberPicker picker4 = (NumberPicker) findViewById(R.id.numberPicker4);
        picker4.setMinValue(0);
        picker4.setMaxValue(2);
        picker4.setValue(1);
        picker4.setDisplayedValues( new String[] { valores.get(9), valores.get(10), valores.get(11) } );
        picker4.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        Button resolver = (Button) findViewById(R.id.resolver);

        resolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((valores.get(picker.getValue()).equals(correcto) && valores.get(picker2.getValue()+3).equals(correcto)
                        && valores.get(picker3.getValue()+6).equals(correcto) && valores.get(picker4.getValue()+9).equals(correcto)) || cont == 3)
                {
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
                }
                else {
                    Toast.makeText(getApplicationContext(), "¡Inténtalo de nuevo!", Toast.LENGTH_SHORT).show();
                    intentos.add(valores.get(picker.getValue()));
                    intentos.add(valores.get(picker2.getValue()+3));
                    intentos.add(valores.get(picker3.getValue()+6));
                    intentos.add(valores.get(picker4.getValue()+9));
                    cont++;
                }
            }
        });

        Button salir = (Button) findViewById(R.id.salir);



    }
}
