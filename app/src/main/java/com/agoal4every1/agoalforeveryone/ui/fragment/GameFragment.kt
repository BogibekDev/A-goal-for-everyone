package com.agoal4every1.agoalforeveryone.ui.fragment

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.agoal4every1.agoalforeveryone.R
import com.agoal4every1.agoalforeveryone.databinding.FragmentGameBinding
import com.agoal4every1.agoalforeveryone.manager.PrefManager
import com.agoal4every1.agoalforeveryone.utils.Extentions.click
import com.agoal4every1.agoalforeveryone.utils.PlaySound
import com.agoal4every1.agoalforeveryone.utils.viewBinding
import kotlin.random.Random


class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding by viewBinding { FragmentGameBinding.bind(it) }
    private lateinit var lastView: View
    private lateinit var clickedLastView: View
    private var timeObject: CountDownTimer? = null
    private var firstLlAnimationFromX: ValueAnimator? = null
    private var firstLlAnimationFromXEnd: ValueAnimator? = null
    private var secondLlAnimationFromX: ValueAnimator? = null
    private var secondLlAnimationFromXEnd: ValueAnimator? = null
    private var thirdLlAnimationFromX: ValueAnimator? = null
    private var thirdLlAnimationFromXEnd: ValueAnimator? = null
    private var fourthLlAnimationFromX: ValueAnimator? = null
    private var fourthLlAnimationFromXEnd: ValueAnimator? = null
    private var width = 0
    private var height = 0
    private var heart = 9
    private var time = 90
    private var round = 1
    private var isDestroyed = false
    private var isWin: Boolean = false
    private var isClicked: Boolean = false
    private var isChanged: Boolean = false
    private var list = arrayOf(randomImage(), randomImage(), randomImage(), randomImage())
    private var ids = arrayOf(0, 1, 2, 3)

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
        round = PrefManager(requireContext()).getInt("round")
        lastView = binding.four
        clickedLastView = binding.one
        isWin = false
        heart = 9
        time = 90
        binding.tvRound.text = "$round"
        binding.ivMainPhoto.setImageResource(randomMainImage())
        setIds()
        setImageLayoutParams()
        setAttempts()
        setTimer()
        animOneBegin()
        binding.apply {
            ivUpBlack.click {
                checkImage(R.drawable.up)
            }
            ivDownBlack.click {
                checkImage(R.drawable.down)
            }
            ivLeftBlack.click {
                checkImage(R.drawable.left)
            }
            ivRightBlack.click {
                checkImage(R.drawable.right)
            }
        }
    }

    private fun setIds() {
        ids[0] = binding.one.id
        ids[1] = binding.two.id
        ids[2] = binding.three.id
        ids[3] = binding.four.id
    }

    private fun checkImage(image: Int) {
        isClicked = true
        if (list[0] == image) {
            //viewni invisible qilish kerak
            Log.d("@@@", "checkImage: true")
            PlaySound.playTrueSound(requireContext())
        } else {
            heart--
            setAttempts()
            Log.d("@@@", "checkImage: false")
            PlaySound.playWrongSound(requireContext())
        }
    }

    private fun setTimer() {

        timeObject = object : CountDownTimer(90 * 1000, 1000) {

            override fun onTick(p0: Long) {
                time--

                if (time % 60 >= 10)
                    binding.tvTime.text = "${time / 60}:${time % 60}"
                else
                    binding.tvTime.text = "${time / 60}:0${time % 60}"

            }

            override fun onFinish() {
                round++
                PrefManager(requireContext()).saveInt("round", round)
                showResultDialog(time, round, getScore(), isWin)
            }

        }
        timeObject?.start()
    }

    private fun manageGame(value: Float, view: View) {


//        when (view.id) {
//            ids[0] -> Log.d("@@@", "image one -> value= $value")
//            ids[1] -> Log.d("@@@", "image two -> value= $value")
//            ids[2] -> Log.d("@@@", "image three -> value= $value")
//            ids[3] -> Log.d("@@@", "image four -> value= $value")
//        }

        if (value <= -(0.35 * width).toFloat() && value >= -(0.37 * width).toFloat()) {
            isClicked = false
        }

        if (value < -(0.83 * width).toFloat() && value > -(0.85 * width).toFloat()) {
            isChanged = false
        }

        if (value < -(0.80 * width).toFloat() && value > -(0.82 * width).toFloat() && !isClicked) {
            if (isNext(view)) {
                heart--
                setAttempts()
            }
        }

        if (value < -(0.82 * width).toFloat() && value > -(0.84 * width).toFloat() && !isChanged) {
            changePosition()
            isChanged = true
        }


        if (heart == -1) {
            showResultDialog(time, round, getScore(), isWin)
        }
    }

    private fun isNext(view: View): Boolean {
        return if (lastView != view) {
            lastView = view
            true
        } else false
    }

    private fun animOneBegin() {

        firstLlAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())
        firstLlAnimationFromX?.addUpdateListener {
            val value = it.animatedValue as Float

            if (!isDestroyed) {
                binding.one.visibility = View.VISIBLE

                manageGame(value, binding.one)
                binding.one.translationX = value
            }
        }
        firstLlAnimationFromX?.interpolator = LinearInterpolator()
        firstLlAnimationFromX?.duration = 8000
        firstLlAnimationFromX?.addListener(object : Animator.AnimatorListener {
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

        firstLlAnimationFromX?.start()
        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animTwoBegin()
            }

        }.start()


    }

    private fun animOneEnd() {
        firstLlAnimationFromXEnd = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        firstLlAnimationFromXEnd?.addUpdateListener {
            val value = it.animatedValue as Float
            if (!isDestroyed) {
                list[0] = randomImage()
                binding.one.setImageResource(list[0])
                binding.one.translationX = value
            }
        }
        firstLlAnimationFromXEnd?.interpolator = LinearInterpolator()
        firstLlAnimationFromXEnd?.duration = 0

        firstLlAnimationFromXEnd?.start()

    }

    private fun animTwoBegin() {

        secondLlAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())

        secondLlAnimationFromX?.addUpdateListener {
            val value = it.animatedValue as Float
            if (!isDestroyed) {
                binding.two.visibility = View.VISIBLE
                manageGame(value, binding.two)
                binding.two.translationX = value
            }
        }
        secondLlAnimationFromX?.interpolator = LinearInterpolator()
        secondLlAnimationFromX?.duration = 8000

        secondLlAnimationFromX?.start()

        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animThreeBegin()
            }

        }.start()

        secondLlAnimationFromX?.addListener(object : Animator.AnimatorListener {
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

        secondLlAnimationFromXEnd = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        secondLlAnimationFromXEnd?.addUpdateListener {
            val value = it.animatedValue as Float
            if (!isDestroyed) {
                list[1] = randomImage()
                binding.two.setImageResource(list[1])
                binding.two.translationX = value
            }
        }
        secondLlAnimationFromXEnd?.interpolator = LinearInterpolator()
        secondLlAnimationFromXEnd?.duration = 0

        secondLlAnimationFromXEnd?.start()


    }

    private fun animThreeBegin() {

        thirdLlAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())

        thirdLlAnimationFromX?.addUpdateListener {
            val value = it.animatedValue as Float
            if (!isDestroyed) {
                binding.three.visibility = View.VISIBLE
                manageGame(value, binding.three)
                binding.three.translationX = value
            }
        }
        thirdLlAnimationFromX?.interpolator = LinearInterpolator()
        thirdLlAnimationFromX?.duration = 8000

        thirdLlAnimationFromX?.start()

        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animFourBegin()
            }

        }.start()

        thirdLlAnimationFromX?.addListener(object : Animator.AnimatorListener {
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

        thirdLlAnimationFromXEnd = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        thirdLlAnimationFromXEnd?.addUpdateListener {
            val value = it.animatedValue as Float
            if (!isDestroyed) {
                list[2] = randomImage()
                binding.three.setImageResource(list[2])
                binding.three.translationX = value
            }
        }
        thirdLlAnimationFromXEnd?.interpolator = LinearInterpolator()
        thirdLlAnimationFromXEnd?.duration = 0
        thirdLlAnimationFromXEnd?.start()

    }

    private fun animFourBegin() {

        fourthLlAnimationFromX = ValueAnimator.ofFloat(0f, -width.toFloat())

        fourthLlAnimationFromX?.addUpdateListener {
            val value = it.animatedValue as Float
            if (!isDestroyed) {
                binding.four.visibility = View.VISIBLE
                manageGame(value, binding.four)
                binding.four.translationX = value
            }
        }
        fourthLlAnimationFromX?.interpolator = LinearInterpolator()
        fourthLlAnimationFromX?.duration = 8000

        fourthLlAnimationFromX?.start()

        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                animOneBegin()
            }

        }.start()

        fourthLlAnimationFromX?.addListener(object : Animator.AnimatorListener {
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

        fourthLlAnimationFromXEnd = ValueAnimator.ofFloat(-width.toFloat(), 0f)

        fourthLlAnimationFromXEnd?.addUpdateListener {
            val value = it.animatedValue as Float
            if (!isDestroyed) {
                list[3] = randomImage()
                binding.four.setImageResource(list[3])
                binding.four.translationX = value
            }
        }
        fourthLlAnimationFromXEnd?.interpolator = LinearInterpolator()
        fourthLlAnimationFromXEnd?.duration = 0

        fourthLlAnimationFromXEnd?.start()

    }

    private fun setAttempts() {
        when (heart) {
            9 -> {
                binding.vFirstAttempt.setBackgroundResource(R.drawable.v_round_white)
                binding.vSecondAttempt.setBackgroundResource(R.drawable.v_round_white)
                binding.vThirdAttempt.setBackgroundResource(R.drawable.v_round_white)
            }
            6 -> {
                binding.vFirstAttempt.setBackgroundResource(R.drawable.v_round_black)
                binding.vSecondAttempt.setBackgroundResource(R.drawable.v_round_white)
                binding.vThirdAttempt.setBackgroundResource(R.drawable.v_round_white)
            }
            3 -> {
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

    private fun setImageLayoutParams() {
        binding.one.layoutParams.width = width / 4
        binding.one.layoutParams.height = toDp(100)
        binding.two.layoutParams.width = width / 4
        binding.two.layoutParams.height = toDp(100)
        binding.three.layoutParams.width = width / 4
        binding.three.layoutParams.height = toDp(100)
        binding.four.layoutParams.width = width / 4
        binding.four.layoutParams.height = toDp(100)

        binding.apply {
            one.setImageResource(list[0])
            two.setImageResource(list[1])
            three.setImageResource(list[2])
            four.setImageResource(list[3])
        }
    }

    private fun randomImage(): Int {
        return when (Random.nextInt(0, 4)) {
            0 -> R.drawable.up
            1 -> R.drawable.right
            2 -> R.drawable.down
            else -> R.drawable.left
        }
    }


    private fun randomMainImage(): Int {
        return when (Random.nextInt(0, 4)) {
            0 -> R.drawable.img1
            1 -> R.drawable.img2
            2 -> R.drawable.img3
            else -> R.drawable.img4
        }
    }

    private fun changePosition() {
        val temp = list[0]
        list[0] = list[1]
        list[1] = list[2]
        list[2] = list[3]
        list[3] = temp
    }

    private fun toDp(size: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        size.toFloat(),
        resources.displayMetrics
    ).toInt()

    override fun onDestroyView() {
        isDestroyed = true
        super.onDestroyView()
        cancelAllUi()

    }

    private fun cancelAllUi() {
        timeObject?.cancel()
        cancelAnim()

    }

    private fun cancelAnim() {
        fourthLlAnimationFromXEnd?.cancel()
        fourthLlAnimationFromX?.cancel()
        thirdLlAnimationFromXEnd?.cancel()
        thirdLlAnimationFromX?.cancel()
        secondLlAnimationFromXEnd?.cancel()
        secondLlAnimationFromX?.cancel()
        firstLlAnimationFromXEnd?.cancel()
        firstLlAnimationFromX?.cancel()

    }

    private fun getScore(): String {
        return when (heart) {
            -1 -> {
                isWin = false
                "0/3"
            }
            3 -> {
                isWin = false
                "1/2"
            }
            6 -> {
                isWin = true
                "2/1"
            }
            9 -> {
                isWin = true
                "3/0"
            }
            else -> {
                isWin = false
                "0/3"
            }
        }

    }

    private fun showResultDialog(time: Int, round: Int, score: String, gameResult: Boolean) {
        onDestroyView()
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_main)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            (width * 0.85).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)


        dialog.findViewById<TextView>(R.id.tvResult).text = if (gameResult) {
            resources.getString(R.string.you_win)
        } else {
            resources.getString(R.string.you_lose)
        }
        if (time % 60 >= 10)
            dialog.findViewById<TextView>(R.id.tvTime).text = "${time / 60}:${time % 60}"
        else
            dialog.findViewById<TextView>(R.id.tvTime).text = "${time / 60}:0${time % 60}"

        dialog.findViewById<TextView>(R.id.tvRound).text = round.toString()
        dialog.findViewById<TextView>(R.id.tvScore).text = score

        dialog.findViewById<TextView>(R.id.btnReplay).click {
            dialog.dismiss()
            findNavController().navigate(
                R.id.gameFragment, arguments,
                NavOptions.Builder()
                    .setPopUpTo(R.id.gameFragment, true)
                    .build()
            )
        }
        dialog.findViewById<TextView>(R.id.btnMenu).click {
            dialog.dismiss()
            findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
        }

        dialog.show()
    }


    fun View.absX(): Int {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return location[0]
    }
}