package com.spygstudios.spyglib.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * <p>
 * ComponentUtils class.
 * </p>
 *
 * @author Ris
 * @version $Id: $Id
 */
public class ComponentUtils {

    /*
     * Converts a string to a component
     * 
     * @param message The message to convert
     */
    /**
     * <p>
     * toComponent.
     * </p>
     *
     * @param message a {@link java.lang.String} object
     * @return a {@link net.kyori.adventure.text.Component} object
     */
    public static Component toComponent(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    /*
     * Converts a component to a string
     * 
     * @param component The component to convert
     */
    /**
     * <p>
     * fromComponent.
     * </p>
     *
     * @param component a {@link net.kyori.adventure.text.Component}
     *                  object
     * @return a {@link java.lang.String} object
     */
    public static String fromComponent(Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    /*
     * Converts a list of strings to a list of components
     * 
     * @param messages The messages to convert
     */
    /**
     * <p>
     * toComponent.
     * </p>
     *
     * @param messages a {@link java.util.List} object
     * @return a {@link java.util.List} object
     */
    public static List<Component> toComponent(List<String> messages) {
        List<Component> components = new ArrayList<>();
        for (String message : messages) {
            components.add(toComponent(message));
        }
        return components;
    }

    /*
     * Converts a list of components to a list of strings
     * 
     * @param components The components to convert
     */
    /**
     * <p>
     * fromComponent.
     * </p>
     *
     * @param components a {@link java.util.List} object
     * @return a {@link java.util.List} object
     */
    public static List<String> fromComponent(List<Component> components) {
        List<String> messages = new ArrayList<>();
        for (Component component : components) {
            messages.add(fromComponent(component));
        }
        return messages;
    }

    /**
     * Replaces a key in a component with a value
     *
     * @param component The component to replace
     * @param key       The key to replace
     * @param value     The value to replace the key with
     * @return a {@link net.kyori.adventure.text.Component} object
     */
    public static Component replaceComponent(Component component, String key, String value) {
        return component.replaceText(builder -> builder.matchLiteral(key).replacement(value));
    }

    /**
     * Replaces a key in a list of components with a value
     *
     * @param component    a {@link net.kyori.adventure.text.Component}
     *                     object
     * @param replacements a {@link java.util.Map} object
     * @return a {@link net.kyori.adventure.text.Component} object
     */
    public static Component replaceComponent(Component component, Map<String, String> replacements) {
        Component newComponent = component;
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            newComponent = replaceComponent(newComponent, entry.getKey(), entry.getValue());
        }
        return newComponent;

    }

    /**
     * Replaces a key in a list of components with a value
     *
     * @param components The components to replace
     * @param key        The key to replace
     * @param value      The value to replace the key with
     * @return a {@link java.util.List} object
     */
    public static List<Component> replaceComponent(List<Component> components, String key, String value) {
        List<Component> newComponents = new ArrayList<>();
        for (Component component : components) {
            newComponents.add(replaceComponent(component, key, value));
        }
        return newComponents;
    }
}
