package br.maua.dao

import br.maua.models.Jogo

class JogoDAO : GenericoDAO{
    override fun pegarUm(id: Int): Any {
        var jogo : Jogo? = null
        //Cria uma conexão com o banco
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Jogo WHERE id = ${id};")
            while(resultSet?.next()!!){
                jogo = Jogo(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("empresa"),
                    resultSet.getString("plataforma"),
                    resultSet.getInt("data_de_publicacao")
                )
                println("Jogo encontrado: ${jogo}")
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }finally {
            connection?.close()
        }
        return jogo!!

    }

    override fun pegarTodos(): List<Any> {
        val jogos = mutableListOf<Jogo>()
        var connection : ConnectionDAO? = null
        try {
            //Cria uma conexão com o banco
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Jogo;")
            //Intera pelo resultado obtido
            while (resultSet?.next()!!) {
                jogos.add(
                    Jogo(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("empresa"),
                        resultSet.getString("plataforma"),
                        resultSet.getInt("data_de_publicacao")
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
        return jogos
    }

    override fun inserirUm(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            INSERT INTO Jogo 
            (nome, empresa, plataforma,data_de_publicacao) 
            VALUES (?,?,?,?);
            """.trimMargin())
        val jogo = objeto as Jogo
        preparedStatement?.setString(1,jogo.nome)
        preparedStatement?.setString(2,jogo.empresa)
        preparedStatement?.setString(3,jogo.plataforma)
        preparedStatement?.setInt(4,jogo.data_de_publicacao)
        preparedStatement?.executeUpdate()
        //Banco está em auto-commit()

        connectionDAO.close()
    }
    override fun atualizar(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            UPDATE Jogo 
            SET nome = ?, empresa = ?, plataforma = ?, data_de_publicacao= ? 
            WHERE id = ?;
            """.trimMargin())
        val jogo = objeto as Jogo
        preparedStatement?.setString(1, jogo.nome)
        preparedStatement?.setString(2, jogo.empresa)
        preparedStatement?.setString(3, jogo.plataforma)
        preparedStatement?.setInt(4, jogo.data_de_publicacao)
        preparedStatement?.setInt(5,jogo.id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }

    override fun deletar(id: Int) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            DELETE FROM Jogo  
            WHERE id = ?;
            """.trimMargin())
        preparedStatement?.setInt(1,id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }
}