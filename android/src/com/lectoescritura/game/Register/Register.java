package com.lectoescritura.game.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lectoescritura.game.R;

/**
 * Created by Javier on 02/12/2016.
 */
public class Register extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        final EditText nombre = (EditText) findViewById(R.id.nombre);

        ImageButton boy = (ImageButton) findViewById(R.id.boy);
        ImageButton girl = (ImageButton) findViewById(R.id.girl);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Elige personaje", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = getIntent();
                    intent.putExtra("nombre", nombre.getText().toString() );
                    ImageButton b = (ImageButton)v;
                    String avatar = String.valueOf(b.getId());
                    intent.putExtra("avatar", avatar);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        };

        boy.setOnClickListener(onClickListener);
        girl.setOnClickListener(onClickListener);

    }
}
