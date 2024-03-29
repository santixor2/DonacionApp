package com.example.donacionapp.feature_pdfconvert

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.donacionapp.R
import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.feature_request.RequestAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class PdfConvert {

    private fun createBitmapFromView(context: Context, view: View, donors: List<Donors>, adapterRe : RequestAdapter, activity: Activity): Bitmap{
        val appbar = view.findViewById<AppBarLayout>(R.id.appBarLayout)
        val image = view.findViewById<ImageView>(R.id.imageViewPdf2)
        val text = view.findViewById<TextView>(R.id.textView10)
        image.isVisible = false
        text.isVisible = false
        appbar.isVisible = false
        val recycler = view.findViewById<RecyclerView>(R.id.rvRequest)
        recycler.adapter = adapterRe
        return createBitmap(context, view, activity)

    }
    private fun createBitmap(
        context: Context,
        view: View,
        activity: Activity,
    ): Bitmap {
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display?.getRealMetrics(displayMetrics)
            displayMetrics.densityDpi
        } else {
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
            )
        )
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return Bitmap.createScaledBitmap(bitmap, 816, 1254, true)
    }
    private fun convertBitmapToPdf(bitmap: Bitmap, context: Context) {
        val user = FirebaseAuth.getInstance().currentUser
        val randomId: String = UUID.randomUUID().toString()
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0F, 0F, null)
        pdfDocument.finishPage(page)
        val filePath = File(context.getExternalFilesDir(null), "${user?.email}-${randomId}.pdf")
        pdfDocument.writeTo(FileOutputStream(filePath))
//        val file = File(Environment.getExternalStorageDirectory(),"archivoTest24hrs.pdf")
//        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
        renderPdf(context, filePath)
    }
    fun createPdf(context: Context, donors : List<Donors>, activity: Activity){
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_request,null)

        val adapter = RequestAdapter(donors)
        val bitmap = createBitmapFromView(context, view, donors, adapter, activity)
        convertBitmapToPdf(bitmap, activity)

    }
    private fun renderPdf(context: Context, filePath: File) {
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            filePath
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, "application/pdf")

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.d("pdf","error")
        }
    }
}