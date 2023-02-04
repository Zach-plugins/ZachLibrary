package com.zachfr.zachlibrary.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
	public static String colorCode(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	private static final Pattern regex = Pattern.compile("&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|\\{#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})}.*?");

	public static String parseHexColors(String message) {
		message = gradient(message);
		final Matcher matcher = regex.matcher(message);
		while (matcher.find()) {
			final String color = matcher.group();
			final String hexcolor = "#" + (matcher.group(1) == null ? matcher.group(2) : matcher.group(1));
			net.md_5.bungee.api.ChatColor c = null;
			try {
				c = net.md_5.bungee.api.ChatColor.of(hexcolor);
			} catch (final Exception ignored) {
			}
			if (c != null) {
				message = message.replaceAll("[{-}]", "").replaceAll(color.replaceAll("[{-}]", ""), c.toString());
			}
		}
		return message;
	}

	public static String color(String message) {
		if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_16)) {
			message = parseHexColors(colorCode(message));
		} else {
			message = colorCode(message);
		}
		return message;
	}

	public static List<String> color(List<String> message) {
		List<String> lore = new ArrayList<>();
		for (String string : message)
			lore.add(color(string));
		return lore;
	}

	public static String gradient(String message) {
		Rainbow rainbow = new Rainbow();
		Pattern regex = Pattern.compile("&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}).*?:(.*?):&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}).*?");
		Matcher matcher = regex.matcher(message);
		rainbow.setNumberRange(0, 255);
		while (matcher.find()) {
			rainbow.setSpectrum(matcher.group(1), matcher.group(3));
			String s = "";
			int index = 0;
			for (int i = 0; i < 255; i += (255 / matcher.group(2).length())) {
				if (matcher.group(2).length() > index)
					s += "&#" + rainbow.colourAt(i) + matcher.group(2).charAt(index);
				index++;
			}
			return s;
		}
		return message;
	}
}
