package br.maua.dao

import br.maua.models.Review

class ReviewDAO : GenericoDAO{
    override fun pegarUm(id: Int): Any {
        var review : Review? = null
        //Cria uma conexão com o banco
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Review WHERE id = ${id};")
            while(resultSet?.next()!!){
                review = Review(
                    resultSet.getInt("id"),
                    resultSet.getString("texto"),
                    resultSet.getInt("idJogo"),
                    resultSet.getInt("idUser")
                )
                println("Review encontrado: ${review}")
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }finally {
            connection?.close()
        }
        return review!!

    }

    override fun pegarTodos(): List<Any> {
        val review = mutableListOf<Review>()
        var connection : ConnectionDAO? = null
        try {
            //Cria uma conexão com o banco
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Review;")
            //Intera pelo resultado obtido
            while (resultSet?.next()!!) {
                review.add(
                    Review(
                        resultSet.getInt("id"),
                        resultSet.getString("texto"),
                        resultSet.getInt("idJogo"),
                        resultSet.getInt("idUser")
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
        return review
    }

    override fun inserirUm(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            INSERT INTO Review 
            (texto, idJogo, idUser) 
            VALUES (?,?,?);
            """.trimMargin())
        val review = objeto as Review
        preparedStatement?.setString(1,review.texto)
        preparedStatement?.setInt(2,review.idJogo)
        preparedStatement?.setInt(3,review.idUser)
        preparedStatement?.executeUpdate()
        //Banco está em auto-commit()

        connectionDAO.close()
    }

    override fun atualizar(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            UPDATE Review 
            SET texto = ?, idJogo = ?, idUser = ? 
            WHERE id = ?;
            """.trimMargin())
        val review = objeto as Review
        preparedStatement?.setString(1, review.texto)
        preparedStatement?.setInt(2, review.idJogo)
        preparedStatement?.setInt(3, review.idUser)
        preparedStatement?.setInt(4,review.id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }

    override fun deletar(id: Int) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            DELETE FROM Review  
            WHERE id = ?;
            """.trimMargin())
        preparedStatement?.setInt(1,id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }
}