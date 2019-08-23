package com.example.crudexemplo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    EditText editFullname, editNome, editPhone, editPassword, editEmail, editId;
    Button btnNovo, btnSalvar, btnExcluir, btnDraw;
    ListView listViewContatos;
    public final static String EXTRA_MESSAGE = " ";


    private String HOST = "http://openfix.000webhostapp.com/crud/";

    private int itemClicado;

    ContatosAdapter contatosAdapter;
    List<Contato> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.editId);
        editFullname = findViewById(R.id.editFullname);
        editNome = findViewById(R.id.editNome);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);

        btnNovo = findViewById(R.id.btnNovo);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);
        btnDraw = findViewById(R.id.btnDraw);


        listViewContatos = (ListView) findViewById(R.id.ListViewContatos);

        lista = new ArrayList<Contato>();
        contatosAdapter = new ContatosAdapter(MainActivity.this,lista);
        listViewContatos.setAdapter(contatosAdapter);
        listaContatos();

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                limpaCampos();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = editId.getText().toString();
                final String fullname = editFullname.getText().toString();
                final String nome = editNome.getText().toString();
                final String password = editPassword.getText().toString();
                final String phone = editPhone.getText().toString();
                final String email = editEmail.getText().toString();

                if(nome.isEmpty()){
                    editNome.setError("O Nome é obrigatório");

                } else if (id.isEmpty()){

                    Ion.with(MainActivity.this)
                            .load(HOST + "create.php")
                            .setBodyParameter("fullname",fullname)
                            .setBodyParameter("username",nome)
                            .setBodyParameter("password",password)
                            .setBodyParameter("phone",phone)
                            .setBodyParameter("email",email)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    // do stuff with the result or error
                                        if(result.get("CREATE").getAsString().equals("OK")){

                                            int idRetornado = Integer.parseInt(result.get("ID").getAsString());

                                            Contato c = new Contato();
                                            c.setId(idRetornado);
                                            c.setFullname(fullname);
                                            c.setUsername(nome);
                                            c.setPassword(password);
                                            c.setPhone(phone);
                                            c.setEmail(email);

                                            lista.add(c);

                                            limpaCampos();

                                            Toast.makeText(MainActivity.this, "Salvo com sucesso, name" + idRetornado,Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(MainActivity.this,"Ocorreu um erro ao salvar", Toast.LENGTH_LONG).show();
                                        }
                                }
                            });
                }else{
                    //Toast.makeText(MainActivity.this, "erro" ,Toast.LENGTH_LONG).show();

                    Ion.with(MainActivity.this)
                            .load(HOST + "update.php")
                            .setBodyParameter("id",id)
                            .setBodyParameter("fullname",fullname)
                            .setBodyParameter("username",nome)
                            .setBodyParameter("password",password)
                            .setBodyParameter("phone",phone)
                            .setBodyParameter("email",email)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    // do stuff with the result or error
                                    if(result.get("UPDATE").getAsString().equals("OK")){

                                        Contato c = new Contato();
                                        c.setId(Integer.parseInt(id));
                                        c.setFullname(fullname);
                                        c.setUsername(nome);
                                        c.setPassword(password);
                                        c.setPhone(phone);
                                        c.setEmail(email);

                                        lista.set(itemClicado,c);

                                        limpaCampos();

                                        Toast.makeText(MainActivity.this, "Atualizado com sucesso, name" ,Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this,"Ocorreu um erro ao atualizar", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });


        btnExcluir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id = editId.getText().toString();

                if(id.isEmpty()){

                    Toast.makeText(MainActivity.this,"Nenhum contato selecionado", Toast.LENGTH_LONG).show();

                }else{

                    Ion.with(MainActivity.this)
                            .load(HOST + "delete.php")
                            .setBodyParameter("id",id)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    // do stuff with the result or error
                                    if(result.get("DELETE").getAsString().equals("OK")){

                                        lista.remove(itemClicado);

                                        contatosAdapter.notifyDataSetChanged();

                                        Toast.makeText(MainActivity.this, "Excluido com sucesso, name",Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this,"Ocorreu um erro ao Excluir", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }

        });
//
        btnDraw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Sketch.class);
                startActivity(it);
            }
        });
//*/

       //Ação de enviar os dados para a activity.
        listViewContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contato c = (Contato) adapterView.getAdapter().getItem(i);
                editId.setText(String.valueOf(c.getId()));
                editFullname.setText(c.getFullname());
                editNome.setText(c.getUsername());
                editPassword.setText(c.getPassword());
                editPhone.setText(c.getPhone());
                editEmail.setText(c.getEmail());

                itemClicado = i;
            }
        });

    }


    public void startSecondActivity(View view){

        Intent intent = new Intent(this, Sketch.class);

        //Catch information from TextView.
        //TextView textView = (TextView) findViewById(R.id.editId);
        //String message = textView.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private void listaContatos()
    {
        String url = HOST + "read.php";

        Ion.with(getBaseContext())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        for(int i = 0; i < result.size(); i++)
                        {
                            JsonObject obj = result.get(i).getAsJsonObject();
                            Contato c = new Contato();
                            c.setId(obj.get("id").getAsInt());
                            c.setFullname(obj.get("fullname").getAsString());
                            c.setUsername(obj.get("username").getAsString());
                            c.setPassword(obj.get("password").getAsString());
                            c.setPhone(obj.get("phone").getAsString());
                            c.setEmail(obj.get("email").getAsString());

                            lista.add(c);
                }
                        contatosAdapter.notifyDataSetChanged();
            }
        });
    }

    public void limpaCampos(){
        editId.setText("");
        editFullname.setText("");
        editNome.setText("");
        editPassword.setText("");
        editPhone.setText("");
        editEmail.setText("");

        editFullname.requestFocus();
    }
}
