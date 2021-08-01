package com.example.gpsmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val REQUEST_ACCESS_FINE_LOCATION = 1000 // 위치 정보를 주기적으로 얻는데 필요한 객체들을 선언

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProvicerClient : FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback : MyLocationCallBack

    private val polylineOptions = PolylineOptions().width(5f).color(Color.RED)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) // 화면 꺼지지 않게 하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager // SupportMapFragment를 가져와서 지도가 준비되면 알림을 받습니다.
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationInit() // 위에 선언한 변수들을 초기화
    }

    private fun locationInit(){
        fusedLocationProvicerClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest() // 위치 정보 요청에 대한 세부 정보 설정
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 업데이트 인터벌
        // 위치 정보가 없을 때는 업데이트 안함
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
    }

    override fun onResume(){
        super.onResume()
        // 권한 요청
        permissionCheck(cancel = { // 윛치 정보가 필요한 이유 다이얼로그 표시
            showPermissionInfoDialog()
        }, ok = { // 현재 위치를 주기적으로 요청
            addLocationListener()
        })
    }

    private fun permissionCheck(cancel : () -> Unit, ok : () -> Unit){
        // 위치 권환이 있는지 검사
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){ // 권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){ // 이전에 권한을 한 번 거부한 적이 있는 경우
                cancel()
            }else{ // 권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            }
        }else{ // 권한이 수락 되었을 경우
            ok()
        }
    }

    private fun showPermissionInfoDialog(){
        alert("현재 위치 정보를 얻기 위해서는 위치 권한이 필요합니다.", "권한이 필요한 이유"){
            yesButton{
                ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton{

            }
        }.show()
    }


    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProvicerClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_ACCESS_FINE_LOCATION -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    addLocationListener() // 권한 허용 됨
                }else {// 권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    override fun onPause(){
        super.onPause()
        remoteLocationListener() // 주기적인 위치 정보 갱신 요청 삭제
    }

    private fun remoteLocationListener(){
        fusedLocationProvicerClient.removeLocationUpdates(locationCallback)
    }

    inner class MyLocationCallBack : LocationCallback(){
        override fun onLocationResult(locationResult : LocationResult?) { // requestLocation에 전달되는 인자 중 LocationCallBack을 구현한 내부 클래스는 LocationResult객체를 반환하고 Locatino 객체를 얻음
            super.onLocationResult(locationResult)
            val location = locationResult?.lastLocation
            location?.run{ // GPS 설정이 꺼져있거나 정보를 얻을 수 없을 때, Location객체가 null일 수 있음 // 해당 위도와 경도 위치로 카메라를 이동함
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity", "위도 : $latitude , 경도 : $longitude")

                polylineOptions.add(latLng)
                mMap.addPolyline(polylineOptions)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0) // 호주 시드니
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}