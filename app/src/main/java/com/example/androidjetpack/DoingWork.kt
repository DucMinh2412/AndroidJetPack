package com.example.androidjetpack

import android.content.Context
import android.provider.Settings.Global.putString
import android.util.Log.e
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.androidjetpack.FirstFragment.Companion.DUC_NO_PRO
import java.text.SimpleDateFormat
import java.util.*

class DoingWork(
    context: Context,
    workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object{
        const val DUC_NO_PRO_2 = "duc_no_pro"
    }


    override fun doWork(): Result {
        return try {
            // get data
            val count = inputData.getInt(DUC_NO_PRO,0)
            e("abc", "ducNoPro $count")

            // post data
            val postData = "duc pro oo"
            val outputData = Data.Builder()
                    .putString(DUC_NO_PRO_2,postData)
                    .build()

            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}