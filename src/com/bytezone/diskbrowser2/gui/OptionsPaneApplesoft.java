package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;

import javafx.scene.layout.StackPane;

// -----------------------------------------------------------------------------------//
public class OptionsPaneApplesoft extends StackPane
// -----------------------------------------------------------------------------------//
{
  ApplesoftBasicPreferences applesoftBasicPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneApplesoft (ApplesoftBasicPreferences applesoftBasicPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.applesoftBasicPreferences = applesoftBasicPreferences;
  }
}
