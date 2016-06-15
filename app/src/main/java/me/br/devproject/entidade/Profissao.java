package me.br.devproject.entidade;

/**
 * Created by ricks on 15/06/2016.
 */
public enum Profissao {

    ARQUITETO("Arquiteto de Software"),
    PEDREIRO("Arquiteto"),
    PROFESSOR("Professor"),
    DESENVOLVEDOR("Desenvolvedor"),
    ENGENHEIRO("Engenheiro"),
    ADVOGADO("Advogado");

    private Profissao(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

}