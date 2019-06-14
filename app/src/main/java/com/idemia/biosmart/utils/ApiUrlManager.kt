package com.idemia.biosmart.utils

import com.idemia.biosmart.R
import com.idemia.morphobiosmart.android.BaseActivity

/**
 * Api Url Manager
 */
class ApiUrlManager {
    companion object {
        fun url(activity: BaseActivity): String {
            // Https
            val middlewareHttpsValue = activity.preferenceManager.getBoolean("IDEMIA_KEY_MIDDLEWARE_HTTPS", false)

            // IP
            val middlewareIpValue = activity.preferenceManager.getString("IDEMIA_KEY_MIDDLEWARE_IP_ADDRESS",
                activity.getString(R.string.default_middleware_ip_address))

            // Port
            val middlewarePortValue = activity.preferenceManager.getString("IDEMIA_KEY_MIDDLEWARE_PORT",
                activity.getString(R.string.default_middleware_port))

            // Middleware name
            val middlewareNameValue = activity.preferenceManager.getString("IDEMIA_KEY_MIDDLEWARE_NAME",
                activity.getString(R.string.default_middleware_name))

            return generateUrl(
                middlewareHttpsValue,
                middlewareIpValue!!,
                middlewarePortValue!!,
                middlewareNameValue!!
            )
        }

        /**
         * Generate URL
         * @param useHttps True to use https
         * @param ip IP address or domain name
         * @param port Port
         * @param name Middleware name
         * @return URL generated
         */
        private fun generateUrl(useHttps: Boolean, ip: String, port: String, name: String): String {
            if (useHttps)
                return "https://$ip:$port/$name/"
            return  "http://$ip:$port/$name/"
        }
    }
}