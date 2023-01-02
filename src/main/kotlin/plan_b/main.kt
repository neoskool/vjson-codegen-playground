@file:Suppress("ClassName", "SpellCheckingInspection", "PrivatePropertyName")

package plan_b

import vjson.JSON
import vjson.deserializer.rule.LongRule
import vjson.deserializer.rule.ObjectRule
import vjson.deserializer.rule.StringRule
import vjson.util.ObjectBuilder


// runtime
internal annotation class NeoJson
// ------------------------------------------------------------------------------------------------------------------


// by user
@NeoJson
internal data class Track(
    val id: Long,
    val name: String,
    val label: String?
) {

    // code gen
    companion object {
        fun fromJson(json: String): Track {
            return JSON.deserialize(json, NEOJSON_TRACK.NEOJSON_TRACK_RULE).meta
        }
    }
}
// ------------------------------------------------------------------------------------------------------------------


// code gen
internal class NEOJSON_TRACK {
    var meta = Track(0, "", null)

    companion object {

        val NEOJSON_TRACK_RULE = ObjectRule(::NEOJSON_TRACK) {
            put("id", LongRule) {
                meta = meta.copy(id = it)
            }
            put("name", StringRule) {
                meta = meta.copy(name = it)
            }
            put("label", StringRule) {
                meta = meta.copy(label = it)
            }
        }
    }
}

internal fun Track.toJson(): JSON.Object {
    return ObjectBuilder()
        .put("id", this.id)
        .put("name", this.name)
        .put("label", this.label)
        .build()
}
// ------------------------------------------------------------------------------------------------------------------


// test
internal fun main() {
    //language=json
    val json = """
        {
          "id": 101,
          "name": "Fur Elise",
          "label": "lalala"
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

    println(Track.fromJson(json))
    println(Track.fromJson(json1))
    println(Track.fromJson(json2))
}