package onm

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * This function will create new logger for class.
 * Note that every time is this function called new instance of logger is returned so it would be better to use it
 * inside companion object in your class.
 *
 * ```
 *  companion object {
 *      private val log = loggerFor(YourClass::class.java)
 *  }
 *  ```
 * */
fun <T> loggerFor(usedClass: Class<T>): Logger = LoggerFactory.getLogger(usedClass)
