package com.example.youtubebonusapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import android.os.CountDownTimer
import android.util.Log
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

import com.example.youtubeapp.YouTubeDetails
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker


class MainActivity : AppCompatActivity() {

    //Internet Connection Layout
    lateinit var llNoInternet: LinearLayout
    lateinit var ivRefresh: ImageView

    //Main
    lateinit var llMain: LinearLayout

    //Gridlayout
    lateinit var gridView: GridView
    var videos = arrayListOf(
        YouTubeDetails("Lunch Box Cake", "YK86ZQK7aAw", "lunchcake"),
        YouTubeDetails("Chocolate Mousse Cake", "XZJEHpDnr_s", "chocolate_mousse_cake"),
        YouTubeDetails("Japanese Cheese Cake", "mRI-4boceN0", "japanese_cheesecake"),
        YouTubeDetails("Strawberry Chocolate Cake", "YlBVRmAtnJc", "strawberry_chocolate_cake"),
        YouTubeDetails("Tom and Jerry Cheese Cake ", "Xw3GSefvY2Q", "tom_and_jerry_cheese _cake"),
        YouTubeDetails("Lemon Cake", "ybbUEfrpKuc", "lemon_cake"),
        YouTubeDetails("Green Tea Cream Cake", "yVo4zGeVinA", "green_tea_cream_cake"),
        YouTubeDetails("Soft Roll Cake ", "W3k88_AA-yE", "roll_cake"),
        YouTubeDetails("Pound Cake","tyu0JYUO08U","poundcake")
    )

    //YouTube
    lateinit var ytPlayerView: YouTubePlayerView
    private lateinit var ytPlayer: YouTubePlayer
    private var currentVideo = 0
    private var timeStamp = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Internet Connection Layout
        llNoInternet = findViewById(R.id.llNoInternet)
        ivRefresh = findViewById(R.id.ivRefresh)
        ivRefresh.setOnClickListener {
            checkInternetConnection()
        }
        //Main
        llMain = findViewById(R.id.llMain)
        //YouTube
        ytPlayerView = findViewById(R.id.ytPlayerView)

        findViewById<LinearLayout>(R.id.llLogo).visibility = View.VISIBLE
        llMain.visibility = View.GONE

        object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                findViewById<LinearLayout>(R.id.llLogo).visibility = View.GONE
                llMain.visibility = View.VISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start()
        //CheckInternetConnectivity
        checkInternetConnection()


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            ytPlayerView.enterFullScreen()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            ytPlayerView.exitFullScreen()
        }
    }

    fun initializeGV() {


        ytPlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                ytPlayer = youTubePlayer
                ytPlayer.cueVideo(videos[currentVideo].ID, timeStamp)
                //GridView
                gridView = findViewById(R.id.glMain)
                gridView.adapter =
                    GridViewAdapter(videos, ytPlayer, ytPlayerView, this@MainActivity)
            }


        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentVideo", currentVideo)
        outState.putFloat("timeStamp", timeStamp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentVideo = savedInstanceState.getInt("currentVideo", 0)
        timeStamp = savedInstanceState.getFloat("timeStamp", 0f)
    }

    fun checkInternetConnection() {
        //Internet Connection Layout
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        activeNetwork?.isConnectedOrConnecting
        //Check internet connection
        if (activeNetwork?.isConnectedOrConnecting == true) {
            llNoInternet.visibility = View.GONE
            llMain.visibility = View.VISIBLE
            initializeGV()


        } else {
            llNoInternet.visibility = View.VISIBLE
            llMain.visibility = View.GONE
            println("Not Connected To The Internet")

        }
    }


}