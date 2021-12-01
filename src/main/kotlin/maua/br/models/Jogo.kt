package br.maua.models

data class Jogo(
    val id:Int,
    val nome: String,
    val empresa: String,
    val plataforma: String,
    val data_de_publicacao: Int
)
