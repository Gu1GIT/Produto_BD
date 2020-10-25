package com.example.Projeto01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Projeto01.database.ProdutoDAO;
import com.example.Projeto01.modelo.Produto;

public class CadastroProduto extends AppCompatActivity {


    private boolean edicao = false;
    private boolean excluir = false;
    private int id = 0;
    private Float valor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produtos");
        carregarProduto();
    }

    private void carregarProduto() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("produtoSelecionado") != null) {
            Produto produto = (Produto) intent.getExtras().get("produtoSelecionado");
            EditText editTextNome = findViewById(R.id.editTextNome);
            EditText editTextValor = findViewById(R.id.editTextValor);
            editTextNome.setText(produto.getNome());
            editTextValor.setText(String.valueOf(produto.getValor()));
            id = produto.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        processar();
    }


    public void onClickExcluir(View v) {
        excluir = true;
        processar();
    }

    private void erroMensagem() {
        Toast.makeText(CadastroProduto.this, "É preciso preencher todos os campos", Toast.LENGTH_LONG).show();
    }


    private void processar() {

        EditText editTextNome = findViewById(R.id.editTextNome);
        EditText editTextValor = findViewById(R.id.editTextValor);

        String nome = editTextNome.getText().toString();
        String valorstring = editTextValor.getText().toString();
        if (!valorstring.isEmpty()) {
            valor = Float.parseFloat(valorstring);
        }
        if ((nome.isEmpty() || valorstring.isEmpty()) && !excluir) {
            erroMensagem();
            return;

        } else {

            Produto produto = new Produto(id, nome, valor);
            ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
            if (excluir) {
                boolean excluir = produtoDAO.excluir(produto);
            } else {
                boolean salvou = produtoDAO.salvar(produto);
                if (salvou) {
                } else {
                    Toast.makeText(CadastroProduto.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
                }
            }
            finish();
        }
    }
}