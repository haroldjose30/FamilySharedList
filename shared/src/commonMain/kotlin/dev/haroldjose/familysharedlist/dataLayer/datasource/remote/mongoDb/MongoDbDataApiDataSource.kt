package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb

import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.IMongoDbBaseDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.FilterByUuidDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDocumentDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterUpdateDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

open class MongoDbDataApiDataSource<T : IMongoDbBaseDto>(
    override val dataSource: String,
    override val database: String,
    override val collection: String,
) : IMongoDbDataApiDataSource<T> {

    //TODO: handle Error in all request
    private val apiUrl = BuildKonfig.apiUrl
    private val apiKey = BuildKonfig.apiKey
    private enum class Resources(val value: String) {
        FIND("find"),
        FIND_ONE("findOne"),
        INSERT_ONE("insertOne"),
        UPDATE_ONE("updateOne"),
        DELETE_ONE("deleteOne")
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

        client.post(Resources.INSERT_ONE.value) {
            setBody(bodyRequest)
        }
    }

    override suspend fun findAll(): HttpResponse {

        val httpResponse = client.post(Resources.FIND_ONE.value) {
            setBody(getDefaultRequestDto())
        }

        //TODO: serialize here with generics or inject an serializer function to return List<T>
        //This code does not work
        if (httpResponse.status.value in 200..299) {
            //val mongoDbResponseDto = httpResponse.body<MongoDbResponseDto<T>>()
            //val test = mongoDbResponseDto.documents
        }

        return httpResponse
    }

    override suspend fun findBy(uuid: String): HttpResponse {

        val bodyRequest = MongoDbRequestFilterDto(
            default = getDefaultRequestDto(),
            filter = FilterByUuidDto(
                uuid = uuid
            )
        )

        val httpResponse = client.post(Resources.FIND.value) {
            setBody(bodyRequest)
        }

        //TODO: serialize here with generics or inject an serializer function to return List<T>
        //This code does not work
        //if (httpResponse.status.value in 200..299) {
        //    val mongoDbResponseDto = httpResponse.body<MongoDbResponseDto<T>()
        //    return mongoDbResponseDto.documents
        //}

        return httpResponse
    }

    override suspend fun update(item: T) {
        val bodyRequest = MongoDbRequestFilterUpdateDto(
            default = getDefaultRequestDto(),
            filter = FilterByUuidDto(
                uuid = item.uuid
            ),
            update = item
        )

        client.post(Resources.UPDATE_ONE.value) {
            setBody(bodyRequest)
        }
    }

    override suspend fun delete(uuid: String) {

        val bodyRequest = MongoDbRequestFilterDto(
            default = getDefaultRequestDto(),
            filter = FilterByUuidDto(
                uuid = uuid
            )
        )

        client.post(Resources.DELETE_ONE.value) {
            setBody(bodyRequest)
        }
    }
}