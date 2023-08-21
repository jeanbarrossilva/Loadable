package com.jeanbarrossilva.loadable

import com.jeanbarrossilva.loadable.Serializability.ENFORCED
import com.jeanbarrossilva.loadable.Serializability.IGNORED
import java.io.ByteArrayOutputStream
import java.io.NotSerializableException
import java.io.ObjectOutputStream

/**
 * Determines whether the content held by [Loadable] and its derived structures should be
 * serializable.
 *
 * @see ENFORCED
 * @see IGNORED
 **/
enum class Serializability {
    /**
     * Indicates that [Loadable]s' and derived structures' content should be able to be serialized;
     * if not, then a [NotSerializableException] will be thrown when they are checked.
     *
     * @see check
     **/
    ENFORCED {
        override fun <T> check(content: T) {
            ByteArrayOutputStream().use { byteArrayOutputStream ->
                ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
                    objectOutputStream.writeObject(content)
                }
            }
        }
    },

    /**
     * Indicates that serialization capability of [Loadable]s' and derived structures' content is
     * ignored.
     **/
    IGNORED {
        override fun <T> check(content: T) {
        }
    };

    /**
     * Checks whether the [content] conforms to the chosen serialization capability policy.
     *
     * @param content Value whose serializability will be checked.
     * @throws NotSerializableException If the policy is [ENFORCED] and the [content] cannot be
     * serialized.
     **/
    @Throws(NotSerializableException::class)
    internal abstract fun <T> check(content: T)
}
