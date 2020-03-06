package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListerEtapesActivity extends AppCompatActivity { //implements View.OnClickListener {
    ListView liste;
    ImageView iv;
    String idCircuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_etapes);
        liste=(ListView) findViewById(R.id.liste);
        iv = new ImageView(this);
        idCircuit = getIntent().getStringExtra("idCircuit");
        Toast.makeText(ListerEtapesActivity.this, idCircuit, Toast.LENGTH_LONG).show();
        lister();
    }

    public void lister() {

        final ArrayList<HashMap<String, Object>> tabLivres = new ArrayList<HashMap<String, Object>>();


        final String url = "http://10.0.2.2:80/RoyalToursV2/royaltours/pageClient/MVC/ControleurAPI.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i,j;
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray listeCircuits = jsonResponse.getJSONArray("liste");
                            HashMap<String, Object> map;
                            String msg = jsonResponse.getString("code");

                            Log.d("msg",msg);
                            if(msg.equals("OK")){
                                String imgUrl = "";
                                Log.d("ListeCircuit",listeCircuits.toString());
                                JSONObject unLivre;
                                for(i=0;i<listeCircuits.length();i++){
                                    unLivre=listeCircuits.getJSONObject(i);
                                    map= new HashMap<String, Object>();
                                    j=(i%7);//m0.jpg, ...,m6.jpg round robin
                                    String nomImage = "m"+j;
                                   /* byte[] decodedString = Base64.decode(unLivre.getString("image"), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    Drawable d = new BitmapDrawable(getResources(),decodedByte);
                                    map.put("Image", d);
                                    if (i==3)
                                        map.put("img", d);
                                    else*/
                                    imgUrl = "C:/xampp/htdocs/RoyalToursV2/royaltours/pageAdmin/images/imagecircuit/"+unLivre.getString("imgPrincipale");
                                    imgUrl = "@drawable/iceland";
                                    String img = unLivre.getString("imgPrincipale");
                                    String lienImg = img.substring(0,img.length()-4);
                                    imgUrl = "@drawable/"+lienImg;
                                   map.put("img", String.valueOf(getResources().getIdentifier(imgUrl, "drawable", getPackageName())));
//
                                    map.put("idl", unLivre.getString("idEtape"));
                                    map.put("mtitre", unLivre.getString("nomEtape"));
                                    map.put("mauteur", unLivre.getString("dateEtape"));
                                    map.put("mannee", unLivre.getString("description"));
                                    //map.put("mpages", unLivre.getString(""));
                                    tabLivres.add(map);
                                }

                                SimpleAdapter monAdapter = new SimpleAdapter (ListerEtapesActivity.this, tabLivres, R.layout.lister_livres_map,
                                        new String[] {"img", "idl", "mtitre", "mauteur", "mannee"},
                                        new int[] {R.id.img, R.id.idl, R.id.mtitre, R.id.mauteur});
                                //loadImageFromURL(imgUrl,(ImageView)findViewById(R.id.img));
                                liste.setAdapter(monAdapter);
                            }
                            else{}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TextView idTV = (TextView)view.findViewById(R.id.idl);
                                String idS = idTV.getText().toString();
                                //Toast.makeText(ListerEtapesActivity.this, idS, Toast.LENGTH_SHORT).show();
                                listerJours(idS);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ListerEtapesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                params.put("nomTableALister", "etape");
                params.put("condition", "WHERE `idCircuit` ="+idCircuit);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }

    public void listerJours(String id){
        Intent IntLister = new Intent(ListerEtapesActivity.this, ListerJoursActivity.class);
        IntLister.putExtra("idEtape",id);
        startActivity(IntLister);
    }
}
