package br.maua.dao

import br.maua.models.Usuario

class UsuarioDAO : GenericoDAO{
    override fun pegarUm(id: Int): Any {
        var usuario : Usuario? = null
        //Cria uma conexão com o banco
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Usuario WHERE id = ${id};")
            while(resultSet?.next()!!){
                usuario = Usuario(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("email"),
                    resultSet.getString("senha")
                )
                println("Jogo encontrado: ${usuario}")
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }finally {
            connection?.close()
        }
        return usuario!!
    }

    override fun pegarTodos(): List<Any> {
        val usuarios = mutableListOf<Usuario>()
        var connection : ConnectionDAO? = null
        try {
            //Cria uma conexão com o banco
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Usuario;")
            //Intera pelo resultado obtido
            while (resultSet?.next()!!) {
                usuarios.add(
                    Usuario(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("senha")
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
        return usuarios
    }

    override fun inserirUm(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            INSERT INTO Usuario 
            (nome, email, senha) 
            VALUES (?,?,?);
            """.trimMargin())
        val usuario = objeto as Usuario
        preparedStatement?.setString(1,usuario.nome)
        preparedStatement?.setString(2,usuario.email)
        preparedStatement?.setString(3,usuario.senha)
        preparedStatement?.executeUpdate()
        //Banco está em auto-commit()

        connectionDAO.close()
    }

    override fun atualizar(objeto: Any) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            UPDATE Usuario 
            SET nome = ?, email = ?, senha = ? 
            WHERE id = ?;
            """.trimMargin())
        val usuario = objeto as Usuario
        preparedStatement?.setString(1, usuario.nome)
        preparedStatement?.setString(2, usuario.email)
        preparedStatement?.setString(3, usuario.senha)
        preparedStatement?.setInt(4,usuario.id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }

    override fun deletar(id: Int) {
        val connectionDAO = ConnectionDAO()
        val preparedStatement = connectionDAO.getPreparedStatement("""
            DELETE FROM Usuario  
            WHERE id = ?;
            """.trimMargin())
        preparedStatement?.setInt(1,id)
        preparedStatement?.executeUpdate()
        connectionDAO.close()
    }
}