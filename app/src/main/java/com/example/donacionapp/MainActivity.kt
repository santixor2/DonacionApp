package com.example.donacionapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.donacionapp.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            val message = if (isGranted) "permiso concedido" else "permiso rechazado"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    private val nameCanal = "test"
    private val idCanal = "id"
    private val notificationId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        screenSplash.setKeepOnScreenCondition {
            lifecycleScope.launch {
                delay(2000)
            }
            false
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment
        navHostFragment.navController.apply {
            binding.bottomNavigationView.setupWithNavController(this)
            binding.bottomNavigationView.itemIconTintList = null
            if (getPrefs()) {
                navigate(R.id.action_onboardingFragment_to_loginFragment)
            } else {
                navigate(R.id.action_onboardingFragment_self)
            }
        }
        //permissionPdf()
    }
    fun showPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    private fun getPrefs() = getPreferences(Context.MODE_PRIVATE).getBoolean("onBoardingFinished", false)
    fun showBottomNav(show: Boolean) {
        binding.bottomNavigationView.isVisible = show
    }
    @SuppressLint("MissingPermission")
    fun sendNotification(title:String, body:String, description:String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            val importCanal = NotificationManager.IMPORTANCE_HIGH
            val canal = NotificationChannel(idCanal,nameCanal,importCanal)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }

        val notification = NotificationCompat.Builder(this,idCanal).also {
            it.setContentTitle(title)
            it.setContentText(body)
            it.setStyle(NotificationCompat.BigTextStyle().bigText(description))
            it.setSmallIcon(R.drawable.notification)
            it.priority = NotificationCompat.PRIORITY_HIGH
        }.build()
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(notificationId, notification)
    }
//    private fun permissionPdf(){
//        if(checkPermission()) {
//            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show()
//        } else {
//            requestPermissions()
//        }
//    }
//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
//            200
//        )
//    }
//    private fun checkPermission(): Boolean {
//        val permission1 = ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
//        val permission2 = ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
//        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
//    }
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode == 200) {
//            if(grantResults.size > 0) {
//                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
//                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
//
//                if(writeStorage && readStorage) {
//                    Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_LONG)
//                } else {
//                    Toast.makeText(this, "Permisos rechazados", Toast.LENGTH_LONG)
//                    finish()
//                }
//            }
//        }
//    }
}