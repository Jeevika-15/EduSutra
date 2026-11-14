
package com.edusutra.app.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChipRow(
    options: List<String>,
    selected: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleSelect: Boolean = false
) {
    val chipColor = Color(0xFF52669A)
    val unselectedBorder = Color(0xFFE3E6EC)

    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            val isSelected = option in selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isSelected) chipColor else Color.White,
                        RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) chipColor else unselectedBorder,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable {
                        if (singleSelect) {
                            onSelect(option)
                        } else {
                            if (isSelected) {
                                onSelect(option)
                            } else {
                                onSelect(option)
                            }
                        }
                    }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = option,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) Color.White else Color(0xFF3A3E4D)
                )
            }
        }
    }
}
