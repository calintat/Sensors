package com.calintat.sensors.api

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import android.hardware.Sensor
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import com.calintat.sensors.R
import com.calintat.sensors.activities.MainActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sensorManager

enum class Item(val sensor: Int, @StringRes val unit: Int, val dimension: Int, @IdRes val id: Int, @StringRes val label: Int, val shortcutId: String, @DrawableRes val shortcutIcon: Int) {

    ACCELEROMETER(
            sensor = Sensor.TYPE_ACCELEROMETER,
            unit = R.string.metre_per_second_squared,
            dimension = 3,
            id = R.id.navigation_accelerometer,
            label = R.string.navigation_accelerometer,
            shortcutId = "shortcut_accelerometer",
            shortcutIcon = R.drawable.ic_shortcut_accelerometer
    ),

    AMBIENT_TEMPERATURE(
            sensor = Sensor.TYPE_AMBIENT_TEMPERATURE,
            unit = R.string.celsius,
            dimension = 1,
            id = R.id.navigation_ambient_temperature,
            label = R.string.navigation_ambient_temperature,
            shortcutId = "shortcut_ambient_temperature",
            shortcutIcon = R.drawable.ic_shortcut_ambient_temperature
    ),

    GRAVITY(
            sensor = Sensor.TYPE_GRAVITY,
            unit = R.string.metre_per_second_squared,
            dimension = 3,
            id = R.id.navigation_gravity,
            label = R.string.navigation_gravity,
            shortcutId = "shortcut_gravity",
            shortcutIcon = R.drawable.ic_shortcut_gravity
    ),

    GYROSCOPE(
            sensor = Sensor.TYPE_GYROSCOPE,
            unit = R.string.radian_per_second,
            dimension = 3,
            id = R.id.navigation_gyroscope,
            label = R.string.navigation_gyroscope,
            shortcutId = "shortcut_gyroscope",
            shortcutIcon = R.drawable.ic_shortcut_gyroscope
    ),

    LIGHT(
            sensor = Sensor.TYPE_LIGHT,
            unit = R.string.lux,
            dimension = 1,
            id = R.id.navigation_light,
            label = R.string.navigation_light,
            shortcutId = "shortcut_light",
            shortcutIcon = R.drawable.ic_shortcut_light
    ),

    LINEAR_ACCELERATION(
            sensor = Sensor.TYPE_LINEAR_ACCELERATION,
            unit = R.string.metre_per_second_squared,
            dimension = 3,
            id = R.id.navigation_linear_acceleration,
            label = R.string.navigation_linear_acceleration,
            shortcutId = "shortcut_linear_acceleration",
            shortcutIcon = R.drawable.ic_shortcut_linear_acceleration
    ),

    MAGNETIC_FIELD(
            sensor = Sensor.TYPE_MAGNETIC_FIELD,
            unit = R.string.microtesla,
            dimension = 3,
            id = R.id.navigation_magnetic_field,
            label = R.string.navigation_magnetic_field,
            shortcutId = "shortcut_magnetic_field",
            shortcutIcon = R.drawable.ic_shortcut_magnetic_field
    ),

    PRESSURE(
            sensor = Sensor.TYPE_PRESSURE,
            unit = R.string.millibar,
            dimension = 1,
            id = R.id.navigation_pressure,
            label = R.string.navigation_pressure,
            shortcutId = "shortcut_pressure",
            shortcutIcon = R.drawable.ic_shortcut_pressure
    ),

    PROXIMITY(
            sensor = Sensor.TYPE_PROXIMITY,
            unit = R.string.centimetre,
            dimension = 1,
            id = R.id.navigation_proximity,
            label = R.string.navigation_proximity,
            shortcutId = "shortcut_proximity",
            shortcutIcon = R.drawable.ic_shortcut_proximity
    ),

    RELATIVE_HUMIDITY(
            sensor = Sensor.TYPE_RELATIVE_HUMIDITY,
            unit = R.string.percentage,
            dimension = 1,
            id = R.id.navigation_relative_humidity,
            label = R.string.navigation_relative_humidity,
            shortcutId = "shortcut_relative_humidity",
            shortcutIcon = R.drawable.ic_shortcut_relative_humidity
    ),

    ROTATION_VECTOR(
            sensor = Sensor.TYPE_ROTATION_VECTOR,
            unit = R.string.unitless,
            dimension = 3,
            id = R.id.navigation_rotation_vector,
            label = R.string.navigation_rotation_vector,
            shortcutId = "shortcut_rotation_vector",
            shortcutIcon = R.drawable.ic_shortcut_rotation_vector
    );

    companion object Utils {

        private const val SHORTCUT_ID = "com.calintat.sensors.api.SHORTCUT_ID"

        fun isIdSafe(id: Int) = get(id) != null

        fun get(id: Int?) = values().firstOrNull { it.id == id }

        fun get(intent: Intent) = intent.getStringExtra(SHORTCUT_ID)?.let { get(it) }

        private fun get(shortcutId: String?) = values().firstOrNull { it.shortcutId == shortcutId }

        fun firstAvailableItem(ctx: Context) = values().firstOrNull { it.isAvailable(ctx) }

        fun availableItems(ctx: Context) = values().filter { it.isAvailable(ctx) }

        @TargetApi(25) fun buildShortcut(ctx: Context, shortcutId: String): ShortcutInfo? {

            return get(shortcutId)?.run {

                val intent = ctx.intentFor<MainActivity>(SHORTCUT_ID to shortcutId)

                ShortcutInfo.Builder(ctx, shortcutId)
                        .setRank(ordinal)
                        .setShortLabel(ctx.getString(label))
                        .setIntent(intent.setAction(Intent.ACTION_MAIN))
                        .setIcon(Icon.createWithResource(ctx, shortcutIcon)).build()
            }
        }
    }

    /**
     * Returns whether this item has any available sensors in a given context.
     */
    fun isAvailable(context: Context) = getDefaultSensor(context) != null

    /**
     * Returns the default sensor of this item in a given context or null if none exists.
     */
    fun getDefaultSensor(context: Context): Sensor? = context.sensorManager.getDefaultSensor(sensor)
}