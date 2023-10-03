package com.example.donacionapp.feature_donors.data.network

import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.util.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class DonorService {
    suspend fun getDonor(): ResultState<List<Donors>> {
        val listService = mutableListOf<Donors>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("donantes").orderBy("created_at", Query.Direction.DESCENDING).get().await()
        for (service in querySnapshot.documents) {
            val item = service.toObject(Donors::class.java)
            item?.image = service["image"].toString()
            item?.name = service["name"].toString()
            item?.city = service["city"].toString()
            item?.blood = service["blood"].toString()
            item?.hospital = service["hospital"].toString()
            if (item != null) {
                listService.add(item)
            }
        }
        return ResultState.Success(listService)
    }
}