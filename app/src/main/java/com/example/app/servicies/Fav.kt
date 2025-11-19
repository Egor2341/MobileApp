package com.example.app.servicies

import com.example.app.data.Base
import com.example.app.data.Favourite
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

object Fav {
    suspend fun favourite(user: Int?, car: Int): Int {
        if (user == null){
            return -1
        }
        val client = Base.getClient()
        val fav = client.from("favourites")
            .select(columns = Columns.list("id")) {
                filter {
                    eq("user_id", user)
                    eq("car_id", car)
                }
            }.decodeSingleOrNull<Map<String, Int>>()
        if (fav == null) {
            client.from("favourites")
                .insert(Favourite(user, car))
            return 1;
        } else {
            client.from("favourites")
                .delete {
                    filter {
                        eq("user_id", user)
                        eq("car_id", car)
                    }
                }
            return 0;
        }
    }

    suspend fun getState(user: Int?, car: Int): Int {
        if (user == null){
            return -1
        }
        val client = Base.getClient()
        val fav = client.from("favourites")
            .select(columns = Columns.list("id")) {
                filter {
                    eq("user_id", user)
                    eq("car_id", car)
                }
            }.decodeSingleOrNull<Map<String, Int>>()
        if (fav == null) {
            return 0;
        } else {
            return 1;
        }
    }

    suspend fun getIdCars(user: Int): List<Int> {
        val res = mutableListOf<Int>()
        Base.getClient().from("favourites")
            .select(columns = Columns.list("car_id")){
                filter {
                    eq("user_id", user)
                }
            }.decodeList<Map<String, Int>>().map { element ->
                res.add(element.get("car_id")?:0)
            }
        return res
    }
}