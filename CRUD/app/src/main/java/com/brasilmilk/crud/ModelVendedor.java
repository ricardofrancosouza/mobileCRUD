package com.brasilmilk.crud;



public class ModelVendedor {
    private Integer ID_VENDEDOR;
    private String NOME;
    private String ATIVO;
    private String TIPO;

    public Integer getID_VENDEDOR() {
        return ID_VENDEDOR;
    }

    public void setID_VENDEDOR(Integer ID_VENDEDOR) {
        this.ID_VENDEDOR = ID_VENDEDOR;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getATIVO() {
        return ATIVO;
    }

    public void setATIVO(String ATIVO) {
        this.ATIVO = ATIVO;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }
}
