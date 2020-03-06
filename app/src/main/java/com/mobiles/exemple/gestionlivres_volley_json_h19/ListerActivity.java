package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListerActivity extends AppCompatActivity { //implements View.OnClickListener {
    ListView liste;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister);
        liste=(ListView) findViewById(R.id.liste);
        iv = new ImageView(this);
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
                                    map.put("idl", unLivre.getString("idCircuit"));
                                    map.put("mtitre", unLivre.getString("nomCircuit"));
                                    map.put("mauteur", unLivre.getString("duree")+" jours");
                                    map.put("mannee", unLivre.getString("dateDebutCircuit"));
                                    map.put("mpages", "$"+unLivre.getString("prixCircuit"));
                                    tabLivres.add(map);
                                }

                                SimpleAdapter monAdapter = new SimpleAdapter (ListerActivity.this, tabLivres, R.layout.lister_livres_map,
                                        new String[] {"img", "idl", "mtitre", "mauteur", "mannee", "mpages"},
                                        new int[] {R.id.img, R.id.idl, R.id.mtitre, R.id.mauteur, R.id.mannee, R.id.mpages});
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
                                //Toast.makeText(ListerActivity.this, idS, Toast.LENGTH_SHORT).show();
                                listerEtapes(idS);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ListerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                params.put("nomTableALister", "circuit");
                params.put("condition", "WHERE `publier` =1");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }

    public void listerEtapes(String id){
        Intent IntLister = new Intent(ListerActivity.this, ListerEtapesActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("idEtape",id);
        IntLister.putExtra("idCircuit",id);
        startActivity(IntLister);
    }
}
