package com.example.trell.ui.main.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.trell.App
import com.example.trell.R
import com.example.trell.ui.main.model.VideoResults
import com.example.trell.ui.main.viewmodel.MainActivityViewModel
import com.example.trell.utils.StartSnapHelper
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainActivityViewModel.IBookMarkVideo {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: ContentListAdapter
    lateinit var view: View
    lateinit var toolbar: Toolbar

    private val REQUEST_PERMISSIONS = 100

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App(this).component.inject(this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        view = findViewById(R.id.view)

        recyclerView = findViewById(R.id.search_recycler_view)

        val startSnapHelper: SnapHelper = StartSnapHelper()
        startSnapHelper.attachToRecyclerView(recyclerView)

        checkPermission()
    }

    private fun checkPermission() {
        /*RUN TIME PERMISSIONS*/
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(this, "Please provide permission to play videos", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            setAdapter(viewModel.getVideoFiles())
        }
    }

    private fun setAdapter(response: VideoResults) {
        recyclerViewAdapter = ContentListAdapter(response.videoFiles, this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> onBackPressed()

        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                var i = 0
                while (i < grantResults.size) {
                    if (grantResults.size > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        setAdapter(viewModel.getVideoFiles())
                    } else {
                        Toast.makeText(
                            this,
                            "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    i++
                }
            }
        }
    }

    override fun onBookMarkClicked(title: String, filePath: String) {
        viewModel.bookMarkVideo(title, filePath)
    }

    override fun onBookMarkRemoved(filePath: String) {
        viewModel.removeBookMark(filePath)
    }
}