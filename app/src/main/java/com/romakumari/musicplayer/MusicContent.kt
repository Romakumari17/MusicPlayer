package com.romakumari.musicplayer

import java.util.concurrent.TimeUnit


data class MusicContent(
    var title:String="",
    var artistName:String="",
    var duration:Long=0,
    var storageLocation:String="",
    var isPlaying:Boolean=false
)
fun formatDuration(duration: Long):String{
     var minutes=TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
     var seconds=(TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS)-
             minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))
    return String.format("0%2d :0%2d")
}
