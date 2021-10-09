package com.example.youtubebonusapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import android.widget.ImageView
import android.widget.TextView
import com.example.youtubeapp.YouTubeDetails
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class GridViewAdapter(val videos: ArrayList<YouTubeDetails>,
                      val youTubePlayer: YouTubePlayer,
                      val ytPlayerView: YouTubePlayerView,
                      val context: Context
) : BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var ivIcon: ImageView
    private lateinit var tvTitle: TextView

    override fun getCount(): Int {
        return videos.size
    }
    override fun getItem(position: Int): Any? {
        return videos[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        val title = videos[position].title
        val ID =  videos[position].ID
        val videoIcone= videos[position].Icon

        if (layoutInflater==null)
        {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.item_row, null)
        }

        if (convertView != null) {
            ivIcon = convertView.findViewById(R.id.ivIcon)
            tvTitle=convertView.findViewById(R.id.tvTitle)
            tvTitle.text = title
            var Icon = 0
            when {
                videoIcone == "lunchcake" -> Icon = R.drawable.lunchcake
                videoIcone == "chocolate_mousse_cake" -> Icon = R.drawable.chocolate_mousse_cake
                videoIcone == "japanese_cheesecake" -> Icon = R.drawable.japanese_cheesecake
                videoIcone == "strawberry_chocolate_cake" -> Icon =
                    R.drawable.strawberry_chocolate_cake
                videoIcone == "tom_and_jerry_cheese _cake" -> Icon =
                    R.drawable.tom_and_jerry_cheese_cake
                videoIcone == "lemon_cake" -> Icon = R.drawable.lemon_cake
                videoIcone == "green_tea_cream_cake" -> Icon = R.drawable.green_tea_cream_cake
                videoIcone == "roll_cake" -> Icon = R.drawable.roll_cake
            }

            ivIcon.setImageResource(Icon)

            tvTitle.setOnClickListener{
                ytPlayerView.visibility = View.VISIBLE
                youTubePlayer.cueVideo(ID, 0f)
            }
        }
        return convertView
    }
}