package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.mongoDb

import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.isDebug
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

internal class FamilyListMongoDbRepository: IFamilyListRepository {

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
            filter = FilterByUUIdDto(
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
            filter = FilterByUUIdDto(
                uuid = uuid
            )
        )

        client.post(Resources.DELETE_ONE.value) {
            setBody(bodyRequest)
        }
    }
}

@Serializable
private data class FilterByUUIdDto(
    val uuid: String
)

@Serializable
private data class UpdateDto(
    val isCompleted: Boolean
)