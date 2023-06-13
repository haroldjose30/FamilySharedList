package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList
/*
import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.FilterByUuidDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDocumentDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterUpdateDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbResponseDto
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


internal class FamilyListRepository_Old: IFamilyListRepository {

    //TODO: handle Error in all request
    private val apiUrl = BuildKonfig.apiUrl
    private val apiKey = BuildKonfig.apiKey
    private enum class Resources(val value: String) {
        FIND("find"),
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
        //TODO: config build config to PROD, DEV and QA
        var enviroment = "PROD"
        var collection = "shoppingList"
        var database = "FamilySharedListDB_$enviroment"
        val dataSource = "Cluster0"
        //FIXME: harold
        //if (isDebug) {
        //    collection = "devCollection"
        //    database = "devDatabase"
        //}
        return MongoDbRequestDto(
            collection = collection,
            database = database,
            dataSource = dataSource
        )
    }

    override suspend fun insert(item: FamilyListDto) {

        val bodyRequest = MongoDbRequestDocumentDto<FamilyListDto>(
            default = getDefaultRequestDto(),
            document = item
        )

        client.post(Resources.INSERT_ONE.value) {
            setBody(bodyRequest)
        }
    }

    override suspend fun findAll(): List<FamilyListDto> {

        val httpResponse = client.post(Resources.FIND.value) {
            setBody(getDefaultRequestDto())
        }

        if (httpResponse.status.value in 200..299) {
            val mongoDbResponseDto = httpResponse.body<MongoDbResponseDto<List<FamilyListDto>>>()
            return mongoDbResponseDto.documents
        }

        return arrayListOf()
    }

    override suspend fun update(item: FamilyListDto) {

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
 */