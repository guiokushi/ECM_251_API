package maua.br.plugins

import br.maua.dao.AvaliacaoDAO
import br.maua.dao.JogoDAO
import br.maua.dao.ReviewDAO
import br.maua.dao.UsuarioDAO
import br.maua.models.Avaliacao
import br.maua.models.Jogo
import br.maua.models.Review
import br.maua.models.Usuario
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            }
    }
    install(CallLogging)
    routing {
        get("/json/gson") {
                call.respond(mapOf("hello" to "world"))
            }
        //Pegar tabela
        get("/tabela/{tabela}"){
            val tabela = call.parameters["tabela"]
            when(tabela){
                "avaliacao" -> {
                    val avaliacaoDAO = AvaliacaoDAO()
                    var avaliacoes = avaliacaoDAO.pegarTodos()
                    call.respond(avaliacoes)
                }
                "jogo" -> {
//                    call.respondText("Tabela Jogo")
                    val jogoDAO = JogoDAO()
                    var jogos = jogoDAO.pegarTodos()
                    call.respond(jogos)

                }
                "review" -> {
//                    call.respondText("Tabela Review")
                    val reviewDAO = ReviewDAO()
                    var reviews = reviewDAO.pegarTodos()
                    call.respond(reviews)
                }
                "usuario" -> {
//                    call.respondText("Tabela Usuario")
                    val usuarioDAO = UsuarioDAO()
                    var usuarios = usuarioDAO.pegarTodos()
                    call.respond(usuarios)
                }

            }

        }
        //Pegar um id da database
        get("/tabela/{tabela}/{id}"){
            val tabela = call.parameters["tabela"]
            when(tabela){
                "avaliacao" -> {
//                    call.respondText("Tabela Avaliação")
                    val avaliacao = AvaliacaoDAO()
                    val id = call.parameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Esse id não pode ser localizado")
                    } else{
                        var resposta = avaliacao.pegarUm(id)
                        call.respond(resposta)
                    }
                }
                "jogo" -> {
//                    call.respondText("Tabela Jogo")
                    val jogo = JogoDAO()
                    val id = call.parameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Esse id não pode ser localizado")
                    } else{
                        var resposta = jogo.pegarUm(id)
                        call.respond(resposta)
                    }

                }
                "review" -> {
//                    call.respondText("Tabela Review")
                    val review = ReviewDAO()
                    val id = call.parameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Esse id não pode ser localizado")
                    } else{
                        var resposta = review.pegarUm(id)
                        call.respond(resposta)
                    }
                }
                "usuario" -> {
//                    call.respondText("Tabela Usuario")
                    val usuario = UsuarioDAO()
                    val id = call.parameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Esse id não pode ser localizado")
                    } else{
                        var resposta = usuario.pegarUm(id)
                        call.respond(resposta)
                    }
                }
            }
        }
        //Criar entrada para database
        get("/criar/{tabela}"){
            val tabela = call.parameters["tabela"]
            when(tabela) {
                "avaliacao" -> {
                    val avaliacaoDAO = AvaliacaoDAO()

                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val avaliacao = call.request.queryParameters["avaliacao"]?.toIntOrNull()
                    val idReview = call.request.queryParameters["idreview"]?.toIntOrNull()
                    val data_de_postagem = call.request.queryParameters["datadepostagem"]?.toIntOrNull()
                    if(id == null || avaliacao == null || idReview == null || data_de_postagem == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(avaliacaoDAO.inserirUm(Avaliacao(id!!,avaliacao!!,idReview!!,data_de_postagem!!)))
                    }
                }
                "jogo" -> {
//                    call.respondText("Tabela Jogo")
                    val jogoDAO = JogoDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val nome = call.request.queryParameters["nome"]
                    val empresa = call.request.queryParameters["empresa"]
                    val plataforma = call.request.queryParameters["plataforma"]
                    val data_de_publicação = call.request.queryParameters["datadepublicacao"]?.toIntOrNull()
                    if(id == null || nome == "" || empresa == "" || data_de_publicação == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(jogoDAO.inserirUm(Jogo(id!!,nome!!,empresa!!,plataforma!!,data_de_publicação!!)))
                    }

                }
                "review" -> {
//                    call.respondText("Tabela Review")
                    val review = ReviewDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val texto = call.request.queryParameters["texto"]
                    val idJogo = call.request.queryParameters["idjogo"]?.toIntOrNull()
                    val idUser = call.request.queryParameters["iduser"]?.toIntOrNull()
                    if(id == null || texto.isNullOrBlank() || idJogo == null || idUser == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(review.inserirUm(Review(id!!,texto!!,idJogo!!,idUser!!)))
                    }
                }
                "usuario" -> {
//                    call.respondText("Tabela Usuario")
                    val usuarioDAO = UsuarioDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val nome = call.request.queryParameters["nome"]
                    val email = call.request.queryParameters["email"]
                    val senha = call.request.queryParameters["senha"]
                    if(id == null || nome.isNullOrBlank() || email.isNullOrBlank() || senha.isNullOrBlank()){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(usuarioDAO.inserirUm(Usuario(id!!,nome!!,email!!,senha!!)))
                    }
                }
            }
        }
        //Update
        get("/update/{tabela}"){
            val tabela = call.parameters["tabela"]
            when(tabela) {
                "avaliacao" -> {
                    val avaliacaoDAO = AvaliacaoDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val avaliacao = call.request.queryParameters["avaliacao"]?.toIntOrNull()
                    val idReview = call.request.queryParameters["idreview"]?.toIntOrNull()
                    val data_de_postagem = call.request.queryParameters["datadepostagem"]?.toIntOrNull()
                    if(id == null || avaliacao == null || idReview == null || data_de_postagem == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(avaliacaoDAO.atualizar(Avaliacao(id!!,avaliacao!!,idReview!!,data_de_postagem!!)))
                    }
                }
                "jogo" -> {
//                    call.respondText("Tabela Jogo")
                    val jogoDAO = JogoDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val nome = call.request.queryParameters["nome"]
                    val empresa = call.request.queryParameters["empresa"]
                    val plataforma = call.request.queryParameters["plataforma"]
                    val data_de_publicação = call.request.queryParameters["datadepublicacao"]?.toIntOrNull()
                    if(id == null || nome == "" || empresa == "" || data_de_publicação == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(jogoDAO.atualizar(Jogo(id!!,nome!!,empresa!!,plataforma!!,data_de_publicação!!)))
                    }

                }
                "review" -> {
//                    call.respondText("Tabela Review")
                    val review = ReviewDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val texto = call.request.queryParameters["texto"]
                    val idJogo = call.request.queryParameters["idjogo"]?.toIntOrNull()
                    val idUser = call.request.queryParameters["iduser"]?.toIntOrNull()
                    if(id == null || texto.isNullOrBlank() || idJogo == null || idUser == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(review.atualizar(Review(id!!,texto!!,idJogo!!,idUser!!)))
                    }
                }
                "usuario" -> {
//                    call.respondText("Tabela Usuario")
                    val usuarioDAO = UsuarioDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    val nome = call.request.queryParameters["nome"]
                    val email = call.request.queryParameters["email"]
                    val senha = call.request.queryParameters["senha"]
                    if(id == null || nome.isNullOrBlank() || email.isNullOrBlank() || senha.isNullOrBlank()){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(usuarioDAO.atualizar(Usuario(id!!,nome!!,email!!,senha!!)))
                    }
                }
            }
        }
        //Delete
        get("/deletar/{tabela}"){
            val tabela = call.parameters["tabela"]
            when(tabela) {
                "avaliacao" -> {
                    val avaliacaoDAO = AvaliacaoDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(avaliacaoDAO.deletar(id))
                    }
                }
                "jogo" -> {
//                    call.respondText("Tabela Jogo")
                    val jogoDAO = JogoDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(jogoDAO.deletar(id))
                    }

                }
                "review" -> {
//                    call.respondText("Tabela Review")
                    val review = ReviewDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(review.deletar(id))
                    }
                }
                "usuario" -> {
//                    call.respondText("Tabela Usuario")
                    val usuarioDAO = UsuarioDAO()
                    val id = call.request.queryParameters["id"]?.toIntOrNull()
                    if(id == null){
                        call.respond(HttpStatusCode.BadRequest,"Ocorreu um erro na operação")
                    }else{
                        call.respond(usuarioDAO.deletar(id))
                    }
                }
            }
        }

    }
}
