import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandSize

@Composable
fun AppCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    type: AppCheckBoxVariant = AppCheckBoxVariant.Tertiary
) {
    val colors = when (type) {
        AppCheckBoxVariant.Primary -> customCheckboxColors(
            checkedColor = MaterialTheme.colorScheme.primary,
            checkmarkColor = MaterialTheme.colorScheme.onPrimary
        )

        AppCheckBoxVariant.Secondary -> customCheckboxColors(
            checkedColor = MaterialTheme.colorScheme.secondary,
            checkmarkColor = MaterialTheme.colorScheme.onSecondary
        )

        else -> customCheckboxColors(
            checkedColor = MaterialTheme.colorScheme.surface,
            checkmarkColor = MaterialTheme.colorScheme.onSurface
        )
    }

    Checkbox(
        colors = colors,
        checked = checked,
        onCheckedChange = { if (enabled) onCheckedChange(it) },
        modifier = modifier.padding(vertical = BrandSize.xxs) // Reduced vertical padding
    )
}

enum class AppCheckBoxVariant {
    Primary, Secondary, Tertiary
}

@Composable
fun customCheckboxColors(
    checkedColor: Color = BrandColor.White,
    uncheckedColor: Color = BrandColor.White,
    checkmarkColor: Color = BrandColor.Gray500,
) = CheckboxDefaults.colors(
    checkedColor = checkedColor,
    uncheckedColor = uncheckedColor,
    checkmarkColor = checkmarkColor,
)
