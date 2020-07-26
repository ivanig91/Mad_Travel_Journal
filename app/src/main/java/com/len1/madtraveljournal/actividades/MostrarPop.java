package com.len1.madtraveljournal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MostrarPop extends Activity {

    ImageView foto;
    TextView nombre;
    TextView calle;
    TextView descripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pop);

        foto = findViewById(R.id.ivPopFoto);
        nombre = findViewById(R.id.tvPopNombre);
        calle = findViewById(R.id.tvPopDir);
        descripcion = findViewById(R.id.tvPopDesc);
        Intent intent = getIntent();

        LugarBar bar = (LugarBar) intent.getSerializableExtra("bar");

        nombre.setText(bar.getNombre());
        calle.setText(bar.getDireccion());
        descripcion.setText(bar.getDescripcion());
        //Picasso.get().load(lugar.getFotoUrl()).into(holder.icono);
        Picasso.get().load(bar.getFotoUrl()).into(foto);
        // Antes de descomentar el codigo crea el FAB de nuevo
        /*
        FloatingActionButton fab = findViewById(R.id.btPopCerrar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/
    }
}
