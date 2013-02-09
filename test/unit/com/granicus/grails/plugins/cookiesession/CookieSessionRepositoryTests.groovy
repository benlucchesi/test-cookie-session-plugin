package com.granicus.grails.plugins.cookiesession

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class CookieSessionRepositoryTests {

    void testStringToStoreInSessionIsLessThanMaxCookieSize() {
	CookieSessionRepository cookieSessionRepository = new CookieSessionRepository()
	cookieSessionRepository.cookieCount = 4
	cookieSessionRepository.maxCookieSize = 4
	String input = "123"
	String[] output = cookieSessionRepository.splitString(input)
	assertNotNull(output)
	assertEquals("Must have returned an array with same length as cookieCount", 4, output.length)
	assertEquals("123", output[0])
	assertNull(output[1])
	assertNull(output[2])
	assertNull(output[3])
    }

    void testStringToStoreInSessionIsDivisibleByMaxCookieSize() {
	CookieSessionRepository cookieSessionRepository = new CookieSessionRepository()
	cookieSessionRepository.cookieCount = 4
	cookieSessionRepository.maxCookieSize = 4
	String input = "12345678"
	String[] output = cookieSessionRepository.splitString(input)
	assertNotNull(output)
	assertEquals("Must have returned an array with same length as cookieCount", 4, output.length)
	assertEquals("1234", output[0])
	assertEquals("5678", output[1])
	assertNull(output[2])
	assertNull(output[3])
    }

    void testStringToStoreInSessionIsNotDivisibleByMaxCookieSize() {
	CookieSessionRepository cookieSessionRepository = new CookieSessionRepository()
	cookieSessionRepository.cookieCount = 4
	cookieSessionRepository.maxCookieSize = 4
	String input = "1234567"
	String[] output = cookieSessionRepository.splitString(input)
	assertNotNull(output)
	assertEquals("Must have returned an array with same length as cookieCount", 4, output.length)
	assertEquals("1234", output[0])
	assertEquals("567", output[1])
	assertNull(output[2])
	assertNull(output[3])
    }


    void testStringToStoreInSessionIsEmpty() {
	CookieSessionRepository cookieSessionRepository = new CookieSessionRepository()
	cookieSessionRepository.cookieCount = 4
	cookieSessionRepository.maxCookieSize = 4
	String input = ""
	String[] output = cookieSessionRepository.splitString(input)
	assertNotNull(output)
	assertEquals("Must have returned an array with same length as cookieCount", 4, output.length)
	assertNull(output[0])
	assertNull(output[1])
	assertNull(output[2])
	assertNull(output[3])
    }
}
