package com.mmfsin.streetparking.data.bbdd.daos

import androidx.room.Dao

@Dao
interface SpotsDAO {
//
//    @Insert(onConflict = REPLACE)
//    fun insertProduct(productDTO: ProductDTO)
//
//    @Query("SELECT * FROM table_products")
//    fun getAllProducts(): Flow<List<ProductDTO>>
//
//    @Query("SELECT * FROM table_products WHERE haveIt = :haveIt")
//    fun getProductsByHaveIt(haveIt: Boolean): Flow<List<ProductDTO>>
//
//    @Query("SELECT * FROM table_products WHERE favorite = :favorite")
//    fun getFavoriteProducts(favorite: Boolean): Flow<List<ProductDTO>>
//
//    @Query("SELECT * FROM table_products WHERE id = :id LIMIT 1")
//    fun getProductById(id: String): ProductDTO?
//
//    @Query(
//        """
//        UPDATE table_products
//        SET
//        name = :name,
//        info= :info,
//        whereToFind = :whereTo,
//        haveIt = :haveIt,
//        favorite = :favorite
//        WHERE id = :id
//        """
//    )
//    suspend fun updateProduct(
//        id: String,
//        name: String,
//        info: String?,
//        whereTo: String?,
//        haveIt: Boolean,
//        favorite: Boolean
//    )
//
//    @Query(
//        """
//        UPDATE table_products
//        SET
//        haveIt = :haveIt
//        WHERE id = :id
//        """
//    )
//    suspend fun updateHaveIt(
//        id: String,
//        haveIt: Boolean
//    )
//
//    @Query(
//        """
//        UPDATE table_products
//        SET
//        favorite = :isFavorite
//        WHERE id = :id
//        """
//    )
//    suspend fun updateFavorite(
//        id: String,
//        isFavorite: Boolean
//    )
//
//    @Query(
//        """
//        UPDATE table_products
//        SET
//        categoryId = :categoryId
//        WHERE id = :id
//        """
//    )
//    suspend fun updateCategory(
//        id: String,
//        categoryId: Int
//    )
//
//    @Query("DELETE FROM table_products WHERE id = :id")
//    suspend fun deleteProductById(id: String)
}