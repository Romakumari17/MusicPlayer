package com.romakumari.musicplayer

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.romakumari.musicplayer.databinding.FragmentPlayMusicFragmentBinding
import com.romakumari.musicplayer.databinding.PlaylistlayoutBinding
import java.net.URI

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayMusic_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayMusic_fragment : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var musicContent: MusicContent
    var mediaPlayer = MediaPlayer()
    lateinit var navController: NavController
    var songs = arrayListOf<Int>()
    lateinit var binding: FragmentPlayMusicFragmentBinding
    var currentsongIndex: Int = 0
    var  musicargs=arguments
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
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
        binding = FragmentPlayMusicFragmentBinding.inflate(layoutInflater)
        return binding.root
      // binding.music.text = musicContent.title


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  mediaPlayer = MediaPlayer.create(requireContext(), R.id.music)
        binding.musiccontrol.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                binding.music.setText(mainActivity.musicContent?.title)
                mediaPlayer.pause()
                binding.musiccontrol.setBackgroundResource(R.drawable.baseline_pause_24)
            } else {
                mediaPlayer.start()
                binding.musiccontrol.setBackgroundResource(R.drawable.baseline_play_arrow_24)

            }
        }
        binding.fabforward.setOnClickListener {
            currentsongIndex++
            if (currentsongIndex >= songs.size - 1) {
                currentsongIndex = 0
            }
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(requireContext(), songs[currentsongIndex])
            mediaPlayer.start()
        }
       

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


    companion object {

        

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayMusic_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayMusic_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }


}
