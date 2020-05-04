package jay2468.maskmap.fragment

import jay2468.maskmap.R
import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import jay2468.maskmap.utils.PermissionUtils
import jay2468.maskmap.common.MyLocationListener
import jay2468.maskmap.common.MyMapObserver
import jay2468.maskmap.data.db.entity.MaskEntity
import jay2468.maskmap.databinding.MapsBinding
import jay2468.maskmap.utils.AnimatorUtil.revealView
import jay2468.maskmap.view.CommProgressDialog
import jay2468.maskmap.view.MarkerDialog
import jay2468.maskmap.viewModel.MapViewModel
import jay2468.maskmap.viewModel.WelcomeViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.Serializable


class MapsFragment : Fragment(), OnMapReadyCallback, KodeinAware {
    override val kodein by closestKodein()
    private val mapViewModel: MapViewModel by instance<MapViewModel>()
    private val welComViewModel: WelcomeViewModel by instance<WelcomeViewModel>()
    private val markerDialog: MarkerDialog by instance<MarkerDialog>()
    private val myLocationListener: MyLocationListener by instance<MyLocationListener>()
    private val commProgressDialog: CommProgressDialog by instance<CommProgressDialog>()

    private val PERMISSION_ACCESS_FINE_LOCATION = 1
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var currentAddress: List<Address>
    private val entityMap: MutableMap<String, MaskEntity> = mutableMapOf()
    private lateinit var nearbyPharmacyList: List<MaskEntity>
    private var marker: Marker? = null
    private lateinit var geoCoder: Geocoder
    private lateinit var binding: MapsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.maps, container, false)
        binding.also {
            it.mapViewModel = mapViewModel
            it.welcomeViewModel = welComViewModel
        }
        bindUI()
        geoCoder = Geocoder(context)
        lifecycle.addObserver(MyMapObserver(mapView, this))
        mapView.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locate()
        } else {
            val taiwan = LatLng(23.9193026, 120.6736842)

            googleMap.apply {
                moveCamera(CameraUpdateFactory.newLatLng(taiwan))
                animateCamera(CameraUpdateFactory.zoomTo(8.0f))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this@MapsFragment.googleMap = googleMap

        googleMap.apply {
            setMapStyle(MapStyleOptions(resources.getString(R.string.goole_map_json)))
        }.setOnMapClickListener {
            marKer(it.latitude, it.longitude)
        }
        if (mapViewModel.specificOne.value == null)
            locate()
        bindObserve()
    }

    private fun bindObserve() {

        myLocationListener.getLatLng.observe(viewLifecycleOwner, Observer { latLng ->
            if (mapViewModel.specificOne.value == null)
                marKer(latLng.latitude, latLng.longitude)
        })

        with(mapViewModel) {
            address.observe(viewLifecycleOwner, Observer { maskEntities ->
                if(maskEntities!=null){
                    nearbyPharmacyList = maskEntities
                    for (entity in nearbyPharmacyList) {
                        val markerOptions = MarkerOptions()
                        val marker = googleMap.addMarker(
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.black_pin)).position(
                                LatLng(entity.Latitude, entity.Longitude)))
                        marker.tag = entity.id
                        entityMap[entity.id] = entity
                        googleMap.setOnMarkerClickListener {
                            markerDialog.maskEntity = entityMap[it.tag]
                            markerDialog.show()
                            true
                        }
                    }
                }
            })

            specificOne.observe(viewLifecycleOwner, Observer { maskEntity ->
                if (maskEntity != null) {
                    with(maskEntity) {
                        val markerOptions = MarkerOptions()
                        googleMap.apply {
                            moveCamera(CameraUpdateFactory.newLatLng(LatLng(Latitude, Longitude)))
                            animateCamera(CameraUpdateFactory.zoomTo(15.0f))
                        }
                        val marker = googleMap.addMarker(
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.black_pin)).position(
                                        LatLng(Latitude, Longitude)))
                        marker.tag = id
                        entityMap[id] = this
                        googleMap.setOnMarkerClickListener {
                            markerDialog.maskEntity = entityMap[it.tag]
                            markerDialog.show()
                            true
                        }
                        mapViewModel.setSpecificOne(null)
                    }
                }
            })
        }

        with(welComViewModel) {
            loading.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading) {
                    commProgressDialog.show()
                } else {
                    commProgressDialog.dismiss()
                }
            })
        }
    }

    private fun bindUI() {
        with(binding) {
            mapView = mapview

            btnMore.setOnClickListener {
                val action =
                        MapsFragmentDirections.actionMapsFragmentToNearbyPharmacyFragment(nearbyPharmacyList as Serializable)
                findNavController().navigate(action)
            }

            btnLocate.setOnClickListener {
                locate()
            }

            btnSearchOpen.setOnClickListener {
                val metric = DisplayMetrics()
                activity!!.windowManager.defaultDisplay.getMetrics(metric)
                val width = metric.widthPixels
                revealView(searchLayout, width, 0, 0f, width.toFloat(), View.VISIBLE)
            }

            btnClose.setOnClickListener {
                val metric = DisplayMetrics()
                activity!!.windowManager.defaultDisplay.getMetrics(metric)
                val width = metric.widthPixels
                revealView(searchLayout, width, 0, width.toFloat(), 0f, View.GONE)
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ACCESS_FINE_LOCATION)
    }

    private fun locate() {
        if (PermissionUtils.hasLocationPermission(context!!)) {
            myLocationListener.getLastDeviceLocation()
        } else
            requestLocationPermission()
    }

    private fun marKer(latitude: Double, longitude: Double) {
        marker?.remove()
        val vatLng = LatLng(latitude, longitude)

        with(googleMap) {
            moveCamera(CameraUpdateFactory.newLatLng(vatLng))
            animateCamera(CameraUpdateFactory.zoomTo(15.0f))
            marker = addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).position(vatLng))
        }

        getFromLocation(latitude, longitude)
    }

    private fun getFromLocation(latitude: Double, longitude: Double) {
        currentAddress = geoCoder.getFromLocation(latitude, longitude, 1)
        if (currentAddress.isNotEmpty())
            mapViewModel.getNearByAdress(currentAddress)
    }
}