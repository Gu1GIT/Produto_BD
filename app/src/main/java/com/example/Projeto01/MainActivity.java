package com.example.Projeto01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Projeto01.database.DatabaseDBHelper;
import com.example.Projeto01.database.ProdutoDAO;
import com.example.Projeto01.modelo.Produto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");
        listViewProdutos = findViewById(R.id.listView_produtos);
        onClickListaProdutos();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtoDAO.Listar());
        listViewProdutos.setAdapter(adapterProdutos);
    }

    private void onClickListaProdutos() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoSelecionado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProduto.class);
                intent.putExtra("produtoSelecionado", produtoSelecionado);
                startActivity(intent);
            }
        });
    }

    public void onClickNovoProduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProduto.class);
        startActivity(intent);
    }

}