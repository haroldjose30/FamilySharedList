package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.mongoDb

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

interface IMongoDbRequestDto {
    val collection: String
    val database: String
    val dataSource: String
}

@Serializable
data class MongoDbRequestDto(
    override val collection: String,
    override val database: String,
    override val dataSource: String,
): IMongoDbRequestDto

@Serializable
data class MongoDbRequestDocumentDto<Document>(
    override val collection: String,
    override val database: String,
    override val dataSource: String,
    val document: Document
    ): IMongoDbRequestDto {

        constructor(
            default: MongoDbRequestDto,
            document: Document
        ) : this(
            collection = default.collection,
            database = default.database,
            dataSource = default.dataSource,
            document = document
        )
}

@Serializable
data class MongoDbRequestFilterDto<T>(
    override val collection: String,
    override val database: String,
    override val dataSource: String,
    val filter: T
): IMongoDbRequestDto {

    constructor(
        default: MongoDbRequestDto,
        filter: T
    ) : this(
        collection = default.collection,
        database = default.database,
        dataSource = default.dataSource,
        filter = filter,
    )
}


@Serializable
data class MongoDbRequestFilterUpdateDto<Filter,Update>(
    override val collection: String,
    override val database: String,
    override val dataSource: String,
    val filter: Filter,
    val update: Update
): IMongoDbRequestDto {



    constructor(
        default: MongoDbRequestDto,
        filter: Filter,
        update: Update
    ) : this(
        collection = default.collection,
        database = default.database,
        dataSource = default.dataSource,
        filter = filter,
        update = update
    )
}

/*
@Serializable
data class MongoDbRequestFilterUpdateSetDto<Filter,Update>(
    override val collection: String,
    override val database: String,
    override val dataSource: String,
    val filter: Filter,
    val update: MongoDbUpdateDto<Update>
): IMongoDbRequestDto {


    @Serializable
    data class MongoDbUpdateDto<Update>(
        @JsonNames("\$set")
        val set: Update
    )

    constructor(
        default: MongoDbRequestDto,
        filter: Filter,
        set: Update
    ) : this(
        collection = default.collection,
        database = default.database,
        dataSource = default.dataSource,
        filter = filter,
        update = MongoDbUpdateDto<Update>(set = set)
    )
}
*/

