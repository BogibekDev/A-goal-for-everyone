package com.agoal4every1.agoalforeveryone.ui.fragment

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import com.agoal4every1.agoalforeveryone.R
import com.agoal4every1.agoalforeveryone.databinding.FragmentGameBinding
import com.agoal4every1.agoalforeveryone.utils.viewBinding
import kotlin.random.Random


class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding by viewBinding { FragmentGameBinding.bind(it) }
    private lateinit var lastView: View
    private var width = 0
    private var height = 0
    private var heart = 3
    private var time = 90
    private var round = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        lastView = binding.one
        binding.tvRound.text="$round"
        setImageLayoutParams()
        setAttempts()
        setTimer()
        animOneBegin()
    }

    private fun setTimer() {

        object : CountDownTimer(90 * 1000, 1000) {

            override fun onTick(p0: Long) {
                if (time % 60 >= 10)
                    binding.tvTime.text = "${time / 60}:${time % 60}"
                else
                    binding.tvTime.text = "${time / 60}:0${time % 60}"

                time--

                if (time % 30 == 0) {
                    round++
                    binding.tvRound.setText(round)
                }

            }

            override fun onFinish() {

            }

        }.start()
    }


    private fun manageGame(value: Float, view: View) {

        if (value < -480 && value > -540 && isNext(view)) {
            heart--
            setAttempts()
        }
        if (heart == -1) {
            requireActivity().finish()
        }
    }


    private fun isNext(view: View): Boolean {
        return if (lastView != view) {
            lastView = view
            true
        } else {
            false
        }
    }

    private fun animOneBegin() {

        val llAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.one.visibility = View.VISIBLE
            manageGame(value, binding.one)
            binding.one.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 4000
        llAnimationFromX.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {


            }

            override fun onAnimationEnd(p0: Animator?) {
                animOneEnd()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

        llAnimationFromX.start()
        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animTwoBegin()
            }

        }.start()


    }

    private fun animOneEnd() {

        val llAnimationFromX = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.one.setImageResource(randomImage())

            binding.one.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 0

        llAnimationFromX.start()

    }

    private fun animTwoBegin() {

        val llAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.two.visibility = View.VISIBLE
            manageGame(value, binding.two)
            binding.two.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 4000

        llAnimationFromX.start()

        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animThreeBegin()
            }

        }.start()

        llAnimationFromX.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                animTwoEnd()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

    }

    private fun animTwoEnd() {

        val llAnimationFromX = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.two.setImageResource(randomImage())

            binding.two.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 0

        llAnimationFromX.start()


    }

    private fun animThreeBegin() {

        val llAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.three.visibility = View.VISIBLE
            manageGame(value, binding.three)
            binding.three.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 4000

        llAnimationFromX.start()

        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animFourBegin()
            }

        }.start()

        llAnimationFromX.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                animThreeEnd()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

    }

    private fun animThreeEnd() {

        val llAnimationFromX = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.three.setImageResource(randomImage())
            binding.three.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 0
        llAnimationFromX.start()

    }

    private fun animFourBegin() {

        val llAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.four.visibility = View.VISIBLE
            manageGame(value, binding.four)
            binding.four.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 4000

        llAnimationFromX.start()

        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animOneBegin()
            }

        }.start()

        llAnimationFromX.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                animFourEnd()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

    }

    private fun animFourEnd() {

        val llAnimationFromX = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        llAnimationFromX.addUpdateListener {
            val value = it.animatedValue as Float
            binding.four.setImageResource(randomImage())

            binding.four.translationX = value
        }
        llAnimationFromX.interpolator = LinearInterpolator()
        llAnimationFromX.duration = 0

        llAnimationFromX.start()

    }

    private fun setAttempts() {
        when (heart) {
            3 -> {
                binding.vFirstAttempt.setBackgroundResource(R.drawable.v_round_white)
                binding.vSecondAttempt.setBackgroundResource(R.drawable.v_round_white)
                binding.vThirdAttempt.setBackgroundResource(R.drawable.v_round_white)
            }
            2 -> {
                binding.vFirstAttempt.setBackgroundResource(R.drawable.v_round_black)
                binding.vSecondAttempt.setBackgroundResource(R.drawable.v_round_white)
                binding.vThirdAttempt.setBackgroundResource(R.drawable.v_round_white)
            }
            1 -> {
                binding.vFirstAttempt.setBackgroundResource(R.drawable.v_round_black)
                binding.vSecondAttempt.setBackgroundResource(R.drawable.v_round_black)
                binding.vThirdAttempt.setBackgroundResource(R.drawable.v_round_white)
            }
            0 -> {
                binding.vFirstAttempt.setBackgroundResource(R.drawable.v_round_black)
                binding.vSecondAttempt.setBackgroundResource(R.drawable.v_round_black)
                binding.vThirdAttempt.setBackgroundResource(R.drawable.v_round_black)
            }
        }
    }

    fun View.absX(): Int {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return location[0]
    }

    fun View.absY(): Int {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return location[1]
    }


    private fun setImageLayoutParams() {
        binding.one.layoutParams.width = width / 4
        binding.one.layoutParams.height = toDp(100)
        binding.two.layoutParams.width = width / 4
        binding.two.layoutParams.height = toDp(100)
        binding.three.layoutParams.width = width / 4
        binding.three.layoutParams.height = toDp(100)
        binding.four.layoutParams.width = width / 4
        binding.four.layoutParams.height = toDp(100)
    }

    private fun randomImage(): Int {
        return when (Random.nextInt(0, 4)) {
            0 -> R.drawable.up
            1 -> R.drawable.right
            2 -> R.drawable.down
            else -> R.drawable.left
        }
    }

    private fun toDp(size: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        size.toFloat(),
        resources.displayMetrics
    ).toInt()


}