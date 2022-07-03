package com.fitnest.network

internal object Endpoints {
    const val BASE_URL = "https://fitnestappgo.herokuapp.com/"

    object Flow : Group {
        override val name = "flow"
    }

    object Onboarding : Group {
        override val name = "onboarding"
    }

    object Registration : Group {
        override val name = "registration"
    }

    object PrivateArea : Group {
        override val name = "private-area/"

        val DASHBOARD = "${name}dashboard"

        object Notifications : Group {

            override val name = "${PrivateArea.name}notifications"

            val DEACTIVATE = "${name}/deactivate"
            val PIN = "${name}/pin"

        }
    }

    interface Group {
        val name: String
    }
}