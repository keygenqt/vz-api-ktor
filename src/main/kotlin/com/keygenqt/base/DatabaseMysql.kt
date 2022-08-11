package com.keygenqt.base

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.slf4j.LoggerFactory
import javax.sql.DataSource


class DatabaseMysql(
    private val jdbc: String,
    private val config: String,
    private val migration: String,
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun connectAndMigrate(): DatabaseMysql {
        log.info("Initialising database")
        val dataSource = hikari(config)
        Database.connect(dataSource)
        runFlyway(dataSource)
        return this
    }

    private fun hikari(configPath: String): DataSource {
        val config = HikariConfig(configPath).apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = this@DatabaseMysql.jdbc
            validate()
        }
        return HikariDataSource(config)
    }

    private fun runFlyway(datasource: DataSource) {
        val flyway = Flyway.configure()
            .locations(migration)
            .dataSource(datasource)
            .load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            log.error("Exception running flyway migration", e)
            throw e
        }
        log.info("Flyway migration has finished")
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction { block() }
}