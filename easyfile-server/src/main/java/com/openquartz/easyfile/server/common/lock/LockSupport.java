package com.openquartz.easyfile.server.common.lock;

import com.openquartz.easyfile.common.bean.Pair;

/**
 * LockSupport
 *
 * @author svnee
 */
public interface LockSupport {

    /**
     * consume if try lock
     *
     * @param lockKey key: bizId, value: bizType
     * @param consumer consumer function
     * @return if consume function
     */
    boolean consumeIfTryLock(Pair<String, LockBizType> lockKey, Consumer consumer);

    /**
     * consume if lock
     * if can't try lock will throw exception
     *
     * @param lockKey lockKey
     * @param consumer consume
     */
    void consumeIfLock(Pair<String, LockBizType> lockKey, Consumer consumer);
}
