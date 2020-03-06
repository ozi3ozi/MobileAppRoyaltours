package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
//https://futurestud.io/tutorials/how-to-run-an-android-app-against-a-localhost-api
public class GestionLivresActivity extends AppCompatActivity implements View.OnClickListener{
//    Button benreg,blister,bmodifier,benlever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_livres);
//        ajouterEvents();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

//    public void ajouterEvents(){
//        benreg = (Button) findViewById(R.id.benreg);
//        benreg.setOnClickListener(this);
//        blister = (Button) findViewById(R.id.blister);
//        blister.setOnClickListener(this);
//        bmodifier = (Button) findViewById(R.id.bmodifier);
//        bmodifier.setOnClickListener(this);
//        benlever = (Button) findViewById(R.id.benlever);
//        benlever.setOnClickListener(this);
//    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.circuits:
                lister();
                break;
            case R.id.compte:
                lister();
                break;
            case R.id.QUITTER:
                this.finish();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

//    public void enregistrer(){
//        Intent IntEnreg = new Intent(GestionLivresActivity.this, EnregistrerActivity.class);
//        startActivity(IntEnreg);
//    }
    public void lister(){
        Intent IntLister = new Intent(GestionLivresActivity.this, ListerActivity.class);
        startActivity(IntLister);
    }

    @Override
    public void onClick(View v) {




    }
}
//    public void modifier(){
//        Intent IntModif = new Intent(GestionLivresActivity.this, ModifierActivity.class);
//        startActivity(IntModif);
//    }
//    public void enlever(){
//        Intent IntEnlever = new Intent(GestionLivresActivity.this, EnleverActivity.class);
//        startActivity(IntEnlever);
//    }


