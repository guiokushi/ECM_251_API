package br.maua.dao

import br.maua.models.Avaliacao


class AvaliacaoDAO : GenericoDAO{
    override fun pegarUm(id: Int): Any {
        var avaliacao : Avaliacao? = null
        //Cria uma conexão com o banco
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Avaliacao WHERE id = ${id};")
            while(resultSet?.next()!!){
                avaliacao = Avaliacao(
                    resultSet.getInt("id"),
                    resultSet.getInt("avaliacao"),
                    resultSet.getInt("idReview"),
                    resultSet.getInt("data_de_postagem")
                )
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }finally {
            connection?.close()
        }
        return avaliacao!!
    }

    override fun pegarTodos(): List<Any> {
        val avaliacao = mutableListOf<Avaliacao>()
        var connection : ConnectionDAO? = null
        try {
            //Cria uma conexão com o banco
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Avaliacao;")
            //Intera pelo resultado obtido
            while (resultSet?.next()!!) {
                avaliacao.add(
                    Avaliacao(
                        resultSet.getInt("id"),
                        resultSet.getInt("avaliacao"),
                        resultSet.getInt("idReview"),
                        resultSet.getInt("data_de_postagem")
                    )
                )
            }

        }
        catch (exception:Exception){
            exception.printStackTrace()
        }
        finally {
            connection?.close()
        }
        return avaliacao
    }

    override fun inserirUm(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            INSERT INTO Avaliacao 
            (avaliacao, idReview, data_de_postagem) 
            VALUES (?,?,?);
            """.trimMargin())
        val avaliacao = objeto as Avaliacao
        preparedStatement?.setInt(1,avaliacao.avaliacao)
        preparedStatement?.setInt(2,avaliacao.idReview)
        preparedStatement?.setInt(3,avaliacao.data_de_postagem)
        preparedStatement?.executeUpdate()
        //Banco está em auto-commit()

        connectionDAO.close()
    }

    override fun atualizar(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            UPDATE Avaliacao 
            SET avaliacao = ?, idReview = ?, data_de_postagem = ? 
            WHERE id = ?;
            """.trimMargin())
        val avaliacao = objeto as Avaliacao
        preparedStatement?.setInt(1, avaliacao.avaliacao)
        preparedStatement?.setInt(2, avaliacao.idReview)
        preparedStatement?.setInt(3, avaliacao.data_de_postagem)
        preparedStatement?.setInt(4,avaliacao.id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }

    override fun deletar(id: Int) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            DELETE FROM Avaliacao  
            WHERE id = ?;
            """.trimMargin())
        preparedStatement?.setInt(1,id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }
}