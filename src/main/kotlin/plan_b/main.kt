@file:Suppress("ClassName", "SpellCheckingInspection", "PrivatePropertyName")

package plan_b

import vjson.JSON
import vjson.deserializer.rule.*
import vjson.util.ObjectBuilder


// runtime
internal annotation class NeoJson

object NullableIntRule : BuiltInNullableRule<Int>(IntRule, { null })

object NullableLongRule : BuiltInNullableRule<Long>(LongRule, { null })

object NullableBooleanRule : BuiltInNullableRule<Boolean>(BoolRule, { null })

object NullableDoubleRule : BuiltInNullableRule<Double>(DoubleRule, { null })

internal val String.Companion.rule
    get() = StringRule

internal val String.Companion.nullableRule
    get() = NullableStringRule

internal val Int.Companion.rule
    get() = IntRule
internal val Int.Companion.nullableRule
    get() = NullableIntRule

internal val Long.Companion.rule
    get() = LongRule

internal val Long.Companion.nullableRule
    get() = NullableLongRule

internal val Boolean.Companion.rule
    get() = BoolRule

internal val Boolean.Companion.nullableRule
    get() = NullableBooleanRule

internal val Float.Companion.rule
    get() = DoubleRule

internal val Float.Companion.nullableRule
    get() = NullableDoubleRule

internal val Double.Companion.rule
    get() = DoubleRule

internal val Double.Companion.nullableRule
    get() = NullableDoubleRule

// ------------------------------------------------------------------------------------------------------------------


// by user
@NeoJson
internal data class Track(
    val id: Long,
    val tag: Int?,
    val score: Float?,
    val name: String,
    val label: Label,
) {

    // code gen
    class NEOJSON_TRACK {
        var meta = Track(0, null, null, "", Label.NEOJSON_LABEL().meta)
    }

    // code gen
    companion object {
        val NEOJSON_RULE = ObjectRule(::NEOJSON_TRACK) {
            put("id", Long.rule) {
                meta = meta.copy(id = it)
            }
            put("tag", Int.nullableRule) {
                meta = meta.copy(tag = it)
            }
            put("score", Float.nullableRule) {
                meta = meta.copy(score = it?.toFloat())
            }
            put("name", String.rule) {
                meta = meta.copy(name = it)
            }
            put("label", Label.NEOJSON_RULE) {
                meta = meta.copy(label = it.meta)
            }
        }

        val NULLABLE_NEOJSON_RULE = BuiltInNullableRule(NEOJSON_RULE) { null }

        fun fromJson(json: String): Track {
            return JSON.deserialize(json, NEOJSON_RULE).meta
        }
    }
}

@NeoJson
internal data class Label(
    val id: Long,
    val name: String,
) {

    // code gen
    class NEOJSON_LABEL {
        var meta = Label(0, "")
    }

    // code gen
    companion object {
        val NEOJSON_RULE = ObjectRule(::NEOJSON_LABEL) {
            put("id", Long.rule) {
                meta = meta.copy(id = it)
            }
            put("name", String.rule) {
                meta = meta.copy(name = it)
            }
        }

        val NULLABLE_NEOJSON_RULE = BuiltInNullableRule(NEOJSON_RULE) { null }
    }

}

@NeoJson
internal data class Artist(
    val id: Long,
    val name: String,
) {

    class NEOJSON_ARTIST {
        var meta = Artist(
            id = 0,
            name = ""
        )
    }

    companion object {
        val NEOJSON_RULE = ObjectRule(::NEOJSON_ARTIST) {
            put("id", Long.rule) {
                meta = meta.copy(id = it)
            }
            put("name", String.rule) {
                meta = meta.copy(name = it)
            }
        }

        val NULLABLE_NEOJSON_RULE = BuiltInNullableRule(NEOJSON_RULE) { null }

        val LIST_RULE = ArrayRule(
            construct = { mutableListOf<NEOJSON_ARTIST>() },
            add = { add(it) },
            NEOJSON_RULE
        )

        val NULLABLE_LIST_RULE = BuiltInNullableRule(LIST_RULE) { null }

        val LIST_NULLABLE_ELEMENT_RULE = ArrayRule(
            construct = { mutableListOf<NEOJSON_ARTIST?>() },
            add = { add(it) },
            NULLABLE_NEOJSON_RULE
        )

        val NULLABLE_LIST_NULLABLE_ELEMENT_RULE = BuiltInNullableRule(LIST_NULLABLE_ELEMENT_RULE) { null }

    }
}
// ------------------------------------------------------------------------------------------------------------------


internal fun Track.toJsonObject(): JSON.Object {
    return ObjectBuilder()
        .put("id", this.id)
        .also {
            if (this.tag == null) {
                it.put("tag", null)
            } else {
                it.put("tag", tag)
            }
        }
        .put("name", this.name)
        .put("label", this.label.toJsonObject().stringify())
        .build()
}

internal fun Label.toJsonObject(): JSON.Object {
    return ObjectBuilder()
        .put("id", this.id)
        .put("name", this.name)
        .build()
}
// ------------------------------------------------------------------------------------------------------------------


// test
private fun main() {
    //language=json
    val json = """
        {
          "id": 101,
          "name": "Fur Elise",
          "label": {
            "id": 1001,
            "name": "NSO"
          }
        }
    """.trimIndent()

    //language=json
    val json1 = """
        {
          "id": 102,
          "name": "Moonlight' Sonata"
        }
    """.trimIndent()

    //language=json
    val json2 = """
        {
          "id": 103,
          "name": "Symphony No.5"
        }
    """.trimIndent()

    println(Track.fromJson(json).toJsonObject())
    println(Track.fromJson(json1))
    println(Track.fromJson(json2))
}