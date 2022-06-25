package mas.utils

import com.fasterxml.jackson.databind.ObjectMapper

object Json {

    private val objectMapper: ObjectMapper = ObjectMapper()

    init {
        objectMapper.findAndRegisterModules()
    }

    fun <T> stringify(obj: T): String = objectMapper.writeValueAsString(obj)

}
