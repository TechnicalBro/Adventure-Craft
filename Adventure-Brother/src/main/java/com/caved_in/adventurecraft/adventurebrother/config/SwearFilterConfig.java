package com.caved_in.adventurecraft.adventurebrother.config;

import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SwearFilterConfig {
    @Element(name = "globally-enabled")
    public boolean globallyEnabled = false;

    @Element(name = "default-enabled")
    public boolean defaultEnabled = true;

    @Element(name = "optional-toggle")
    public boolean optionalToggle = true;

    @Element(name = "blocked-words")
    public List<String> blockedWords = new ArrayList<>();
}