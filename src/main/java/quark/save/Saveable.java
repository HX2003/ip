package quark.save;

import java.util.ArrayList;

/**
 * Represents objects that can be saved to persistent storage.
 * Implementing classes must provide a method to convert their state
 * into a list of strings that can be written to disk and
 * later reconstructed.
 */
public interface Saveable {
    /**
     * Converts the object's state into a list of strings for persistent storage.
     *
     * @return An ArrayList of strings representing the object's saveable state.
     */
    ArrayList<String> toSaveStrings();
}
