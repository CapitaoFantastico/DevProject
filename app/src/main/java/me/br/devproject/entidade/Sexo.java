package me.br.devproject.entidade;

/**
 * Created by ricks on 22/06/2016.
 */
public enum Sexo {

    FEMININO, MASCULINO;

    public static Sexo getSexo(int pos){
        for(Sexo sexo : Sexo.values()){
            if (sexo.ordinal() == pos){
                return sexo;
            }
        }
        return null;
    }
}
