package com.esprit.android.ebi3.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.ArtisanatListActivity;
import com.esprit.android.ebi3.CustomRequest;
import com.esprit.android.ebi3.R;
import com.esprit.android.ebi3.ServiceListActivity;
import com.esprit.android.ebi3.models.Produit;
import com.esprit.android.ebi3.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Hassan on 08/02/16.
 */


public class SwipeCategorieServiceAdapter extends PagerAdapter {

    private Context ctx ;
    private LayoutInflater layoutInflater;
    public static boolean touched=false;
    private List<Produit> imgList1,imgList2,imgList3;
    RequestQueue requestQueue1;
    String showUrl = "http://www.e-bi3.com/server/getproductbyuserCategorie.php";
    public SwipeCategorieServiceAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater =(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.categorie_swipe,container,false);
      final Button first = (Button) item_view.findViewById(R.id.firsticone);
      final  Button second = (Button) item_view.findViewById(R.id.secondicone);
      final  Button third = (Button) item_view.findViewById(R.id.thirdicone);
        final  Button forth = (Button) item_view.findViewById(R.id.forthicone);
        TextView ftext = (TextView) item_view.findViewById(R.id.firsttext);
        TextView stext = (TextView) item_view.findViewById(R.id.secondtext);
        TextView ttext = (TextView) item_view.findViewById(R.id.thirdtext);
        TextView fotext = (TextView) item_view.findViewById(R.id.forthtext);
        if(position==0)
        {
            third.setVisibility(View.VISIBLE);
            forth.setVisibility(View.VISIBLE);
            first.setBackgroundResource(R.drawable.cleaning);
            second.setBackgroundResource(R.drawable.repare);
            third.setBackgroundResource(R.drawable.babysit);
            forth.setBackgroundResource(R.drawable.book);
            ftext.setText("Ménage");
            stext.setText("Réparation");
            ttext.setText("Babysitting");
            fotext.setText("Education");


            first.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    imgList2 = new ArrayList<Produit>();

                  // Save new user data into Parse.com Data Storage
                    touched = true;
                    first.setBackgroundResource(R.drawable.cleaningcouleur);
                    second.setBackgroundResource(R.drawable.repare);
                    third.setBackgroundResource(R.drawable.babysit);
                    forth.setBackgroundResource(R.drawable.book);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category", "Services");
                    params.put("SubCategory", "Menage");


                    /////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

                    requestQueue1 = Volley.newRequestQueue(ctx);
                    //System.out.println("ww");
                    final ProgressDialog loading = ProgressDialog.show(ctx, "Chargement...", "Un petit moment svp...", false, false);

                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = listproduit.length()-1; i > 0; i--) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);


                                    imgList2.add(new Produit(
                                            nourriture.getString("ProductImage1"),
                                            nourriture.getString("ProductImage2"),
                                            nourriture.getString("ProductImage3"),
                                            nourriture.getString("ProductImage1Title"),
                                            nourriture.getString("ProductImage2Title"),
                                            nourriture.getString("ProductImage3Title"),
                                            nourriture.getString("ProductImage1Desc"),
                                            nourriture.getString("ProductImage2Desc"),
                                            nourriture.getString("ProductImage3Desc"),
                                            nourriture.getString("name"),
                                            nourriture.getString("description"),
                                            nourriture.getString("place"),
                                            nourriture.getString("quantity"),
                                            nourriture.getString("date"),
                                            (float) nourriture.getInt("price"),

                                            new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                    nourriture.getString("Description"),
                                                    "Jobs",
                                                    "Tunisie",
                                                    nourriture.getString("adresse"),
                                                    nourriture.getString("tel"),
                                                    nourriture.getString("email"),
                                                    nourriture.getString("ImagePath"))
                                    ));
                                    // System.out.println(ListNourriture.size() + " my size");
                                    //  System.out.println(nourriture.getInt("Id")+" zzzzz");


                                }


                                // System.out.println(ListNourriture.size()+" this is size");
                                ServiceListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());
                            loading.dismiss();

                        }
                    });
                    requestQueue1.add(jsObjRequest);


                    // Save new user data into Parse.com Data Storage

                    return false;

                }
            });

            second.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    imgList2 = new ArrayList<Produit>();

                // Save new user data into Parse.com Data Storage
                    touched = true;
                    first.setBackgroundResource(R.drawable.cleaning);
                    second.setBackgroundResource(R.drawable.reparecouleur);
                    third.setBackgroundResource(R.drawable.babysit);
                    forth.setBackgroundResource(R.drawable.book);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category", "Services");
                    params.put("SubCategory", "Reparation");


                    /////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

                    requestQueue1 = Volley.newRequestQueue(ctx);
                    //System.out.println("ww");
                    final ProgressDialog loading = ProgressDialog.show(ctx,"Chargement...","Un petit moment svp...",false,false);

                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = listproduit.length()-1; i > 0; i--) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);





                                    imgList2.add(new Produit(
                                            nourriture.getString("ProductImage1"),
                                            nourriture.getString("ProductImage2"),
                                            nourriture.getString("ProductImage3"),
                                            nourriture.getString("ProductImage1Title"),
                                            nourriture.getString("ProductImage2Title"),
                                            nourriture.getString("ProductImage3Title"),
                                            nourriture.getString("ProductImage1Desc"),
                                            nourriture.getString("ProductImage2Desc"),
                                            nourriture.getString("ProductImage3Desc"),
                                            nourriture.getString("name"),
                                            nourriture.getString("description"),
                                            nourriture.getString("place"),
                                            nourriture.getString("quantity"),
                                            nourriture.getString("date"),
                                            (float) nourriture.getInt("price"),

                                            new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                    nourriture.getString("Description"),
                                                    "Jobs",
                                                    "Tunisie",
                                                    nourriture.getString("adresse"),
                                                    nourriture.getString("tel"),
                                                    nourriture.getString("email"),
                                                    nourriture.getString("ImagePath"))
                                    ));
                                    // System.out.println(ListNourriture.size() + " my size");
                                    //  System.out.println(nourriture.getInt("Id")+" zzzzz");





                                }








                                // System.out.println(ListNourriture.size()+" this is size");
                                ServiceListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());
                            loading.dismiss();

                        }
                    });
                    requestQueue1.add(jsObjRequest);


                    return false;
                }
            });

            third.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    imgList2 = new ArrayList<Produit>();

                 // Save new user data into Parse.com Data Storage
                    touched = true;
                    first.setBackgroundResource(R.drawable.cleaning);
                    second.setBackgroundResource(R.drawable.repare);
                    third.setBackgroundResource(R.drawable.babysitcouleur);
                    forth.setBackgroundResource(R.drawable.book);

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category", "Services");
                    params.put("SubCategory", "Babysitting");


                    /////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

                    requestQueue1 = Volley.newRequestQueue(ctx);
                    //System.out.println("ww");
                    final ProgressDialog loading = ProgressDialog.show(ctx, "Chargement...", "Un petit moment svp...", false, false);

                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = listproduit.length()-1; i > 0; i--) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);


                                    imgList2.add(new Produit(
                                            nourriture.getString("ProductImage1"),
                                            nourriture.getString("ProductImage2"),
                                            nourriture.getString("ProductImage3"),
                                            nourriture.getString("ProductImage1Title"),
                                            nourriture.getString("ProductImage2Title"),
                                            nourriture.getString("ProductImage3Title"),
                                            nourriture.getString("ProductImage1Desc"),
                                            nourriture.getString("ProductImage2Desc"),
                                            nourriture.getString("ProductImage3Desc"),
                                            nourriture.getString("name"),
                                            nourriture.getString("description"),
                                            nourriture.getString("place"),
                                            nourriture.getString("quantity"),
                                            nourriture.getString("date"),
                                            (float) nourriture.getInt("price"),

                                            new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                    nourriture.getString("Description"),
                                                    "Jobs",
                                                    "Tunisie",
                                                    nourriture.getString("adresse"),
                                                    nourriture.getString("tel"),
                                                    nourriture.getString("email"),
                                                    nourriture.getString("ImagePath"))
                                    ));
                                    // System.out.println(ListNourriture.size() + " my size");
                                    //  System.out.println(nourriture.getInt("Id")+" zzzzz");


                                }


                                // System.out.println(ListNourriture.size()+" this is size");
                                ServiceListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());
                            loading.dismiss();

                        }
                    });
                    requestQueue1.add(jsObjRequest);

                    return false;
                }
            });


            forth.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    imgList2 = new ArrayList<Produit>();

                  // Save new user data into Parse.com Data Storage
                    touched = true;
                    first.setBackgroundResource(R.drawable.cleaning);
                    second.setBackgroundResource(R.drawable.repare);
                    third.setBackgroundResource(R.drawable.babysit);
                    forth.setBackgroundResource(R.drawable.bookcouleur);

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category", "Services");
                    params.put("SubCategory", "Education");


                    /////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

                    requestQueue1 = Volley.newRequestQueue(ctx);
                    //System.out.println("ww");
                    final ProgressDialog loading = ProgressDialog.show(ctx,"Chargement...","Un petit moment svp...",false,false);

                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = listproduit.length()-1; i > 0; i--) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);





                                    imgList2.add(new Produit(
                                            nourriture.getString("ProductImage1"),
                                            nourriture.getString("ProductImage2"),
                                            nourriture.getString("ProductImage3"),
                                            nourriture.getString("ProductImage1Title"),
                                            nourriture.getString("ProductImage2Title"),
                                            nourriture.getString("ProductImage3Title"),
                                            nourriture.getString("ProductImage1Desc"),
                                            nourriture.getString("ProductImage2Desc"),
                                            nourriture.getString("ProductImage3Desc"),
                                            nourriture.getString("name"),
                                            nourriture.getString("description"),
                                            nourriture.getString("place"),
                                            nourriture.getString("quantity"),
                                            nourriture.getString("date"),
                                            (float) nourriture.getInt("price"),

                                            new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                    nourriture.getString("Description"),
                                                    "Jobs",
                                                    "Tunisie",
                                                    nourriture.getString("adresse"),
                                                    nourriture.getString("tel"),
                                                    nourriture.getString("email"),
                                                    nourriture.getString("ImagePath"))
                                    ));
                                    // System.out.println(ListNourriture.size() + " my size");
                                    //  System.out.println(nourriture.getInt("Id")+" zzzzz");





                                }








                                // System.out.println(ListNourriture.size()+" this is size");
                                ServiceListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());
                            loading.dismiss();

                        }
                    });
                    requestQueue1.add(jsObjRequest);
                    return false;
                }
            });

        }

        if(position==1)
        {
            first.setBackgroundResource(R.drawable.cooking);
            second.setBackgroundResource(R.drawable.securite);
            third.setVisibility(View.GONE);
            forth.setVisibility(View.GONE);
            third.setBackgroundResource(R.drawable.mmaison);
            forth.setBackgroundResource(R.drawable.epice);
            ftext.setText("Cuisine");
            stext.setText("Securité");
            ttext.setText("");
            fotext.setText("");


            first.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    imgList2 = new ArrayList<Produit>();

                  // Save new user data into Parse.com Data Storage
                    touched = true;
                    first.setBackgroundResource(R.drawable.cookingcouleur);
                    second.setBackgroundResource(R.drawable.securite);
                    third.setBackgroundResource(R.drawable.mmaison);
                    forth.setBackgroundResource(R.drawable.epice);

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category", "Services");
                    params.put("SubCategory", "Cuisine");


                    /////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

                    requestQueue1 = Volley.newRequestQueue(ctx);
                    //System.out.println("ww");
                    final ProgressDialog loading = ProgressDialog.show(ctx,"Chargement...","Un petit moment svp...",false,false);

                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = listproduit.length()-1; i > 0; i--) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);





                                    imgList2.add(new Produit(
                                            nourriture.getString("ProductImage1"),
                                            nourriture.getString("ProductImage2"),
                                            nourriture.getString("ProductImage3"),
                                            nourriture.getString("ProductImage1Title"),
                                            nourriture.getString("ProductImage2Title"),
                                            nourriture.getString("ProductImage3Title"),
                                            nourriture.getString("ProductImage1Desc"),
                                            nourriture.getString("ProductImage2Desc"),
                                            nourriture.getString("ProductImage3Desc"),
                                            nourriture.getString("name"),
                                            nourriture.getString("description"),
                                            nourriture.getString("place"),
                                            nourriture.getString("quantity"),
                                            nourriture.getString("date"),
                                            (float) nourriture.getInt("price"),

                                            new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                    nourriture.getString("Description"),
                                                    "Jobs",
                                                    "Tunisie",
                                                    nourriture.getString("adresse"),
                                                    nourriture.getString("tel"),
                                                    nourriture.getString("email"),
                                                    nourriture.getString("ImagePath"))
                                    ));
                                    // System.out.println(ListNourriture.size() + " my size");
                                    //  System.out.println(nourriture.getInt("Id")+" zzzzz");





                                }








                                // System.out.println(ListNourriture.size()+" this is size");
                                ServiceListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());
                            loading.dismiss();

                        }
                    });
                    requestQueue1.add(jsObjRequest);
                    return false;
                }
            });

            second.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    imgList2 = new ArrayList<Produit>();

                 // Save new user data into Parse.com Data Storage
                    touched = true;
                    first.setBackgroundResource(R.drawable.cooking);
                    second.setBackgroundResource(R.drawable.securitecouleur);
                    third.setBackgroundResource(R.drawable.mmaison);
                    forth.setBackgroundResource(R.drawable.epice);

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category", "Services");
                    params.put("SubCategory", "Securite");


                    /////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

                    requestQueue1 = Volley.newRequestQueue(ctx);
                    //System.out.println("ww");
                    final ProgressDialog loading = ProgressDialog.show(ctx,"Chargement...","Un petit moment svp...",false,false);

                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = listproduit.length()-1; i > 0; i--) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);





                                    imgList2.add(new Produit(
                                            nourriture.getString("ProductImage1"),
                                            nourriture.getString("ProductImage2"),
                                            nourriture.getString("ProductImage3"),
                                            nourriture.getString("ProductImage1Title"),
                                            nourriture.getString("ProductImage2Title"),
                                            nourriture.getString("ProductImage3Title"),
                                            nourriture.getString("ProductImage1Desc"),
                                            nourriture.getString("ProductImage2Desc"),
                                            nourriture.getString("ProductImage3Desc"),
                                            nourriture.getString("name"),
                                            nourriture.getString("description"),
                                            nourriture.getString("place"),
                                            nourriture.getString("quantity"),
                                            nourriture.getString("date"),
                                            (float) nourriture.getInt("price"),

                                            new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                    nourriture.getString("Description"),
                                                    "Jobs",
                                                    "Tunisie",
                                                    nourriture.getString("adresse"),
                                                    nourriture.getString("tel"),
                                                    nourriture.getString("email"),
                                                    nourriture.getString("ImagePath"))
                                    ));
                                    // System.out.println(ListNourriture.size() + " my size");
                                    //  System.out.println(nourriture.getInt("Id")+" zzzzz");





                                }








                                // System.out.println(ListNourriture.size()+" this is size");
                                ServiceListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());
                            loading.dismiss();

                        }
                    });
                    requestQueue1.add(jsObjRequest);
                    return false;
                }
            });

            third.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    imgList2 = new ArrayList<Produit>();

                    ArtisanatListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                    // Save new user data into Parse.com Data Storage
                    touched = true;
                    first.setBackgroundResource(R.drawable.bbois);
                    second.setBackgroundResource(R.drawable.gguitar);
                    third.setBackgroundResource(R.drawable.mmaisoncouleur);
                    forth.setBackgroundResource(R.drawable.epice);

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category", "Artisanat");
                    params.put("SubCategory", "Maison");


                    /////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

                    requestQueue1 = Volley.newRequestQueue(ctx);
                    //System.out.println("ww");
                    final ProgressDialog loading = ProgressDialog.show(ctx,"Chargement...","Un petit moment svp...",false,false);

                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = listproduit.length()-1; i > 0; i--) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);





                                    imgList2.add(new Produit(
                                            nourriture.getString("ProductImage1"),
                                            nourriture.getString("ProductImage2"),
                                            nourriture.getString("ProductImage3"),
                                            nourriture.getString("ProductImage1Title"),
                                            nourriture.getString("ProductImage2Title"),
                                            nourriture.getString("ProductImage3Title"),
                                            nourriture.getString("ProductImage1Desc"),
                                            nourriture.getString("ProductImage2Desc"),
                                            nourriture.getString("ProductImage3Desc"),
                                            nourriture.getString("name"),
                                            nourriture.getString("description"),
                                            nourriture.getString("place"),
                                            nourriture.getString("quantity"),
                                            nourriture.getString("date"),
                                            (float) nourriture.getInt("price"),

                                            new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                    nourriture.getString("Description"),
                                                    "Jobs",
                                                    "Tunisie",
                                                    nourriture.getString("adresse"),
                                                    nourriture.getString("tel"),
                                                    nourriture.getString("email"),
                                                    nourriture.getString("ImagePath"))
                                    ));
                                    // System.out.println(ListNourriture.size() + " my size");
                                    //  System.out.println(nourriture.getInt("Id")+" zzzzz");





                                }








                                // System.out.println(ListNourriture.size()+" this is size");
                                ArtisanatListActivity.nourlist.setAdapter(new ProduitCustomAdapter(ctx, R.layout.one_article_main, imgList2));
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());
                            loading.dismiss();

                        }
                    });
                    requestQueue1.add(jsObjRequest);
                    return false;
                }
            });

            forth.setVisibility(View.GONE);

        }



        container.addView(item_view);
        return item_view;







    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}