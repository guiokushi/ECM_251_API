package br.maua.dao

import br.maua.shared.SharedPaths
import java.sql.*

class ConnectionDAO {
    val connection : Connection?
    var statement : Statement?
    var resultSet : ResultSet?
    var preparedStatement : PreparedStatement?

    init {
        this.connection = DriverManager.getConnection(
            SharedPaths.STRING_DE_CONEXAO_MARIADB_JDBC, SharedPaths.USUARIO_CONEXAO,
            SharedPaths.SENHA_CONEXAO)
        this.statement = null
        this.resultSet = null
        this.preparedStatement = null
    }

    fun executeQuery(sqlString : String) : ResultSet?{
        this.statement = this.connection?.createStatement()
        this.resultSet = this.statement?.executeQuery(sqlString)
        return this.resultSet
    }

    fun close(){
        this.connection?.close()
        this.statement?.close()
        this.preparedStatement?.close()
        this.resultSet?.close()
    }

    fun getPreparedStatement(sqlString: String): PreparedStatement?{
        this.preparedStatement = this.connection?.prepareStatement(sqlString)
        return this.preparedStatement
    }

    fun commit(){
        this.connection?.commit()
    }
}