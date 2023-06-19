package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb

import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.IMongoDbBaseDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.FilterByUuidDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDocumentDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterUpdateDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbFindAllResponseDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbFindByUuidResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

open class MongoDbDataApiDataSource<T : IMongoDbBaseDto>(
    override val dataSource: String,
    override var database: String,
    override val collection: String,
    override val serializers: SerializersModule) : IMongoDbDataApiDataSource<T> {

    //TODO: handle Error in all request
    private val apiUrl = BuildKonfig.apiUrl
    private val apiKey = BuildKonfig.apiKey
    private enum class Resources(val value: String) {
        FIND("data/v1/action/find"),
        FIND_ONE("data/v1/action/findOne"),
        INSERT_ONE("data/v1/action/insertOne"),
        UPDATE_ONE("data/v1/action/updateOne"),
        DELETE_ONE("data/v1/action/deleteOne")
    }

    //TODO: implement DI
    private val client = HttpClient() {
        defaultRequest {
            url(apiUrl)
            contentType(ContentType.Application.Json)
            headers {
                append("api-key", apiKey)
            }
        }
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
                serializersModule = serializers
            })
        }
    }

    private fun getDefaultRequestDto(): MongoDbRequestDto {

        return MongoDbRequestDto(
            collection = collection,
            database = database,
            dataSource = dataSource
        )
    }

    override suspend fun insert(item: T) {

        val bodyRequest = MongoDbRequestDocumentDto<T>(
            default = getDefaultRequestDto(),
            document = item
        )

        val response = client.post(Resources.INSERT_ONE.value) {
            setBody(bodyRequest)
        }

        handleResponseError(response)
    }

    override suspend fun findAll(): List<T> {

        val response = client.post(Resources.FIND.value) {
            setBody(getDefaultRequestDto())
        }
        handleResponseError(response)

        if (response.status.value in 200..299) {
            val mongoDbFindAllResponseDto = response.body<MongoDbFindAllResponseDto<T>>()
             mongoDbFindAllResponseDto.documents?.let {
                 return it
             }
        }

        return arrayListOf()
    }

    override suspend fun findBy(uuid: String): T? {

        val bodyRequest = MongoDbRequestFilterDto(
            default = getDefaultRequestDto(),
            filter = FilterByUuidDto(
                uuid = uuid
            )
        )

        val response = client.post(Resources.FIND_ONE.value) {
            setBody(bodyRequest)
        }
        handleResponseError(response)

        if (response.status.value in 200..299) {
            val mongoDbFindAllResponseDto = response.body<MongoDbFindByUuidResponseDto<T>>()
            return mongoDbFindAllResponseDto.document
        }

        return null
    }

    override suspend fun update(item: T) {
        val bodyRequest = MongoDbRequestFilterUpdateDto(
            default = getDefaultRequestDto(),
            filter = FilterByUuidDto(
                uuid = item.uuid
            ),
            update = item
        )

        val response = client.post(Resources.UPDATE_ONE.value) {
            setBody(bodyRequest)
        }

        handleResponseError(response)
    }

    override suspend fun delete(uuid: String) {

        val bodyRequest = MongoDbRequestFilterDto(
            default = getDefaultRequestDto(),
            filter = FilterByUuidDto(
                uuid = uuid
            )
        )

        val response = client.post(Resources.DELETE_ONE.value) {
            setBody(bodyRequest)
        }

        handleResponseError(response)
    }

    override fun setDataBase(databaseName: String) {
       database = databaseName
    }

    private suspend fun handleResponseError(response: HttpResponse) {
        if (response.status.value !in 200..299) {
            val bodyAsText = response.bodyAsText()
            print("HTTP ERROR: ${bodyAsText}")
        }
    }
}