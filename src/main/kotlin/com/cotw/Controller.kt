package com.cotw

import com.google.cloud.datastore.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@CrossOrigin(origins = [
    "http://localhost:4200",
    "https://climb-on-the-way-1487535946897.appspot.com",
    "http://climb-on-the-way-1487535946897.appspot.com",
    "http://climbontheway.com",
    "https://climbontheway.com",
    "http://www.climbontheway.com",
    "https://www.climbontheway.com"])
@RestController
class Controller {

    @GetMapping("/locations")
    fun getLocations(request: HttpServletRequest): List<Map<String, String>> {
        val datastore = DatastoreOptions
                .newBuilder()
                .setProjectId("climb-on-the-way-1487535946897")
                .build()
                .service

        val query = Query
                .newEntityQueryBuilder()
                .setKind("Location")
                .build()

        return mapQueryResultsToListOfRows(datastore.run(query))
    }

    private fun mapQueryResultsToListOfRows(results: QueryResults<Entity>): List<Map<String, String>> {
        val resultsMapped = mutableListOf<Map<String, String>>()
        while (results.hasNext()) {
            val queryRow = results.next()
            val rowMap = mutableMapOf<String, String>()
            for (key in queryRow.names) {
                try {
                    rowMap[key] = queryRow.getString(key)
                } catch (e: ClassCastException) {
                    try {
                        rowMap[key] = queryRow.getBoolean(key).toString()
                    } catch (e: ClassCastException) {
                        rowMap[key] = queryRow.getDouble(key).toString()
                    }
                }
            }
            resultsMapped.add(rowMap)
        }

        return resultsMapped
    }
}
