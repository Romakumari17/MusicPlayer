package com.romakumari.musicplayer

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
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
    lateinit var runnable: Runnable
    var songs = arrayListOf<Int>()
    lateinit var binding: FragmentPlayMusicFragmentBinding
    var currentsongIndex: Int = 0
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
        binding.seekstart.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
        binding.seekend.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
        binding.Seekbar.progress = 0
        seekbarsetup()
        binding.Seekbar.max = mainActivity.mediaPlayer.duration
        if (mainActivity.mediaPlayer.isPlaying) {
            binding.music.setText(mainActivity.musicContent?.title)
            mainActivity.mediaPlayer.pause()
            binding.musiccontrol.setBackgroundResource(R.drawable.baseline_play_arrow_24)
        } else {
            mainActivity.mediaPlayer.start()
            binding.musiccontrol.setBackgroundResource(R.drawable.baseline_pause_24)

        }
        binding.musiccontrol.setOnClickListener {
            if (mainActivity.mediaPlayer.isPlaying) {
                binding.music.setText(mainActivity.musicContent?.title)
                mainActivity.mediaPlayer.pause()
                binding.musiccontrol.setBackgroundResource(R.drawable.baseline_play_arrow_24)
            } else {
                mainActivity.mediaPlayer.start()
                binding.musiccontrol.setBackgroundResource(R.drawable.baseline_pause_24)

            }
        }
        binding.fabforward.setOnClickListener {
            currentsongIndex++
            if (currentsongIndex == songs.size - 1) {
                currentsongIndex = 0
            }
            else{
                ++currentsongIndex
            }
            mainActivity.mediaPlayer.release()
            mainActivity.mediaPlayer = MediaPlayer.create(requireContext(), songs[currentsongIndex])
            mainActivity.mediaPlayer.start()
        }
        binding.Seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mainActivity.mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit


            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit


        })
    }


        fun seekbarsetup() {
            runnable = Runnable {
                binding.seekstart.text =
                    formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
                binding.Seekbar.progress = mainActivity.mediaPlayer.currentPosition
                Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
            }
            Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
        }



    override fun onDestroy() {
        super.onDestroy()
        mainActivity.mediaPlayer.release()
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
