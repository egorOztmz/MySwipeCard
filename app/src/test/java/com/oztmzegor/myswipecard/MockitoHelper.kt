package com.edata.pumppayment

import org.mockito.Mockito

class MockitoHelper {
    companion object {
        fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    }
}