package br.maua.dao

interface GenericoDAO {
    fun pegarUm(id:Int): Any;
    fun pegarTodos() : List<Any>;
    fun inserirUm(objeto : Any) : Unit;
    fun atualizar(objeto : Any) : Unit;
    fun deletar(id : Int) : Unit;
}