package com.example.quickbitelocal.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuickBiteLogo(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    showText: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            val primaryColor = MaterialTheme.colorScheme.primary
            val secondaryColor = MaterialTheme.colorScheme.secondary
            
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = size.toPx() * 0.1f
                
                // Draw a stylized 'Q' that looks like a plate
                drawCircle(
                    color = primaryColor,
                    radius = size.toPx() * 0.4f,
                    style = Stroke(width = strokeWidth)
                )
                
                // Draw a bite mark or a steam line
                drawArc(
                    color = Color.White,
                    startAngle = -45f,
                    sweepAngle = 90f,
                    useCenter = true,
                    topLeft = Offset(size.toPx() * 0.1f, size.toPx() * 0.1f),
                    size = Size(size.toPx() * 0.8f, size.toPx() * 0.8f)
                )
                
                // Draw a fork/spoon handle as the tail of 'Q'
                drawLine(
                    color = primaryColor,
                    start = Offset(size.toPx() * 0.7f, size.toPx() * 0.7f),
                    end = Offset(size.toPx() * 0.95f, size.toPx() * 0.95f),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        }
        
        if (showText) {
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "QuickBite",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1).sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "Local",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier.offset(y = (-4).dp)
                )
            }
        }
    }
}
