package com.inappupdate.apk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class MainActivity : AppCompatActivity() {
    private var appUpdate: AppUpdateManager? = null
    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appUpdate = AppUpdateManagerFactory.create(this)
        checkUpdate()
    }
    fun checkUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->

            if(updateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
                    && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){

                appUpdate?.startUpdateFlowForResult(updateInfo,AppUpdateType.IMMEDIATE,this,REQUEST_CODE)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }

    fun inProgressUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->

            if(updateInfo.updateAvailability()== UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdate?.startUpdateFlowForResult(updateInfo,AppUpdateType.IMMEDIATE,this,REQUEST_CODE)
            }
        }
    }
}