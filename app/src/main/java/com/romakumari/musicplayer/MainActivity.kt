package com.romakumari.musicplayer

import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.romakumari.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var musicViewModel: MusicViewModel
    var permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            getsongs()
        } else {
                 ActivityResultContracts.RequestPermission()

        }
    }
    var musiclist = ArrayList<MusicContent>()
    var musicContent : MusicContent? = null
    var mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        musicViewModel = ViewModelProvider(this)[MusicViewModel::class.java]

        navController = findNavController(R.id.nav_host)
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.playlistid -> {
                    navController.navigate(R.id.playlist_fragment)
                }

                R.id.playmusicid -> {
                    navController.navigate(R.id.playMusic_fragment)
                }
            }
            return@setOnItemSelectedListener true
        }
        navController.addOnDestinationChangedListener { _, destination, arguments->
            when(destination.id) {
                R.id.playlist_fragment ->
                    binding.bottomNav.menu[0].isChecked = true


                R.id.playMusic_fragment ->
                    binding.bottomNav.menu[1].isChecked = true
            }
        }


    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            getsongs()
        } else {
            permission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }


    fun getsongs() {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC
        val cursor: Cursor? = contentResolver?.query(
            uri, null,
            selection, null, null
        )
        musiclist.clear()
        if (cursor != null) {
            if (cursor?.moveToFirst() == true) {
                while (cursor?.isLast == false) {
                    System.out.println("in cursor ")

                    musiclist.add(
                        MusicContent(
                            title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                            artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            storageLocation = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                        )
                    )
                    cursor.moveToNext()
                }
                musicViewModel.PlayMusic.value = musiclist
            }
        }
    }

}
