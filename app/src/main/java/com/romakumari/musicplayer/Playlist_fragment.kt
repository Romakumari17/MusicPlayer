package com.romakumari.musicplayer

import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.romakumari.musicplayer.databinding.ActivityMainBinding
import com.romakumari.musicplayer.databinding.FragmentPlaylistFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Playlist_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Playlist_fragment() : Fragment() ,  MusicInterface {
    lateinit var musicAdapter:MusicAdapter
    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentPlaylistFragmentBinding
    lateinit var musicViewModel: MusicViewModel
    private var param1: String? = null
    private var param2: String? = null

    constructor(parcel: Parcel) : this() {
        param1 = parcel.readString()
        param2 = parcel.readString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity=activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentPlaylistFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicAdapter=MusicAdapter(this)
        binding.recyclerview.layoutManager=LinearLayoutManager(mainActivity)
        binding.recyclerview.adapter=musicAdapter
        musicViewModel=ViewModelProvider(mainActivity)[MusicViewModel::class.java]
        musicViewModel.PlayMusic.observe(mainActivity){
            musicAdapter.updatelist(it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Playlist_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Playlist_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onsongPlayClick(musicContent: MusicContent) {
        if(mainActivity.mediaPlayer.isPlaying){
            mainActivity.mediaPlayer.stop()
            mainActivity.mediaPlayer.reset()
        }else{
            mainActivity.mediaPlayer.setDataSource(mainActivity, Uri.parse(musicContent.storageLocation))
            mainActivity.mediaPlayer.prepare()
            mainActivity.mediaPlayer.start()
        }
    }
}


