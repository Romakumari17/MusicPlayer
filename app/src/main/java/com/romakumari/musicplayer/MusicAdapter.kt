package com.romakumari.musicplayer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class MusicAdapter(
    var musicInterface: MusicInterface
) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    var MusicContent : ArrayList<MusicContent> = arrayListOf()

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        var musicname = view.findViewById<TextView>(R.id.musicname)
        var duration=view.findViewById<TextView>(R.id.duration)
        var imageplay=view.findViewById<ImageButton>(R.id.imageplay)
        val root=view.rootView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view= LayoutInflater.from(parent.context)
                .inflate(R.layout.playlistlayout,parent,false)
            return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return MusicContent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.musicname.setText(MusicContent[position].title)
        holder.duration.text= formatDuration(MusicContent[position].duration)
        holder.root.setOnClickListener {
            var intent=Intent(context,PlayMusic_fragment::class.java)
            ContextCompat.startActivity(context,intent,null)
        }
        holder.imageplay.setOnClickListener{
            musicInterface.onsongPlayClick(MusicContent[position])
        }

    }
    fun updatelist(musicContent: ArrayList<MusicContent>){
        this.MusicContent.clear()
        this.MusicContent.addAll(musicContent)
        notifyDataSetChanged()
    }
}




