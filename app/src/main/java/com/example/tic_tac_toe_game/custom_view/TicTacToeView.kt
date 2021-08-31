package com.example.tic_tac_toe_game.custom_view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class TicTacToeView : View, ValueAnimator.AnimatorUpdateListener {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    override fun onAnimationUpdate(p0: ValueAnimator?) {
        TODO("Not yet implemented")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }
}