package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.FormattedAppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class GraphicsTab extends DBGraphicsTab
// -----------------------------------------------------------------------------------//
{
  private static final double SCALE = 2;

  private FormattedAppleFile formattedAppleFile;

  // ---------------------------------------------------------------------------------//
  public GraphicsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    if (formattedAppleFile != null && formattedAppleFile.getImage () != null)
      resize (formattedAppleFile.getImage (), SCALE);
  }

  // ---------------------------------------------------------------------------------//
  public void setFormattedAppleFile (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    this.formattedAppleFile = formattedAppleFile;

    refresh ();
  }
}
