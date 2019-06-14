package com.idemia.biosmart.scenes.user_info

import java.lang.ref.WeakReference

/**
 *  UserInfo Router
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class UserInfoRouter : UserInfoRoutingLogic {

    private var activity: WeakReference<UserInfoActivity>? = null

    fun setActivity(activity: UserInfoActivity) {
        this.activity = WeakReference(activity)
    }

    override fun routeToNextScene(name: String) {
        if (activity?.get() != null) {
            /*Intent intent = new Intent(activity.get().getApplicationContext(), NextSceneActivity.class);
            intent.putExtra("user_name", name);
            activity.get().startActivity(intent);
            */
        }
    }
}

/**
 *  UserInfo Routing Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface UserInfoRoutingLogic {
    /**
     * Route to Next Scene
     */
    fun routeToNextScene(name: String)
}