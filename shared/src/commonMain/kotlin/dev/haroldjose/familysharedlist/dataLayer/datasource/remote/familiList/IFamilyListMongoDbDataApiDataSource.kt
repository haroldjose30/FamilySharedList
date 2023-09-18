package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familiList

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.IMongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

interface IFamilyListMongoDbDataApiDataSource: IMongoDbDataApiDataSource<FamilyListDto>