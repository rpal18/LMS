package library.util;

import java.lang.reflect.Constructor;

public final class ActionResult<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public ActionResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = copyIfNeeded(data); // Defensive copy at construction
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return copyIfNeeded(data); // Defensive copy on retrieval
    }

    /**
     * Creates a defensive copy of the given object, if possible.
     * Falls back to returning the original if copying isn't possible.
     */


    @SuppressWarnings("unchecked")
    private T copyIfNeeded(T obj) {
        if (obj == null) return null;

        // Immutable objects (safe to share)
        if (obj instanceof String || obj instanceof Integer || obj instanceof Boolean ||
                obj instanceof Long || obj instanceof Double || obj instanceof Float) {
            return obj;
        }

        // If object supports cloning
        if (obj instanceof Cloneable) {
            try {
                return (T) obj.getClass().getMethod("clone").invoke(obj);
            } catch (Exception e) {
                throw new RuntimeException("Failed to clone object", e);
            }
        }

        // If object has a copy constructor
       try{
           Constructor<?> copyCons = obj.getClass().getConstructor(obj.getClass()) ;
       } catch (NoSuchMethodException e) {
           throw new RuntimeException(e);
       }catch(Exception e ){
           throw new RuntimeException("failed to copy element : " , e);
       }
        // Fallback: return original (mutable - risky)
        return obj;
    }
}
