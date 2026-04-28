package cr.ac.una.unaplanillam26.util;

import java.util.Map;
import java.util.WeakHashMap;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author ccarranza
 */
public final class BindingUtils {

    private static final Map<ToggleGroup, ChangeListener<Toggle>> GROUP_LISTENERS = new WeakHashMap<>();

    private BindingUtils() {
    }

    public static <T> void bindToggleGroupToProperty(final ToggleGroup toggleGroup, final ObjectProperty<T> property) {
        // Check all toggles for required user data
        toggleGroup.getToggles().forEach(toggle -> {
            if (toggle.getUserData() == null) {
                throw new IllegalArgumentException("The ToggleGroup contains at least one Toggle without user data!");
            }
        });
        // Select initial toggle for current property state
        for (Toggle toggle : toggleGroup.getToggles()) {
            if (property.getValue() != null && property.getValue().equals(toggle.getUserData())) {
                toggleGroup.selectToggle(toggle);
                break;
            }
        }
        // Ensure each ToggleGroup keeps exactly one listener instance.
        unbindToggleGroupToProperty(toggleGroup, property);

        // Update property value on toggle selection changes
        ChangeListener<Toggle> listener = (observable, oldValue, newValue) -> {
            if (newValue == null) {
                property.setValue(null);
                return;
            }
            @SuppressWarnings("unchecked")
            T value = (T) newValue.getUserData();
            property.setValue(value);
        };
        toggleGroup.selectedToggleProperty().addListener(listener);
        GROUP_LISTENERS.put(toggleGroup, listener);
    }

    public static <T> void unbindToggleGroupToProperty(final ToggleGroup toggleGroup, final ObjectProperty<T> property) {
        ChangeListener<Toggle> listener = GROUP_LISTENERS.remove(toggleGroup);
        if (listener != null) {
            toggleGroup.selectedToggleProperty().removeListener(listener);
        }
    }
}
