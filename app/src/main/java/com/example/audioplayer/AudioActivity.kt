package com.example.audioplayer

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_audio.*
import android.R.attr.start
import android.os.CountDownTimer
import android.content.Intent
import android.net.Uri


class AudioActivity : AppCompatActivity() {

    lateinit var mediaplayer : MediaPlayer
    var  songlength : Int=0
    var seek : Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        val intent = intent
        val callby=intent.getStringExtra("data")
        mediaplayer = MediaPlayer.create(baseContext, R.raw.jonasbrothersucker)

        if (callby=="byraw"){
            mediaplayer = MediaPlayer.create(baseContext, R.raw.jonasbrothersucker)
            songlength =mediaplayer.duration

            seekBar.max=songlength

            val milliseconds: Long = songlength.toLong()
            val minutes = milliseconds / 1000 / 60
            val seconds = milliseconds / 1000 % 60


            end.setText(minutes.toString()+":"+seconds.toString())



            mediaplayer.start()

            val cd = object : CountDownTimer(100000000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val dur = mediaplayer.getCurrentPosition()

                    val milliseconds: Long = dur.toLong()
                    val minutes = milliseconds / 1000 / 60
                    val seconds = milliseconds / 1000 % 60


                    strt.setText(minutes.toString()+":"+seconds.toString())


                    seekBar.setProgress(dur)
                }

                override fun onFinish() {

                }
            }

            cd.start()
        }
        else if(callby=="byphone"){
            val intent_upload = Intent()
            intent_upload.type = "audio/*"
            intent_upload.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent_upload, 1)

        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seek=progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaplayer.seekTo(seek)
            }
        })

        btnplay.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {


                iv.setImageResource(R.drawable.giff)

                mediaplayer.start()
            }
        })
        btnpause.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                iv.setImageResource(R.drawable.ss)
                mediaplayer.pause()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                //the selected audio.
                val uri = data?.getData()
                mediaplayer = MediaPlayer.create(baseContext, uri)
                songlength =mediaplayer.duration
                seekBar.max=songlength

                val milliseconds: Long = songlength.toLong()
                val minutes = milliseconds / 1000 / 60
                val seconds = milliseconds / 1000 % 60


                end.setText(minutes.toString()+":"+seconds.toString())
                mediaplayer.start()
                val cd = object : CountDownTimer(100000000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val dur = mediaplayer.getCurrentPosition()

                        val milliseconds: Long = dur.toLong()
                        val minutes = milliseconds / 1000 / 60
                        val seconds = milliseconds / 1000 % 60


                        strt.setText(minutes.toString()+":"+seconds.toString())
                        seekBar.setProgress(dur)
                    }

                    override fun onFinish() {

                    }
                }

                cd.start()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onStop() {
        mediaplayer.pause()
        super.onStop()
    }

    override fun onBackPressed() {

        super.onBackPressed()
        this.finish()

    }
}
