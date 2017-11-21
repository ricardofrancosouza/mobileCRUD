package com.brasilmilk.crud;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String CATEGORIA = "MainActivity";
    ImageButton imgCadastrar, imgAlterar, imgExcluir, imgPesqInd, imgListar;
    EditText edtIdVendedor, edtNome, edtAtivo, edtTipo;
    ListView listViewVendedores;
    ModelVendedor objModelVendedor;
    List<ModelVendedor> objListaVendedores;
    DAOVendedor objDaoVendedor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        imgCadastrar = (ImageButton) findViewById(R.id.imgCad);
        imgCadastrar.setOnClickListener(this);

        imgAlterar = (ImageButton) findViewById(R.id.imgAlterar);
        imgAlterar.setOnClickListener(this);

        imgExcluir = (ImageButton) findViewById(R.id.imgExcluir);
        imgExcluir.setOnClickListener(this);

        imgListar = (ImageButton) findViewById(R.id.imgListar);
        imgListar.setOnClickListener(this);

        imgPesqInd = (ImageButton) findViewById(R.id.imgPesq);
        imgPesqInd.setOnClickListener(this);

        edtIdVendedor = (EditText) findViewById(R.id.edtID);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTipo = (EditText) findViewById(R.id.edtTipo);
        edtAtivo = (EditText) findViewById(R.id.edtAtivo);

        listViewVendedores = (ListView) findViewById(R.id.listViewVendedor);
        listViewVendedores.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        objDaoVendedor = new DAOVendedor(this);
        if(view == imgCadastrar){
            long id = objDaoVendedor.insertVendedor(recuperarDadosCampos());
            limparCampos(objDaoVendedor);
            Toast.makeText(this, "Vendedor Cadastrado", Toast.LENGTH_LONG).show();
        }
        if(view == imgListar){
            preencherListViewVendedores(objDaoVendedor.listVendedores());
            limparCampos();
        }
        if(view == imgAlterar){
            if(edtIdVendedor.length() >0){
                boolean update = objDaoVendedor.alterarVendedor(recuperarDadosCampos());
                limparCampos();
                preencherListViewVendedores(objDaoVendedor.listVendedores());
                if(update == true){
                    Toast.makeText(this,"Cadastro Alterado", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Selecionar um cadastro", Toast.LENGTH_LONG).show();
                }
            }
        }
        if(view == imgExcluir){
            if(edtIdVendedor.length()>0){
                boolean delete = objDaoVendedor.excluirVendedor(edtIdVendedor.getText().toString());
                limparCampos(objDaoVendedor);
                if(delete){
                    Toast.makeText(this,"Cadastro Excluido", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Selecione um cadastro", Toast.LENGTH_LONG).show();
                }
            }
        }
        if(view == imgPesqInd){
            String vendedorNome = edtNome.getText().toString();
            if(vendedorNome.length()>0){
                objDaoVendedor = new DAOVendedor(this);
                objModelVendedor = objDaoVendedor.buscaIndividualVendedor(vendedorNome);
                setarCampos(objModelVendedor);
            }else{
                Toast.makeText(this,"Informar nome para pesquisa", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String vendedorNome = parent.getItemAtPosition(position).toString();
        objDaoVendedor = new DAOVendedor(this);
        objModelVendedor = objDaoVendedor.buscaIndividualVendedor(vendedorNome);
        setarCampos(objModelVendedor);
    }

    private void setarCampos(ModelVendedor vendedor) {
        edtIdVendedor.setText(Integer.toString(vendedor.getID_VENDEDOR()));
        edtNome.setText(vendedor.getNOME());
        edtAtivo.setText(vendedor.getATIVO());
        edtTipo.setText(vendedor.getTIPO());
    }
    private void limparCampos(){
        edtIdVendedor.setText("");
        edtNome.setText("");
        edtAtivo.setText("");
        edtTipo.setText("");
    }
    private void limparCampos(final DAOVendedor objDaoVendedor){
        edtIdVendedor.setText("");
        edtNome.setText("");
        edtAtivo.setText("");
        edtTipo.setText("");
        preencherListViewVendedores(objDaoVendedor.listVendedores());
    }

    private void preencherListViewVendedores(List<ModelVendedor> listaVendedores){
        String[] listNomeVendedores = new String[listaVendedores.size()];
        for(int i = 0; i< listaVendedores.size(); i++){
            listNomeVendedores[i] = listaVendedores.get(i).getNOME();
        }
        ArrayAdapter<String> adapterListaVendedores =
                new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listNomeVendedores);
        listViewVendedores.setAdapter(adapterListaVendedores);
    }
    private ModelVendedor recuperarDadosCampos(){
        objModelVendedor = new ModelVendedor();
        try {
            if(edtIdVendedor.length()>0){
                objModelVendedor.setID_VENDEDOR(Integer.parseInt(edtIdVendedor.getText().toString()));
            }else{

            }
            objModelVendedor.setNOME(edtNome.getText().toString());
            objModelVendedor.setTIPO(edtTipo.getText().toString());
            objModelVendedor.setATIVO(edtAtivo.getText().toString());
        }catch (Exception e){
            Log.e("Erro: ", e.getMessage());
        }
        return objModelVendedor;
    }
}
