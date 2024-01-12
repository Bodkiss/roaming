package com.knowroaming.esim.app.presentation.theme

import androidx.compose.foundation.shape.GenericShape

object BrandShape {

    val TriangleClip = GenericShape { size, _ ->
        val edges = size.width * 0.3f
        val middle = size.height * 0.5f

        moveTo(edges, 0f)

        lineTo(size.width, 0f)

        lineTo(size.width, size.height)

        lineTo(edges, size.height)

        // Create a smooth curve from the bottom left to the middle left
        quadraticBezierTo(
            0f,
            middle * 1.15f, // Control point below the midpoint for a smooth concave curve
            0f,
            middle // Midpoint on the left side
        )

        // Create a smooth curve from the middle left to the top left
        quadraticBezierTo(
            0f,
            middle * 0.85f, // Control point above the midpoint for a smooth concave curve
            edges,
            0f // Ending point back to the start
        )

        close()
    }
}