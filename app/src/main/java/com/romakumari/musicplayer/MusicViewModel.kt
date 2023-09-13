package com.romakumari.musicplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.ArrayList

class MusicViewModel:ViewModel() {
    var PlayMusic:MutableLiveData<ArrayList<MusicContent>> = MutableLiveData(arrayListOf(MusicContent()))
}