package dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.mongoDb

import dev.haroldjose.sharedfamilylist.dataLayer.dto.FamilyListDto
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.isDebug
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
    //TODO: put this values on enviroment variable, see https://github.com/yshrsmz/BuildKonfig
    private val baseUrl = "https://data.mongodb-api.com/app/data-kcqbh/endpoint/data/v1/action/"
    private val apiKey = "bHI2NV07pNs6Xx2X8sVTvI5Fx6R6poymz2jt8Vv3eUtzFQYy0OK1FJmsFXAByKIA"
    private enum class Resources(val value: String) {
        FIND("find"),
        INSERT_ONE("insertOne"),
        UPDATE_ONE("updateOne"),
        DELETE_ONE("deleteOne")
    }

    //TODO: implement DI
    private val client = HttpClient() {
        defaultRequest {
            url(baseUrl)
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

        var collection = "shoppingList"
        var database = "sharedfamilylistDb"
        val dataSource = "Cluster0"

        if (isDebug) {
            collection = "devCollection"
            database = "devDatabase"
        }
        return MongoDbRequestDto(
            collection = collection,
            database = database,
            dataSource = dataSource
        )
    }

    override suspend fun insert(item: FamilyListDto) {

        var bodyRequest = MongoDbRequestDocumentDto<FamilyListDto>(
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

        var bodyRequest = MongoDbRequestFilterUpdateDto(
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

        var bodyRequest = MongoDbRequestFilterDto(
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